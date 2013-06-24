<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
