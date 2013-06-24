package br.com.eod.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
public class PerfilController {
	static Logger LOG = Logger.getLogger(PerfilController.class);
	
	@Autowired
	PerfilService perfilService;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	TarefaService tarefaService;
	
	@Autowired
	AlertaService alertaService;
	
	@RequestMapping(value="_adicionarperfil.do", method = RequestMethod.GET)
	public String addPerfil(Map model, HttpServletRequest request, HttpSession session) {
		LOG.info("addPerfil()");
		
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		usuarioService.getUsuarioDao().refresh((Usuario) request.getSession().getAttribute("usuario"));
		usuario = usuarioService.getUsuarioDao().findByExample(usuario).get(0);
		
		Perfil perfil = new Perfil();
		perfil.setUsuario(usuario);
		
		model.put("perfil", perfil);
		request.setAttribute("perfil", perfil);
		
		List<Usuario> usuarios  = new ArrayList<Usuario>();
		usuarios.add(usuario);
		
		model.put("usuarios", usuarios);
		
		return "addperfil";				
	}
	
	@RequestMapping(value="_salvarperfil.do", method=RequestMethod.POST)
	public String savePerfil(@Valid Perfil perfil, BindingResult result,
			Map model,HttpSession session, HttpServletRequest request){
		LOG.info("savePerfil()");
		
		if (result.hasErrors()) {
			return "addperfil";
		}
		
		usuarioService.getUsuarioDao().refresh((Usuario) request.getSession().getAttribute("usuario"));
		Usuario usuario = usuarioService.getUsuarioDao().findByExample((Usuario) request.getSession().getAttribute("usuario")).get(0);
		
		if(perfil.getId() == 0){
			if(usuario.getPerfis().contains(perfil)){
				session.setAttribute("erro", "Não foi possivel inserir o perfil pois já existe perfil com o nome "+perfil.getNome());
				session.setAttribute("perfilEroo", perfil);
			}
		}else{
			for (Perfil perfilExistente : usuario.getPerfis()) {
				if(perfilExistente.getNome().equals(perfil.getNome())){
					session.setAttribute("erro", "Não foi possivel editar o perfil pois já existe perfil com o nome "+perfil.getNome());
					session.setAttribute("perfilEroo", perfil);
					
					List<Usuario> usuarios  = new ArrayList<Usuario>();
					usuarios.add(usuario);
					model.put("usuarios", usuario);
					
					return "addperfilerror";
				}
			}
		}
		
		//usuarioService.getUsuarioDao().saveOrUpdate(usuario);
		perfilService.getPerfilDao().saveOrUpdate(perfil);

		usuarioService.getUsuarioDao().refresh((Usuario) request.getSession().getAttribute("usuario"));
		usuario = usuarioService.getUsuarioDao().findByExample((Usuario) request.getSession().getAttribute("usuario")).get(0);
		
		request.setAttribute("perfis", usuario.getPerfis());	
		
		return "listperfis";
	}	
	
	@RequestMapping(value="_listarperfis.do", method = RequestMethod.GET)
	public String listPerfil(HttpServletRequest request, HttpSession session) {
		LOG.info("listPerfil()");
		
		usuarioService.getUsuarioDao().refresh((Usuario) request.getSession().getAttribute("usuario"));
		Usuario usuario = usuarioService.getUsuarioDao().findByExample((Usuario) request.getSession().getAttribute("usuario")).get(0);
		
		request.setAttribute("perfis", usuario.getPerfis());
		
		return "listperfis";				
	}	
	
	@RequestMapping(value="_editarperfil.do", method = RequestMethod.POST)
	public String editPerfil(Map model,HttpServletRequest request,HttpSession session) {
		LOG.info("editPerfil()");
		
		usuarioService.getUsuarioDao().refresh((Usuario) request.getSession().getAttribute("usuario"));
		Usuario usuario = usuarioService.getUsuarioDao().findByExample((Usuario) request.getSession().getAttribute("usuario")).get(0);
		
		for (Perfil perfil : usuario.getPerfis()) {
			if(perfil.getId() == Integer.parseInt(request.getParameter("idPerfil"))){
				model.put("perfil", perfil);
				
				List<Usuario> usuarios = new ArrayList<Usuario>();
				usuarios.add(usuario);
				model.put("usuarios", usuarios);
				
				break;
			}
		}
		
		return "addperfil";				
	}	
	
	@RequestMapping(value="_deletarperfil.do", method=RequestMethod.POST)
	public String deletePerfil(HttpSession session, HttpServletRequest request){
		LOG.info("deletePerfil()");

		usuarioService.getUsuarioDao().refresh((Usuario) request.getSession().getAttribute("usuario"));
		Usuario usuario = usuarioService.getUsuarioDao().findByExample((Usuario) request.getSession().getAttribute("usuario")).get(0);
		
		for (Perfil perfil : usuario.getPerfis()) {
			if(perfil.getId() == Integer.parseInt(request.getParameter("idPerfil"))){
				for (Tarefa tarefa : perfil.getTarefas()) {
					for (Alerta alerta : tarefa.getAlerta()) {
						
						alerta.getTarefa().getAlerta().remove(alerta);
						alertaService.getAlertaDao().remove(alerta);
					}
					
					tarefa.getPerfil().getTarefas().remove(tarefa);
					tarefaService.getTarefaDao().remove(tarefa);
				}
				
				perfil.getUsuario().getPerfis().remove(perfil);
				perfilService.getPerfilDao().remove(perfil);
				
				break;	
			}
		}

		Perfil perfilAtual = (Perfil) session.getAttribute("perfilAtual");
		if(perfilAtual != null &&  perfilAtual.getId() == Integer.parseInt(request.getParameter("idPerfil"))){
			return "escolheperfil";
		}else{
			request.setAttribute("perfis", usuario.getPerfis());			
			
			return "listperfis";
		}
	}	
}
