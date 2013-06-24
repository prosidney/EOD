<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="pt-BR">
<head>
	<title>EOD</title>
	<meta http-equiv="Content-Type" content="application/xhtml+xml; charset=UTF-8" />
	<meta name="description" content="The Perfect 2 Column Liquid Layout (left menu): No CSS hacks. SEO friendly. iPhone compatible." />
	<meta name="keywords" content="The Perfect 2 Column Liquid Layout (left menu): No CSS hacks. SEO friendly. iPhone compatible." />
	<meta name="robots" content="index, follow" />
	<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="../css/screen.css" media="screen" />
	
	<script type="text/javascript" src="../jquery/jquery.js"></script>
	<!--<script type="text/javascript" src="../js/jquery-1.4.4.min.js"></script>-->
	<script type="text/javascript" src="../js/jquery-ui-1.8.11.custom.min.js"></script>
	<link	type="text/css" href="../css/smoothness/jquery-ui-1.8.11.custom.css" rel="Stylesheet" />
 		
	<script type="text/javascript" src="../fullcalendar/fullcalendar.min.js"></script>
	<link 	rel='stylesheet' type='text/css' href='../fullcalendar/fullcalendar.css' /> 
	<link	rel='stylesheet' type='text/css' href='../fullcalendar/fullcalendar.print.css' media='print' />
	
	<!-- arquivos Ext JS padrão -->
	<link rel="stylesheet" type="text/css" href="/grid_json_spring/js/extjs/resources/css/ext-all.css" />
	<script src="../js/extjs/adapter/ext/ext-base.js"></script>
	<script src="../js/extjs/ext-all.js"></script>

 	<script src="../js/calendarioUtil.js"></script>
 	<script src="../js/listartarefas.js"></script>
	<script src="../js/escolheperfil.js"></script>
	<script src="../js/formUtil.js"></script>
	<script src="../js/actions.js"></script> 	 	 	
	
 	<script type="text/javascript" src="../js/jquery-ui-timepicker-addon.js"></script>
 	
 	<link 	rel='stylesheet' type='text/css' href='../css/datatable.css' /> 
</head>
<body>

<!--<div id="header">
	<p><a href="http://matthewjamestaylor.com/blog/perfect-multi-column-liquid-layouts" title="Perfect multi-column liquid layouts - iPhone compatible">&laquo; Back to the CSS article</a> by <a href="http://matthewjamestaylor.com">Matthew James Taylor</a></p>
	<h1>The Perfect 'Left Menu' 2 Column Liquid Layout (Percentage widths)</h1>
	<h2>No CSS hacks. SEO friendly. No Images. No JavaScript. Cross-browser &amp; iPhone compatible.</h2>
	<ul>
		<li><a href="http://matthewjamestaylor.com/blog/perfect-3-column.htm">3 Column <span>Holy Grail</span></a></li>
		<li><a href="http://matthewjamestaylor.com/blog/perfect-3-column-blog-style.htm">3 Column <span>Blog Style</span></a></li>
		<li><a href="http://matthewjamestaylor.com/blog/perfect-2-column-left-menu.htm" class="active">2 Column <span>Left Menu</span></a></li>
		<li><a href="http://matthewjamestaylor.com/blog/perfect-2-column-right-menu.htm">2 Column <span>Right Menu</span></a></li>
		<li><a href="http://matthewjamestaylor.com/blog/perfect-2-column-double-page.htm">2 Column <span>Double Page</span></a></li>
		<li><a href="http://matthewjamestaylor.com/blog/perfect-full-page.htm">1 Column <span>Full Page</span></a></li>
		<li><a href="http://matthewjamestaylor.com/blog/perfect-stacked-columns.htm">Stacked <span>columns</span></a></li>
	</ul>
	<p id="layoutdims">Measure columns in: <a href="http://matthewjamestaylor.com/blog/ultimate-2-column-left-menu-pixels.htm">Pixel widths</a> | <a href="http://matthewjamestaylor.com/blog/ultimate-2-column-left-menu-ems.htm">Em widths</a> | <strong>Percentage widths</strong></p>
</div>
 -->
