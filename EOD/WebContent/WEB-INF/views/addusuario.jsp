<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Inclusão de usuário</title>

<script type="text/javascript">
	function enforceMaxLength(obj, maxLength){  
  		if (obj.value.length > maxLength) {
	  		alert('Número máximo de caracteres atingido, verifique o texto.');
  			obj.value = obj.value.substring(0, maxLength);
  		}
  	}
 </script>
</head>
<body>
<center>
<h3>Inclusão novo usuário</h3>	
<form:form action="salvarnovousuario.do" id="formulario" commandName="usuario" method="post">
	<table>
		<form:hidden path="id"/>
		<tr>
			<td>Nome:</td>
			<td><form:input path="nome" onKeyUp="enforceMaxLength(this,45);"/></td>
		</tr>	
		<tr>
			<td>E-mail:</td>
			<td><form:input path="email" /></td>
		</tr>
		<tr>
			<td>Senha:</td>
			<td><form:input path="senha" /></td>
		</tr>
		<tr>
			<td><input class="selecaoForm" type="submit" value="Enviar"/></td>
			<td><input class="selecaoForm" type="reset" value="Limpar"/></td>
		</tr>
	</table>
	<FONT color="red"><core:out value="${erro}"/></FONT><br/>
	<FONT color="red"><form:errors path="nome" /></FONT><br/>
	<FONT color="red"><form:errors path="email" /></FONT><br/>
	<FONT color="red"><form:errors path="senha" /></FONT><br/>
</form:form>
</center>
</body>
</html>