package br.com.eod.controllers;

import java.util.Map;
import java.util.SortedSet;

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
import br.com.eod.service.PerfilService;
import br.com.eod.service.TarefaService;
import br.com.eod.service.UsuarioService;

@Controller
@SuppressWarnings("unchecked")
public class TarefaController {	
	static Logger LOG = Logger.getLogger(TarefaController.class);
	
	@Autowired
	TarefaService tarefaService;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	PerfilService perfilService;
	
	@Autowired
	AlertaService alertaService;
	
	
	@RequestMapping(value="_listartarefas.do", method = RequestMethod.GET)
	public String listTarefas(HttpServletRequest request,HttpSession session) {
		LOG.info("listTarefas()");
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		usuarioService.getUsuarioDao().refresh((Usuario) request.getSession().getAttribute("usuario"));
		usuario = usuarioService.getUsuarioDao().findByExample(usuario).get(0);
		for (Perfil perfilIt : usuario.getPerfis()) {
			if(perfilIt.equals(((Perfil) session.getAttribute("perfilAtual")))){
				request.setAttribute("perfil", perfilIt);
				
				request.setAttribute("tarefas", perfilIt.getTarefas());
				request.setAttribute("metas", perfilIt.getMetas());
				request.setAttribute("alertas", perfilIt.getAlertas());
			}
		}
		
		return "listtarefas";				
	}
	
	@RequestMapping(value="_adicionartarefa.do", method = RequestMethod.GET)
	public String addTarefa(Map model, HttpServletRequest request, HttpSession session) {
		LOG.info("addTarefa()");
		
		Tarefa tarefa = new Tarefa();
		
		Perfil perfilAtual = (Perfil) session.getAttribute("perfilAtual");
		tarefa.setPerfil(perfilAtual);
		model.put("tarefa", tarefa);
		request.setAttribute("tarefa", tarefa);
		
		usuarioService.getUsuarioDao().refresh((Usuario) request.getSession().getAttribute("usuario"));
		Usuario usuario = usuarioService.getUsuarioDao().findByExample((Usuario) request.getSession().getAttribute("usuario")).get(0);
		model.put("perfis", usuario.getPerfis());
		
		return "addtarefa";				
	}
	
	@RequestMapping(value="_adicionartarefaerro.do", method = RequestMethod.POST)
	public String addTarefaErro(@Valid Tarefa tarefa, BindingResult result,
			Map model,HttpSession session, HttpServletRequest request) {
		LOG.info("addTarefaErro()");
		
		Tarefa tarefaErro = (Tarefa) session.getAttribute("tarefaEroo");
		
		model.put("tarefa", tarefaErro);
		request.setAttribute("tarefa", null);
		
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		usuarioService.getUsuarioDao().refresh((Usuario) request.getSession().getAttribute("usuario"));
		usuario = usuarioService.getUsuarioDao().findByExample(usuario).get(0);
		
		model.put("perfis", usuario.getPerfis());
		
		return "addtarefa";				
	}
	
	

	@RequestMapping(value="_editartarefa.do", method = RequestMethod.POST)
	public String editTarefa(Map model,HttpServletRequest request,HttpSession session) {
		LOG.info("editTarefa()");
		
		usuarioService.getUsuarioDao().refresh((Usuario) request.getSession().getAttribute("usuario"));
		Usuario usuario = usuarioService.getUsuarioDao().findByExample((Usuario) request.getSession().getAttribute("usuario")).get(0);
		Perfil perfilAtual = (Perfil) session.getAttribute("perfilAtual");
		
		SortedSet<Tarefa> tarefas = null; 
		for (Perfil perfil : usuario.getPerfis()) {
			if(perfil.equals(perfilAtual)){
				tarefas = (SortedSet<Tarefa>) perfil.getTarefas();
				break;
			}
		}
		
		model.put("perfis", usuario.getPerfis());
		for (Tarefa tarefaEditar : tarefas) {
			if(Integer.parseInt(request.getParameter("idTarefa")) == tarefaEditar.getId()){
				LOG.info("Encontrado a tarefa "+tarefaEditar.getNome());
				model.put("tarefa", tarefaEditar);
				break;
			}
		}
		
		return "addtarefa";				
	}
	
