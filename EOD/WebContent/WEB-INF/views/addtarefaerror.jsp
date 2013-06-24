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
 </script>

<h3>Adição de tarefa</h3>	
<form:form action="_adicionartarefaerro.do" id="formulario" commandName="tarefa" method="post">
	<table>
		<form:hidden path="id"/>
		<tr>
			<td>nome:</td>
			<td><form:input path="nome" disabled="true"/></td>
		</tr>	
		<tr>
			<td>perfil:</td>
			<td>
	            <form:select path="perfil" disabled="true">
		            <form:options items="${perfis}" itemValue="id" itemLabel="nome" />
           		</form:select>				
			</td>
		</tr>
		<tr>
			<td>motivacao:</td>
			<td>
				<form:select path="motivacao" onchange="showhidefieldMotivacao(this.value);" disabled="true">
					<form:option value="AD" label="Agendada" />
					<form:option value="AR" label="Agendar" />
				</form:select>
			</td>
		</tr>
		<tr>
			<td>duracao:</td>
			<td><form:input path="duracao" disabled="true"/></td>
		</tr>
		<tr>
			<td>divisivel:</td>
			<td>
				<form:select path="divisivel" disabled="true">
					<form:option value="S" label="Sim" />
					<form:option value="N" label="Não" />
				</form:select>
			</td>           		
		</tr>
		<tr>
			<td>descricao:</td>
			<td><form:textarea path="descricao" disabled="true"/></td>
		</tr>	
		<tr id="trDtData"> 
			<td>Data:</td>
			<td><form:input path="data" name="dateData" id="dateData" disabled="true" onmouseover="
																					$('#dateData').datetimepicker({
																							timeFormat: 'hh:mm',
																							dateFormat: 'dd/mm/yy',
																							separator: ' '
																					});	
																					"/></td>
		</tr>
		<tr id="trDtInicio">
			<td>dataInicio:</td>
			<td><form:input path="dataInicio" name="dateInicio" id="dateInicio" disabled="true" onmouseover="
																					$('#dateInicio').datetimepicker({
																							timeFormat: 'hh:mm',
																							dateFormat: 'dd/mm/yy',
																							separator: ' '
																					});	
																					"/></td>
		</tr>	
		<tr id="trDtFim">
			<td>dataFim:</td>
			<td><form:input path="dataFim" name="dateFim" id="dateFim" disabled="true" onmouseover="
																					$('#dateFim').datetimepicker({
																							timeFormat: 'hh:mm',
																							dateFormat: 'dd/mm/yy',
																							separator: ' '
																					});																				
																					"/></td>
		</tr>
		<tr>
			<td><input class="selecaoForm" type="submit" value="Editar"/></td>
		</tr>
	</table>
	<FONT color="red"><core:out value="Erro : ${erro}"></core:out>  </FONT><br/>
	<FONT color="red"><core:out value="ID : ${ID}"></core:out>  </FONT><br/>
	<FONT color="red"><core:out value="Nome : ${Nome}"></core:out>  </FONT><br/>
	<FONT color="red"><core:out value="Data Inicio : ${DataInicio}"></core:out>  </FONT><br/>
	<FONT color="red"><core:out value="Data Fim : ${DataFim}"></core:out>  </FONT><br/>
	<FONT color="red"><form:errors path="nome" /></FONT><br/>
	<FONT color="red"><form:errors path="perfil" /></FONT><br/>
	<FONT color="red"><form:errors path="motivacao" /></FONT><br/>
	<FONT color="red"><form:errors path="dataInicio" /></FONT><br/>
	<FONT color="red"><form:errors path="dataFim" /></FONT><br/>
	<FONT color="red"><form:errors path="duracao" /></FONT><br/>
	<FONT color="red"><form:errors path="divisivel" /></FONT><br/>
	<FONT color="red"><form:errors path="descricao" /></FONT><br/>
</form:form>