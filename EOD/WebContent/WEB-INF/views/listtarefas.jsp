<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
  $(document).ready(function(){
	  	recarregar = <%=request.getAttribute("recarregarMenu")%> 
	  	//alert(recarregar);
	  	
	  	if(recarregar){
	  		$("#conteudo_menu").load("_recarregaMenu.do", function(response, status, xhr) {
				  if (status == "error") {
				    var msg = "Sorry but there was an error: ";
				    $("#conteudo_menu").html(msg + xhr.status + " " + xhr.statusText);
				  }
			});
	  	}
	}); 
 </script>
 
<div id="listagemAlertas">
	<table summary="Submitted table designs">
		<caption id="format">Meus alertas (Perfil <c:out value="${perfilAtual.nome}"/>)</caption>
		<thead id="format">
			<tr>
				<th scope="col"><c:out value="ID:"/></th>
				<th scope="col"><c:out value="Nome:"/></th>
				<th scope="col"><c:out value="Data:"/></th>
				<th scope="col"><c:out value="Tarefa:"/></th>
				<th scope="col"><c:out value="Editar/Excluir"/></th>				
			</tr>
		</thead>
		<tbody id="format">
			<c:forEach var="alerta" items="${alertas}"> 
				<tr>
					<th scope="row">
						<c:out value="${alerta.id}"/>
					</th>
					<td>
						<c:out value="${alerta.nome}"/>
					</td>
					<td>
						<c:out value="${alerta.data}"/>
					</td>
					<td>
						<c:out value="${alerta.tarefa.nome}"/>
					</td>					
					<td>
						<img src="../images/1319057781_edit.png" onclick="funcaoAlerta('edit','${alerta.id}')">
						<img src="../images/1319057544_button_delete.png" onclick="funcaoAlerta('delete','${alerta.id}')" >
					</td>					
				</tr>
			</c:forEach>					
		</tbody>
	</table>	
</div>
<div id="listagemTarefas">
	<table summary="Submitted table designs">
		<caption id="format">Minhas tarefas</caption>
		<thead id="format">
			<tr>
				<th scope="col"><c:out value="ID:"/></th>
				<th scope="col"><c:out value="Nome:"/></th>
				<th scope="col"><c:out value="Data:"/></th>
				<th scope="col"><c:out value="Data Fim:"/></th>
				<th scope="col"><c:out value="Editar/Excluir/Alarme"/></th>				
			</tr>
		</thead>
		<tbody id="format">
			<c:forEach var="tarefa" items="${tarefas}"> 
				<tr>
					<th scope="row">
						<c:out value="${tarefa.id}"/>
					</th>
					<td>
						<c:out value="${tarefa.nome}"/>
					</td>
					<td>
						<c:out value="${tarefa.dataInicioStr}"/>
					</td>
					<td>
						<c:out value="${tarefa.dataFimStr}"/>
					</td>					
					<td>
						<img src="../images/1319057781_edit.png" onclick="funcaoTarefa('edit','${tarefa.id}')">
						<img src="../images/1319057544_button_delete.png" onclick="funcaoTarefa('delete','${tarefa.id}')" >
						<img src="../images/1320270070_chronometer.png" onclick="addAlertaTarefa('${tarefa.id}')" >						
					</td>					
				</tr>
			</c:forEach>					
		</tbody>
	</table>	
</div>
<div id="listagemMetas">
	<table summary="Submitted table designs">
		<caption id="format">Minhas metas</caption>
		<thead id="format">
			<tr>
				<th scope="col"><c:out value="ID:"/></th>
				<th scope="col"><c:out value="Nome:"/></th>
				<th scope="col"><c:out value="Até:"/></th>
				<th scope="col"><c:out value="Editar/Excluir"/></th>				
			</tr>
		</thead>
		<tbody id="format">
			<c:forEach var="meta" items="${metas}"> 
				<tr>
					<th scope="row">
						<c:out value="${meta.id}"/>
					</th>
					<td>
						<c:out value="${meta.nome}"/>
					</td>
					<td>
						<c:out value="${meta.ate}"/>
					</td>
					<td>
						<img src="../images/1319057781_edit.png" onclick="funcaoMeta('edit','${meta.id}')">
						<img src="../images/1319057544_button_delete.png" onclick="funcaoMeta('delete','${meta.id}')" >
					</td>					
				</tr>
			</c:forEach>					
		</tbody>
	</table>	
</div>