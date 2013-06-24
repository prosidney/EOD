package br.com.eod.controllers;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.eod.model.Meta;
import br.com.eod.model.Perfil;
import br.com.eod.model.Tarefa;
import br.com.eod.model.Usuario;
import br.com.eod.service.MetaService;
import br.com.eod.service.UsuarioService;


@Controller
@SuppressWarnings("unchecked")
public class MetaController {
	static Logger LOG = Logger.getLogger(MetaController.class);
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired 
	MetaService metaService;
	
	@RequestMapping(value="_adicionarmeta.do", method = RequestMethod.GET)
	public String addMeta(Map model, HttpServletRequest request, HttpSession session) {
		LOG.info("addMeta()");
		
		Meta meta = new Meta();
		model.put("meta", meta);
		
		request.setAttribute("tarefa", meta);
		
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		usuarioService.getUsuarioDao().refresh((Usuario) request.getSession().getAttribute("usuario"));
		usuario = usuarioService.getUsuarioDao().findByExample(usuario).get(0);
		
		model.put("perfis", usuario.getPerfis());
		
		return "addmeta";				
	}
	
	@RequestMapping(value="_editarmeta.do", method = RequestMethod.POST)
	public String editMeta(Map model,HttpServletRequest request,HttpSession session) {
		LOG.info("editMeta()");
		
		usuarioService.getUsuarioDao().refresh((Usuario) request.getSession().getAttribute("usuario"));
		Usuario usuario = usuarioService.getUsuarioDao().findByExample((Usuario) request.getSession().getAttribute("usuario")).get(0);
		Perfil perfilAtual = (Perfil) session.getAttribute("perfilAtual");
		
		Set<Meta> metas = null; 
		for (Perfil perfil : usuario.getPerfis()) {
			if(perfil.equals(perfilAtual)){
				metas = (Set<Meta>) perfil.getMetas();
				break;
			}
		}
		
		model.put("perfis", usuario.getPerfis());
		for (Meta metaEditar : metas) {
			if(Integer.parseInt(request.getParameter("idMeta")) == metaEditar.getId()){
				LOG.info("Encontrado a meta "+metaEditar.getNome());
				model.put("meta", metaEditar);
				break;
			}
		}
		
		return "addmeta";				
	}
	
	@RequestMapping(value="_salvarmeta.do", method=RequestMethod.POST)
	//@RequestMapping(method = RequestMethod.POST)
	public String saveMeta(@Valid Meta meta, BindingResult result,
			Map model,HttpSession session, HttpServletRequest request){
		LOG.info("saveMeta()");
		
		if (result.hasErrors()) {
			return "addMeta";
		}
		
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		usuarioService.getUsuarioDao().refresh((Usuario) request.getSession().getAttribute("usuario"));
		usuario = usuarioService.getUsuarioDao().findByExample(usuario).get(0);
		
		for (Perfil perfil : usuario.getPerfis()) {
			if(perfil.equals(meta.getPerfil())){
				meta.setPerfil(perfil);
				
				metaService.getMetaDao().saveOrUpdate(meta);
				
				usuarioService.getUsuarioDao().refresh((Usuario) request.getSession().getAttribute("usuario"));
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
		return "addmeta";
	}	

	@RequestMapping(value="_deletarmeta.do", method=RequestMethod.POST)
	public String deleteMeta(HttpSession session, HttpServletRequest request){
		LOG.info("deleteMeta()");

		Perfil perfil = (Perfil) session.getAttribute("perfilAtual");

		for (Meta metaDeletar : perfil.getMetas()) {
			if(Integer.parseInt(request.getParameter("idMeta")) == metaDeletar.getId()){
				LOG.info("Encontrado a meta "+metaDeletar.getNome());
				
				metaDeletar.getPerfil().getMetas().remove(metaDeletar);
				
				metaService.getMetaDao().remove(metaDeletar);

				break;
			}
		}

		request.setAttribute("perfilAtual", perfil);
		request.setAttribute("tarefas", perfil.getTarefas());
		request.setAttribute("metas", perfil.getMetas());
		request.setAttribute("alertas", perfil.getAlertas());	
		
		return "listtarefas";
	}
}
