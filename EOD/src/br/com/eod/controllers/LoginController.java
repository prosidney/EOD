package br.com.eod.controllers;

import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.eod.model.Perfil;
import br.com.eod.model.Tarefa;
import br.com.eod.model.Usuario;
import br.com.eod.service.PerfilService;
import br.com.eod.service.UsuarioService;

@Controller
@SuppressWarnings("unchecked")
public class LoginController {
	static Logger LOG = Logger.getLogger(LoginController.class);
	
	private static Date now;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	PerfilService perfilService;
	
	@RequestMapping(value="_login.do", method = RequestMethod.GET)
	public String logar(HttpSession session, HttpServletRequest request, Authentication authentication) {
		LOG.info("Usuário logado: "+authentication.getName());
		
		
		if(now == null){
			now = new Date();
		}
		
/*		Calendar trava = GregorianCalendar.getInstance();
		trava.set(Calendar.YEAR, 2011);
		trava.set(Calendar.MONTH, 10);
		trava.set(Calendar.DAY_OF_MONTH, 25);
		
		if(new Date().after(trava.getTime())){
			return "blank";
		}*/
		
		Usuario usuario = new Usuario(authentication.getName());
		usuario = usuarioService.getUsuarioDao().findByExample(usuario).get(0);
		
		session.setAttribute("usuario", usuario);
		request.setAttribute("usuario", usuario);
		
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
		
		return "principal";
	}
	
	@RequestMapping(value="_mudarperfil.do", method = RequestMethod.GET)
	public String mudarPerfil(HttpServletRequest request,HttpSession session) throws Exception {
		LOG.info("mudarPerfil");
		usuarioService.getUsuarioDao().refresh((Usuario) request.getSession().getAttribute("usuario"));
		Usuario usuario = usuarioService.getUsuarioDao().findByExample((Usuario) request.getSession().getAttribute("usuario")).get(0);
		request.setAttribute("usuario", usuario);
		
		return "escolheperfil";
	}	
	
	@RequestMapping(value="_escolheperfil.do", method = RequestMethod.POST)
	public String escolherPerfil(HttpServletRequest request,HttpSession session) throws Exception {
		LOG.info("escolherPerfil");
		usuarioService.getUsuarioDao().refresh((Usuario) request.getSession().getAttribute("usuario"));
		Usuario usuario = usuarioService.getUsuarioDao().findByExample((Usuario) request.getSession().getAttribute("usuario")).get(0);
		
		for (Perfil perfil : usuario.getPerfis()) {
			if(perfil.getId() == Integer.parseInt(request.getParameter("idPerfil"))){
				session.setAttribute("perfilAtual", perfil);
				request.setAttribute("perfilAtual", perfil);
				return "home";
			}
		}
		
		throw new Exception("Não foi possivel encontrar um perfil, verifique com o administrador do sistema");
	}	

	@RequestMapping(value="novousuario.do", method=RequestMethod.GET)
	public String novoUsuario(Map model, HttpServletRequest request, HttpSession session){
		LOG.info("LoginController.novoUsuario()");
		
		Usuario novoUsuario = new Usuario();
		
		model.put("usuario", novoUsuario);
		request.setAttribute("usuario", novoUsuario);
		
		return "addusuario";
	}
	
	@RequestMapping(value="salvarnovousuario.do", method=RequestMethod.POST)
	//@RequestMapping(method = RequestMethod.POST)
	public String saveUsuario(@Valid Usuario usuario, BindingResult result,
			Map model,HttpSession session, HttpServletRequest request){
		LOG.info("saveUsuario()");
		
		if (result.hasErrors()) {
			return "addusuario";
		}
		
		try{
			Usuario usuarioCadastrado = usuarioService.getUsuarioDao().saveOrUpdate(usuario);
			
			usuarioCadastrado =  usuarioService.getUsuarioDao().findByExample(usuarioCadastrado).get(0);
			
			Perfil perfilPessoal = new Perfil();
			perfilPessoal.setNome("Perfil Pessoal");
			perfilPessoal.setUsuario(usuarioCadastrado);
			
			perfilService.getPerfilDao().saveOrUpdate(perfilPessoal);
			session.setAttribute("erro", null);
			session.setAttribute("novousuario", usuarioCadastrado);
		}catch(Exception e){
			session.setAttribute("erro", "Ocorreu um erro ao cadastrar o usuario,contate o administrador do sistema "+e.getMessage());
			
			return "addusuario";
		}
		
		return "usuariocadastrado";
	}
}
