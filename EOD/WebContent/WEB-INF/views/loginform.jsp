<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Spring3Example</title>
	</head>
<body>	
	<center>
		<form:form action="_login.do" commandName="loginForm">
		<fieldset>
	    <legend>Login</legend>
			<table>
				<tr>
					<td>Usuário</td>
					<td><form:input path="userName" /></td>
				</tr>
				<tr>
					<td>Senha:</td>
					<td><form:password path="password" /></td>
				</tr>
				<tr>
					<td><input type="submit" value="Entrar" /></td>
					<td><a href="membroform.ney">Cadastrar</a></td>
				</tr>
			</table>
			<FONT color="red"><form:errors path="userName" /></FONT>
			<br />
			<FONT color="red"><form:errors path="password" /></FONT>
		</fieldset>
		</form:form>
	</center>
</body>
</html>