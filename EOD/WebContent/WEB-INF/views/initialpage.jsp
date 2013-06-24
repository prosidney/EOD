<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Spring3Example</title>
</head>
<body>
<h3>Bem vindo <core:out value="${membro.nome}" /></h3>
<core:out value="Usuário: ${membro.usuario} ${membro.sobrenome}" /><br/>
<core:out value="Idade: ${membro.idade}" /><br/>
<core:out value="Relacionamento: ${membro.relacionamento.nome}" />
<br/><br/><br/><br/>
<h3>Galeras do membro</h3>
 <core:forEach var="galera" items="${membro.galeras1}">
	<core:out value="Galera: ${galera.nome}" /><br/>
</core:forEach>
<table>
	<tr>
		<td><a href="loginform.ney">Back</a></td>
	</tr>
</table>
</body>
</html>