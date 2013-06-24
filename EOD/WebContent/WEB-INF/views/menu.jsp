<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="../js/actions.js"></script> 	    
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