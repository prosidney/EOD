package br.com.eod.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.eod.model.Alerta;
import br.com.eod.model.Data;
import br.com.eod.model.Meta;
import br.com.eod.model.Perfil;
import br.com.eod.model.Tarefa;
import br.com.eod.model.Usuario;
import br.com.eod.service.UsuarioService;

@Controller
public class HomeController {
	static Logger LOG = Logger.getLogger(HomeController.class);
	
	@Autowired
	UsuarioService usuarioService;
	
	@RequestMapping(value="_home.do", method=RequestMethod.GET)
	public String showPage(@SuppressWarnings("rawtypes") Map model){		
		LOG.info("HomeController.showPage()");
		
		return "home";

	}
	
	@RequestMapping(value="_recarregaMenu.do", method=RequestMethod.GET)
	public String recarregarMenu(HttpServletRequest request,HttpSession session){		
		LOG.info("recarregarMenu()");
		
		usuarioService.getUsuarioDao().refresh((Usuario) request.getSession().getAttribute("usuario"));
		Usuario usuario = usuarioService.getUsuarioDao().findByExample((Usuario) request.getSession().getAttribute("usuario")).get(0);
		
		Set<Tarefa> proximasTarefas = new TreeSet<Tarefa>();
		Set<Tarefa> todasTarefas = new TreeSet<Tarefa>();
		
		for (Perfil perfil : usuario.getPerfis()) {
			todasTarefas.addAll(perfil.getTarefas());
		}
		Date now = new Date();
		for(Tarefa tarefa : todasTarefas){
			if(tarefa.getData().after(now)){
				proximasTarefas.add(tarefa);
			}
			if(proximasTarefas.size() == 3)
				break;
		}
		
		session.setAttribute("proximasTarefas", proximasTarefas);
		request.setAttribute("proximasTarefas", proximasTarefas);		
		
		
		return "menu";

	}	
	
	@RequestMapping(value="_eventos.do", method=RequestMethod.GET)
	public String showPageEventos(HttpServletRequest request,HttpServletResponse response){		
		LOG.info("HomeController.showPageEventos()");
		return "eventos";

	}
	
	@RequestMapping(value="_getContatos.do", method=RequestMethod.GET)
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		LOG.info("HomeController.handleRequest()");

		//prepara o objeto map no formato necessário para o JsonReader
		//no data.Store
		Map<String,Object> modelMap = new HashMap<String,Object>(2);
		
		//indica que será transformado para formato JSON
		return new ModelAndView("jsonView", modelMap);
	}
	
	@RequestMapping(value="_carregarEventos.do", method=RequestMethod.GET)
	public ModelAndView carregarEventos(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LOG.info("HomeController.carregarEventos()");
		
		//usuarioService.getUsuarioDao().flush();
		@SuppressWarnings("unchecked")
		Usuario usuario = usuarioService.getUsuarioDao().findByExample((Usuario) request.getSession().getAttribute("usuario")).get(0);
		usuarioService.getUsuarioDao().refresh((Usuario) request.getSession().getAttribute("usuario"));
		
		//prepara o objeto map no formato necessário para o JsonReader
		//no data.Store
		Map<String,Object> modelMap = new HashMap<String,Object>(2);
		
		Perfil perfilAtual = (Perfil) request.getSession().getAttribute("perfilAtual");
		for (Perfil perfilIt : usuario.getPerfis()) {
			if(perfilIt.equals(perfilAtual)){
				modelMap.put("tarefas", converteTarefasJson(perfilIt.getTarefas()).toArray());
				modelMap.put("metas", converteMetasJson(perfilIt.getMetas()).toArray());
				modelMap.put("alertas", converteAlertasJson(perfilIt.getAlertas()).toArray());
			}
		}
		
		//indica que será transformado para formato JSON
		return new ModelAndView("jsonView", modelMap);
	}
	
	private SortedSet<Tarefa> converteTarefasJson(SortedSet<Tarefa> tarefasDao) throws Exception{
		SortedSet<Tarefa> tarefasJson = new TreeSet<Tarefa>();
		for (Tarefa tarefa: tarefasDao) {
			Tarefa tarefaJson = new Tarefa();
			
			tarefaJson.setNome(tarefa.getNome());
			tarefaJson.setDescricao(tarefa.getDescricao());
			tarefaJson.setData(tarefa.getData());
			if(tarefa.getDataInicio() != null){
				tarefaJson.setDataInicio(new Data(tarefa.getDataInicio().getTime()));
				tarefaJson.setDataFim(new Data(tarefa.getDataFim().getTime()));
			}else{
				if(tarefa.getData() == null){
					throw new Exception("Inconsistencia de dados");
				}
				tarefaJson.setData(new Data(tarefa.getData().getTime()));
				tarefaJson.setDataInicio(new Data(tarefa.getData().getTime()));
				tarefaJson.setDataFim(new Data(tarefa.getData().getTime()));
			}
				
			
			
			tarefasJson.add(tarefaJson);
		}
		
		return tarefasJson;
	}
	
	private Set<Meta> converteMetasJson(Set<Meta> metasDao) throws Exception{
		Set<Meta> metasJson = new TreeSet<Meta>();
		for (Meta meta: metasDao) {
			Meta metaJson = new Meta();
			
			metaJson.setNome(meta.getNome());
			metaJson.setDescricao(meta.getDescricao());
			metaJson.setAte(new Data(meta.getAte().getTime()));
				
			metasJson.add(metaJson);
		}
		
		return metasJson;
	}	

	private Set<Alerta> converteAlertasJson(Set<Alerta> alertaDao) throws Exception{
		Set<Alerta> alertasJson = new TreeSet<Alerta>();
		for (Alerta alerta: alertaDao) {
			Alerta alertaJson = new Alerta();
			
			alertaJson.setNome(alerta.getNome());
			alertaJson.setData(new Data(alerta.getData().getTime()));
				
			alertasJson.add(alertaJson);
		}
		
		return alertasJson;
	}	
}
