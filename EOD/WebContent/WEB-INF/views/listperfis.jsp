<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="listagemPerfis">
	<table summary="Submitted table designs">
		<caption id="format">Meus perfis</caption>
		<thead id="format">
			<tr>
				<th scope="col"><c:out value="ID:"/></th>
				<th scope="col"><c:out value="Nome:"/></th>
				<th scope="col"><c:out value="Editar/Excluir"/></th>				
			</tr>
		</thead>
		<tbody id="format">
			<c:forEach var="perfil" items="${perfis}"> 
				<tr>
					<th scope="row">
						<c:out value="${perfil.id}"/>
					</th>
					<td>
						<c:out value="${perfil.nome}"/>
					</td>
					<td>
						<img src="../images/1319057781_edit.png" onclick="funcaoPerfil('edit','${perfil.id}')">
						<img src="../images/1319057544_button_delete.png" onclick="funcaoPerfil('delete','${perfil.id}')" >
					</td>					
				</tr>
			</c:forEach>					
		</tbody>
	</table>	
</div>