	@RequestMapping(value="_salvaretarefa.do", method=RequestMethod.POST)
	//@RequestMapping(method = RequestMethod.POST)
	public String saveTarefa(@Valid Tarefa tarefa, BindingResult result,
			Map model,HttpSession session, HttpServletRequest request){
		LOG.info("saveTarefa()");
		
		if (result.hasErrors()) {
			return "addTarefa";
		}
		
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		usuarioService.getUsuarioDao().refresh((Usuario) request.getSession().getAttribute("usuario"));
		usuario = usuarioService.getUsuarioDao().findByExample(usuario).get(0);
		
		for (Perfil perfil : usuario.getPerfis()) {
			if(perfil.equals(tarefa.getPerfil())){
				tarefa.setPerfil(perfil);
				
				if(tarefa.getMotivacao().equals("AD")){
					tarefa.setDataFim(null);
					tarefa.setDataInicio(null);
				}
				
				//Verifica se nao existe tarefa na mesma data e hora. 
				if(tarefa.getId() == 0){
					if(perfil.getTarefas().contains(tarefa)){
						session.setAttribute("erro", "Não foi inserido a tarefa pois já existe uma tarefa definida para esse dia e horario "+tarefa.getData()+" , verifique.");
						session.setAttribute("tarefaEroo", tarefa);
						model.put("perfis", usuario.getPerfis());
						return "addtarefaerror";
					}
				}
				
				try{
					Tarefa conflito = tarefaService.salvarTarefa(usuario, tarefa);
					
					if(conflito != null){
						session.setAttribute("erro", "Não foi possivel inserir a tarefa pois entrou em conflito com a tarefa abaixo, verifique. /n ");
						session.setAttribute("ID",conflito.getId());
						session.setAttribute("Nome",conflito.getNome());
						session.setAttribute("DataInicio",conflito.getDataInicio());
						session.setAttribute("DataFim",conflito.getDataFim());
						
						session.setAttribute("tarefaEroo", tarefa);
						model.put("perfis", usuario.getPerfis());
						return "addtarefaerror";
					}
				}catch (Exception e) {
					e.printStackTrace();
					session.setAttribute("erro", e.getMessage());
					session.setAttribute("ID","");
					session.setAttribute("Nome","");
					session.setAttribute("DataInicio","");
					session.setAttribute("DataFim","");
					
					session.setAttribute("tarefaEroo", tarefa);
					model.put("perfis", usuario.getPerfis());
					return "addtarefaerror";
				}
				
				
				
				usuarioService.getUsuarioDao().refresh(usuario);
				
				for (Perfil perfilIt : usuario.getPerfis()) {
					if(perfilIt.equals(((Perfil) session.getAttribute("perfilAtual")))){
						session.setAttribute("perfilAtual", perfilIt);
						request.setAttribute("tarefas", perfilIt.getTarefas());
						request.setAttribute("metas", perfilIt.getMetas());
						request.setAttribute("alertas", perfilIt.getAlertas());						
					}
				}
				
				session.setAttribute("erro", null);
				session.setAttribute("ID",null);
				session.setAttribute("Nome",null);
				session.setAttribute("DataInicio",null);
				session.setAttribute("DataFim",null);
				
				session.setAttribute("tarefaEroo", null);
				
				request.setAttribute("recarregarMenu", true);
				
				return "listtarefas";
			}
		}
		return "addtarefa";
	}
	
	
	
	@RequestMapping(value="_deletaretarefa.do", method=RequestMethod.POST)
	//@RequestMapping(method = RequestMethod.POST)
	public String deleteTarefa(HttpSession session, HttpServletRequest request){
		LOG.info("deleteTarefa()");

		Perfil perfilAtual = (Perfil) session.getAttribute("perfilAtual");

		for (Tarefa tarefaDeletar : perfilAtual.getTarefas()) {
			if(Integer.parseInt(request.getParameter("idTarefa")) == tarefaDeletar.getId()){
				LOG.info("Encontrado a tarefa "+tarefaDeletar.getNome());
				
				for (Alerta alertaDeletar : tarefaDeletar.getAlerta()) {
					alertaDeletar.getPerfil().getAlertas().remove(alertaDeletar);
					alertaDeletar.getTarefa().getAlerta().remove(alertaDeletar);
					
					alertaService.getAlertaDao().remove(alertaDeletar);
				}
				
				tarefaDeletar.getPerfil().getTarefas().remove(tarefaDeletar);
				tarefaService.getTarefaDao().remove(tarefaDeletar);

				break;
			}
		}

		request.setAttribute("recarregarMenu", true);
		
		request.setAttribute("perfilAtual", perfilAtual);
		request.setAttribute("tarefas", perfilAtual.getTarefas());
		request.setAttribute("metas", perfilAtual.getMetas());
		request.setAttribute("alertas", perfilAtual.getAlertas());	
		
		return "listtarefas";
	}

}