<div class="colmask leftmenu">
	<div class="colleft">
		<div class="col1" id="conteudo_mostrar">
			<!-- Column 1 start -->
			<div id="listagemPerfis">
				<table summary="Submitted table designs">
					<caption id="format">Meus Perfis</caption>
					<thead id="format">
						<tr>
							<th scope="col"><c:out value="ID"/></th>
							<th scope="col"><c:out value="Nome"/></th>
						</tr>
					</thead>
					<tbody id="format">
						<c:forEach var="perfil" items="${usuario.perfis}"> 
							<tr>
								<th scope="row">
									<c:out value="${perfil.id}"/>
								</th>
								<td>
									<a href="#" onclick="escolhePerfil('${perfil.id}')"><c:out value="${perfil.nome}"/></a>
								</td>
							</tr>
						</c:forEach>					
					</tbody>
				</table>
			</div>
			<!-- Column 1 end -->
		</div>
		<div class="col2" id="conteudo_menu">
			<!-- Column 2 start -->
			<div id="ads">
				<a href="http://matthewjamestaylor.com">
					<img src="../images/mjt-125x125.gif" width="125" border="0" height="125" alt="Art and Design by Matthew James Taylor" />
				</a>
			</div>
			<br />
			<div id="botoes_acoes">
				<a href="#" class="selecaoHome">
					<img src="../images/calendar.png" alt="Página principal" />
				</a>
				<!-- 
			    <a href="#" class="selecao">
					<img src="../images/edit.png" alt="Editar Perfil" />
			    </a>
				 -->
			    <a href="#" class="listarTarefas" >
			  		<img src="../images/1319116589_taskbar.png" alt="Meus Eventos" />
			    </a>
			    <a href="#" class="adicionarTarefa">
			  		<img src="../images/1319063935_add_tarefa.png" alt="Adicionar tarefa" />
			    </a>	
			    <a href="#" class="adicionarMeta">
			  		<img src="../images/1319063940_add_meta.png" alt="Adicionar meta" />
			    </a>
			    <a href="#" class="listarPerfis">
			  		<img src="../images/1320691225_Profile.png" alt="Meus perfis" />
			    </a>    
			    <a href="#" class="adicionarPerfil">
			  		<img src="../images/1320691443_profile_add.png" alt="Adicionar perfil" />
			    </a>  
			    <!--
			    <a href="#" class="listarUsuarios">
			  		<img src="../images/1320691225_Profile.png" alt="Meus perfis" />
			    </a>       
			    -->      
				<a href="#" class="mudarPerfil">
			  		<img src="../images/1319132158_exchange.png" alt="mudar perfil" />
			    </a>
			    
			</div>
			<fieldset>
				  <legend><b>Usuario</b></legend>
				  
				  <!--
				  <h2>No CSS hacks</h2>				  
				  <p>The CSS used for this layout is 100% valid and hack free. To overcome Internet Explorer's broken box model, no horizontal padding or margins are used. Instead, this design uses percentage widths and clever relative positioning.</p>
				   -->
				  
				  
				  <table>
				  	<tr>
				  		<td>Nome:</td>
				  		<td><c:out value="${usuario.nome}" /></td>
				  	</tr>
					<tr>
				  		<td>E-mail:</td>
				  		<td><c:out value="${usuario.email}" /></td>
				  	</tr>
				  </table>
			</fieldset>
			<br /><br />
		
			<fieldset>
				  <legend><b>Próximas tarefas</b></legend>
				  <table summary="Submitted table designs">
						<tbody id="format">
							<c:forEach var="proximaTarefa" items="${proximasTarefas}"> 
							<tr>
								<th scope="row" style="background-color:#F4F4F4;" onmouseover="this.style.color='black'">
									<c:out value="${proximaTarefa.nome}"/>
								</th>
								<th scope="row" style="background-color:#F4F4F4;" onmouseover="this.style.color='black'">
									<c:out value="${proximaTarefa.data}"/>
								</th>								
							</tr>
							</c:forEach>					
						</tbody>
					</table>
			</fieldset>
			
			<!-- Column 2 end -->
		</div>
	</div>
</div>
<div id="footer">
  <p>EOD 2011 - Todos direitos reservados</p>
</div>
</body>
</html>
