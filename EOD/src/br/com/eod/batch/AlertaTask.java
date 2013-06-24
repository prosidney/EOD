package br.com.eod.batch;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.eod.model.Alerta;
import br.com.eod.service.AlertaService;

public class AlertaTask {
	public static String CORPO_MENSAGEM = "Essa mensagem foi enviado para lembrar de sua tarefa: \n";
	public static String ASSUNTO_MENSAGEM = "[EOD] Não se esqueca da tarefa";
	public static String DE = "tccpucsp2011@gmail.com";
	
	public static String SEMANAL = "S";
	public static String ANUAL = "A";
	public static String MENSAL = "M";
	
	public static String SMTP_SERVER = "smtp.gmail.com";
	public static String SMTP_SERVER_PORT = "465";
	
	@Autowired
	AlertaService alertaService;
	
	static Logger LOG = Logger.getLogger(AlertaTask.class);
	// properties and collaborators
	  
	public void doIt() {
		LOG.info("doIt");

		try{
			Date now = new Date();

			List<Alerta> alertas = alertaService.getAlertaDao().findAllBatch();

			for (Alerta alerta : alertas) {
				if(now.after(alerta.getData())){
					StringBuilder message = new StringBuilder(CORPO_MENSAGEM);
					message.append("Tarefa: "+alerta.getTarefa().getNome()+"\n");
					message.append("Descrição: "+alerta.getTarefa().getDescricao()+"\n");
					message.append("Data: "+alerta.getTarefa().getData()+"\n");

					String to = alerta.getTarefa().getPerfil().getUsuario().getEmail();

					if(alerta.getEnviado() == null){
						try{
							LOG.info("Enviando e-mail para o alerta"+alerta.getNome());

							enviarEmail(to, ASSUNTO_MENSAGEM,  message.toString(), DE, alerta, now);

							alerta.setEnviado(now);

							LOG.info("E-mail enviado para o alerta"+alerta.getNome());
							alertaService.getAlertaDao().saveOrUpdate(alerta);
							LOG.info("Atualizado campo enviado do alerta:"+alerta.getNome()+","+alerta.getEnviado());
						}catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					}else{
						String repetir = alerta.getRepetir();
						Calendar gc = GregorianCalendar.getInstance();
						gc.setTime(alerta.getEnviado());

						if(SEMANAL.equalsIgnoreCase(repetir)){
							gc.add(Calendar.WEEK_OF_YEAR, 1);

							if(now.after(gc.getTime())){
								try {
									LOG.info("Enviando e-mail para o alerta"+alerta.getNome());

									enviarEmail(to, ASSUNTO_MENSAGEM,  message.toString(), DE, alerta, now);

									LOG.info("E-mail enviado para o alerta"+alerta.getNome());
									alertaService.getAlertaDao().saveOrUpdate(alerta);
									LOG.info("Atualizado campo enviado do alerta:"+alerta.getNome()+","+alerta.getEnviado());
								} catch (MessagingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						if(MENSAL.equalsIgnoreCase(repetir)){
							gc.add(Calendar.MONTH, 1);

							if(now.after(gc.getTime())){
								try {
									LOG.info("Enviando e-mail para o alerta"+alerta.getNome());

									enviarEmail(to, ASSUNTO_MENSAGEM,  message.toString(), DE, alerta, now);

									LOG.info("E-mail enviado para o alerta"+alerta.getNome());
									alertaService.getAlertaDao().saveOrUpdate(alerta);
									LOG.info("Atualizado campo enviado do alerta:"+alerta.getNome()+","+alerta.getEnviado());
								} catch (MessagingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						if(ANUAL.equalsIgnoreCase(repetir)){
							gc.add(Calendar.YEAR, 1);

							if(now.after(gc.getTime())){
								try {
									LOG.info("Enviando e-mail para o alerta"+alerta.getNome());

									enviarEmail(to, ASSUNTO_MENSAGEM,  message.toString(), DE, alerta, now);

									LOG.info("E-mail enviado para o alerta"+alerta.getNome());
									alertaService.getAlertaDao().saveOrUpdate(alerta);
									LOG.info("Atualizado campo enviado do alerta:"+alerta.getNome()+","+alerta.getEnviado());
								} catch (MessagingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}						
						}					
					}
				}
			}
		}catch (Exception e) {
			LOG.info("Ocorreu algum erro ao executar o job \n");
			e.printStackTrace();
		}
	}
	  
	  private void enviarEmail(String to, String subject, String message , String from, Alerta alerta, Date now) throws MessagingException{
			postMail(from, to, subject, message.toString());
			alerta.setEnviado(now);
			alertaService.getAlertaDao().saveOrUpdate(alerta);
	  }
	  
	  private void postMail(String from, String to, String subject, String message) throws MessagingException
	  {
		  Properties props = new Properties();  

		  // quem estiver utilizando um SERVIDOR PROXY descomente essa parte e atribua as propriedades do SERVIDOR PROXY utilizado  
		  /* 
          props.setProperty("proxySet","true"); 
          props.setProperty("socksProxyHost","192.168.155.1"); // IP do Servidor Proxy 
          props.setProperty("socksProxyPort","1080");  // Porta do servidor Proxy 
		   */  

		  props.put("mail.transport.protocol", "smtp"); //define protocolo de envio como SMTP  
		  props.put("mail.smtp.starttls.enable","true");   
		  props.put("mail.smtp.host", SMTP_SERVER); //server SMTP do GMAIL  
		  props.put("mail.smtp.auth", "true"); //ativa autenticacao  
		  props.put("mail.smtp.user", from); //usuario ou seja, a conta que esta enviando o email (tem que ser do GMAIL)  
		  props.put("mail.debug", "true");  
		  props.put("mail.smtp.port", SMTP_SERVER_PORT); //porta  
		  props.put("mail.smtp.socketFactory.port", SMTP_SERVER_PORT); //mesma porta para o socket  
		  props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");  
		  props.put("mail.smtp.socketFactory.fallback", "false");  

		  //Cria um autenticador que sera usado a seguir  
		  SimpleAuth auth = null;  
		  auth = new SimpleAuth ("tccpucsp2011@gmail.com","agenda2011");  

		  //Session - objeto que ira realizar a conexão com o servidor  
		  /*Como há necessidade de autenticação é criada uma autenticacao que 
		   * é responsavel por solicitar e retornar o usuário e senha para  
		   * autenticação */  
		  Session session = Session.getDefaultInstance(props, auth);  
		  session.setDebug(true); //Habilita o LOG das ações executadas durante o envio do email  

		  //Objeto que contém a mensagem  
		  Message msg = new MimeMessage(session);  

		  try {  
			  //Setando o destinatário  
			  msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));  
			  //Setando a origem do email  
			  msg.setFrom(new InternetAddress(from));  
			  //Setando o assunto  
			  msg.setSubject(subject);  
			  //Setando o conteúdo/corpo do email  
			  msg.setContent(message,"text/plain");  

		  } catch (Exception e) {  
			  System.out.println(">> Erro: Completar Mensagem");  
			  e.printStackTrace();  
		  }  

		  //Objeto encarregado de enviar os dados para o email  
		  Transport tr;  
		  try {  
			  tr = session.getTransport("smtp"); //define smtp para transporte  
			  /* 
			   *  1 - define o servidor smtp 
			   *  2 - seu nome de usuario do gmail 
			   *  3 - sua senha do gmail 
			   */  
			  tr.connect(SMTP_SERVER, auth.getUserName(), auth.getPassword());  
			  msg.saveChanges(); // don't forget this  
			  //envio da mensagem  
			  tr.sendMessage(msg, msg.getAllRecipients());  
			  tr.close();  
		  } catch (Exception e) {  
			  // TODO Auto-generated catch block  
			  System.out.println(">> Erro: Envio Mensagem");  
			  e.printStackTrace();  
		  }  
	  }	
	  //clase que retorna uma autenticacao para ser enviada e verificada pelo servidor smtp  
	  class SimpleAuth extends Authenticator {  
	      public String username = null;  
	      public String password = null;  
	    
	    
	      public SimpleAuth(String user, String pwd) {  
	          username = user;  
	          password = pwd;  
	      }  
	    
	      protected PasswordAuthentication getPasswordAuthentication() {  
	          return new PasswordAuthentication (username,password);  
	      }  
	      
	      private String getUserName(){
	    	  return username;
	      }
	     
	      private String getPassword(){
	    	  return password;
	      }
	  }
}
