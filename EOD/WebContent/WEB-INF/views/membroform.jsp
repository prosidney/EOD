<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Spring3Example</title>
</head>
<body>
<h3>Cadastro de novos membros</h3>
<form:form action="membroform.ney"  commandName="membroForm">
	<table>
		<tr>
			<td>Email:</td>
			<td><form:input path="email" /></td>
		</tr>	
		<tr>
			<td>Nome:</td>
			<td><form:input path="nome" /></td>
		</tr>
		<tr>
			<td>Sobrenome:</td>
			<td><form:input path="sobrenome" /></td>
		</tr>
		<tr>
			<td>Idade:</td>
			<td><form:input path="idade" /></td>
		</tr>
		<tr>
			<td>MSN:</td>
			<td><form:input path="msn" /></td>
		</tr>
		<tr>
			<td>Cidade:</td>
			<td><form:input path="cidade" /></td>
		</tr>	
		<tr>
			<td>Orkut:</td>
			<td><form:input path="orkut" /></td>
		</tr>
		<tr>
			<td>Facebook:</td>
			<td><form:input path="facebook" /></td>
		</tr>
		<tr>
			<td>Usuário:</td>
			<td><form:input path="usuario" /></td>
		</tr>	
		<tr>
			<td>Relacionamento:</td>
			<td>
	            <form:select path="relacionamentoId">
		            <form:options items="${relacionamentos}" itemValue="relacionamentoId" itemLabel="nome" />
	            </form:select>	
			</td>
		</tr>			
		<tr>
			<td>Senha:</td>
			<td><form:password path="senha" /></td>
		</tr>	
		<tr>
			<td><input type="submit" value="Submit" /></td>
		</tr>
	</table>
	<FONT color="red"><form:errors path="nome" /></FONT><br/>
	<FONT color="blue"><form:errors path="sobrenome" /></FONT><br/>
	<FONT color="green"><form:errors path="email" /></FONT><br/>
	<FONT color="yellow"><form:errors path="relacionamentoId" /></FONT>
	<FONT color="gray"><form:errors path="usuario" /></FONT><br/>
	<FONT color="pink"><form:errors path="senha" /></FONT><br/>
</form:form>
</body>
</html>