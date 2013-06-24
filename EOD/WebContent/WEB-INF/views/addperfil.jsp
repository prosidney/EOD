<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>

<!--<script type="text/javascript" src="../jquery/jquery.js"></script> -->

<script type="text/javascript">
  $(document).ready(function(){
		var action = $('form').attr('action');
        $('form').attr('action','javascript:void(0);');
        $('form').submit(function(){
                 $.post(action ,jQuery("form").serialize(),  function(data){
                      $('#conteudo_mostrar').html(data);
                      $('#conteudo_mostrar').show();
                 });
                return null;
        });
	}); 
  
	function enforceMaxLength(obj, maxLength){  
  		if (obj.value.length > maxLength) {
	  		alert('Número máximo de caracteres atingido, verifique o texto.');
  			obj.value = obj.value.substring(0, maxLength);
  		}
  	}
 </script>

<h3>Adição de perfil</h3>	
<form:form action="_salvarperfil.do" id="formulario" commandName="perfil" method="post">
	<table>
		<form:hidden path="id"/>
		<tr>
			<td>nome:</td>
			<td><form:input path="nome" onKeyUp="enforceMaxLength(this,45);"/></td>
		</tr>	
		<tr>
			<td>usuario:</td>
			<td>
	            <form:select path="usuario">
		            <form:options items="${usuarios}" itemValue="id" itemLabel="nome"/>
           		</form:select>				
			</td>
		</tr>
		<tr>
			<td><input class="selecaoForm" type="submit" value="Enviar"/></td>
			<td><input class="selecaoForm" type="reset" value="Limpar"/></td>
		</tr>
	</table>
	<FONT color="red"><core:out value="${erro}"/></FONT><br/>
	<FONT color="red"><form:errors path="nome" /></FONT><br/>
	<FONT color="red"><form:errors path="usuario" /></FONT><br/>
</form:form>