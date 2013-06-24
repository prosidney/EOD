package br.com.eod.controllers;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.eod.model.Alerta;
import br.com.eod.model.Perfil;
import br.com.eod.model.Tarefa;
import br.com.eod.model.Usuario;
import br.com.eod.service.AlertaService;
import br.com.eod.service.TarefaService;
import br.com.eod.service.UsuarioService;


@Controller
@SuppressWarnings("unchecked")
public class AlertaController {
	static Logger LOG = Logger.getLogger(AlertaController.class);
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	TarefaService tarefaService;
	
	@Autowired
	AlertaService alertaService;
	
	@RequestMapping(value="_adicionaralerta.do", method = RequestMethod.GET)
	public String addAlerta(Map model, HttpServletRequest request, HttpSession session) {
		LOG.info("addAlerta()");
		
		Alerta alerta = new Alerta();
		model.put("alerta", alerta);
		
		request.setAttribute("alerta", alerta);
		
		usuarioService.getUsuarioDao().refresh((Usuario) request.getSession().getAttribute("usuario"));
		
		Usuario usuario = usuarioService.getUsuarioDao().findByExample((Usuario) request.getSession().getAttribute("usuario")).get(0);
		model.put("perfis", usuario.getPerfis());
		
		return "addalarme";				
	}
	
	@RequestMapping(value="_editaralerta.do", method = RequestMethod.POST)
	public String editAlarme(Map model,HttpServletRequest request,HttpSession session) {
		LOG.info("editMeta()");
		
		usuarioService.getUsuarioDao().refresh((Usuario) request.getSession().getAttribute("usuario"));
		Usuario usuario = usuarioService.getUsuarioDao().findByExample((Usuario) request.getSession().getAttribute("usuario")).get(0);
		model.put("perfis", usuario.getPerfis());
		
		Perfil perfilAtual = (Perfil) session.getAttribute("perfilAtual");
		
		Set<Tarefa> tarefas = null;
		for (Perfil perfil : usuario.getPerfis()) {
			if(perfil.equals(perfilAtual)){
				tarefas = (Set<Tarefa>) perfil.getTarefas();
				break;
			}
		}
		
		for (Tarefa tarefa : tarefas) {
			for (Alerta alertaEditar : tarefa.getAlerta()) {
				if(Integer.parseInt(request.getParameter("idAlerta")) == alertaEditar.getId()){
					LOG.info("Encontrado o alerta "+alertaEditar.getNome());
					model.put("alerta", alertaEditar);
					model.put("tarefas", tarefas);
					break;
				}
			}
		}
		
		return "addalerta";				
	}	
	
	@RequestMapping(value="_salvaralerta.do", method=RequestMethod.POST)
	public String saveAlerta(@Valid Alerta alerta, BindingResult result,
			Map model,HttpSession session, HttpServletRequest request){
		LOG.info("saveAlerta()");
		
		if (result.hasErrors()) {
			return "addalerta";
		}
		
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		usuarioService.getUsuarioDao().refresh(usuario);
		usuario = usuarioService.getUsuarioDao().findByExample(usuario).get(0);
		
		for (Perfil perfil : usuario.getPerfis()) {
			if(perfil.equals(alerta.getPerfil())){
				alerta.setPerfil(perfil);
				
				alertaService.getAlertaDao().saveOrUpdate(alerta);
				
				//usuarioService.getUsuarioDao().flush();
				
				usuarioService.getUsuarioDao().refresh(usuario);
				usuario = usuarioService.getUsuarioDao().findByExample(usuario).get(0);
				
				for (Perfil perfilIt : usuario.getPerfis()) {
					if(perfilIt.equals(((Perfil) session.getAttribute("perfilAtual")))){
						session.setAttribute("perfilAtual", perfilIt);
						request.setAttribute("tarefas", perfilIt.getTarefas());
						request.setAttribute("metas", perfilIt.getMetas());
						request.setAttribute("alertas", perfilIt.getAlertas());
					}
				}
				
				return "listtarefas";
			}
		}
		return "addalerta";
	}		
	
	@RequestMapping(value="_adicionaAlertaTarefa.do", method = RequestMethod.POST)
	public String adicionarAlertaTarefa(Map model,HttpServletRequest request,HttpSession session) {
		LOG.info("adicionarAlertaTarefa()");
		
		Alerta alerta = new Alerta();
		
		Perfil perfilAtual = (Perfil) session.getAttribute("perfilAtual");
		for (Tarefa tarefa : perfilAtual.getTarefas()) {
			if(tarefa.getId() == Integer.parseInt(request.getParameter("idTarefa"))){
				alerta.setTarefa(tarefa);
				alerta.setPerfil(perfilAtual);
				break;
			}
		}
		
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		usuarioService.getUsuarioDao().refresh(usuario);
		usuario = usuarioService.getUsuarioDao().findByExample(usuario).get(0);
		
		model.put("perfis", usuario.getPerfis());
		model.put("tarefas", perfilAtual.getTarefas());
		request.setAttribute("alerta", alerta);
		
		
		return "addalerta";	
	}
	
	@RequestMapping(value="_deletaralerta.do", method=RequestMethod.POST)
	public String deleteAlerta(HttpSession session, HttpServletRequest request){
		LOG.info("deleteAlerta()");

		Perfil perfilAtual = (Perfil) session.getAttribute("perfilAtual");

		for (Alerta alertaDeletar : perfilAtual.getAlertas()) {
			if(Integer.parseInt(request.getParameter("idAlerta")) == alertaDeletar.getId()){
				LOG.info("Encontrado o alerta "+alertaDeletar.getNome());
				
				alertaDeletar.getPerfil().getAlertas().remove(alertaDeletar);
				alertaDeletar.getTarefa().getAlerta().remove(alertaDeletar);
				alertaService.getAlertaDao().remove(alertaDeletar);

				break;
			}
		}

		request.setAttribute("perfilAtual", perfilAtual);
		request.setAttribute("tarefas", perfilAtual.getTarefas());
		request.setAttribute("metas", perfilAtual.getMetas());
		request.setAttribute("alertas", perfilAtual.getAlertas());	
		
		return "listtarefas";
	}
}