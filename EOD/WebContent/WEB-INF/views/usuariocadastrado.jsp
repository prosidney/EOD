<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Usuário Cadastrado</title>
</head>
<body>
<center>
<h4>Usuario <c:out value="${novousuario.nome}"></c:out> cadastrado com sucesso  </h4>
<h5>Clique <a href="_login.do">AQUI</a> para entrar no sistema.  </h5>
</center>
</body>
</html>