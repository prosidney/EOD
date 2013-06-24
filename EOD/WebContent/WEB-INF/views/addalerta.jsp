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

<h3>Adição de alerta</h3>	
<form:form action="_salvaralerta.do" id="formulario" commandName="alerta" method="post">
	<table>
		<form:hidden path="id"/>
		<tr>
			<td>nome:</td>
			<td><form:input path="nome" onKeyUp="enforceMaxLength(this,45);"/></td>
		</tr>	
		<tr>
			<td>perfil:</td>
			<td>
	            <form:select path="perfil">
		            <form:options items="${perfis}" itemValue="id" itemLabel="nome" />
           		</form:select>				
			</td>
		</tr>
		<tr>
			<td>Repetir:</td>
			<td>
				<form:select path="repetir">
					<form:option value="N" label="Não" />
					<form:option value="S" label="Semanal" />
					<form:option value="M" label="Mensal" />
					<form:option value="A" label="Anual" />
				</form:select>
			</td>
		</tr>	
		<tr>
			<td>Tarefa:</td>
			<td>
				<form:select path="tarefa">
		            <form:options items="${tarefas}" itemValue="id" itemLabel="nome" />
           		</form:select>		
			</td>
		</tr>	
		<tr> 
			<td>Data:</td>
			<td><form:input path="data" name="dateData" id="dateData" onmouseover="
																					$('#dateData').datetimepicker({
																							timeFormat: 'hh:mm',
																							dateFormat: 'dd/mm/yy',
																							separator: ' '
																					});	
																					"/></td>
		</tr>
		<tr>
			<td><input class="selecaoForm" type="submit" value="Enviar"/></td>
			<td><input class="selecaoForm" type="reset" value="Limpar"/></td>
		</tr>
	</table>
	<FONT color="red"><core:out value="${erro}"/></FONT><br/>
	<FONT color="red"><form:errors path="nome" /></FONT><br/>
	<FONT color="red"><form:errors path="perfil" /></FONT><br/>
	<FONT color="red"><form:errors path="repetir" /></FONT><br/>
	<FONT color="red"><form:errors path="tarefa" /></FONT><br/>
	<FONT color="red"><form:errors path="data" /></FONT><br/>
</form:form>