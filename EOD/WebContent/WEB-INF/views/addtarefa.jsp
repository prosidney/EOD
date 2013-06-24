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
	function enforceNumericValue(obj){  
		 //  check for valid numeric strings	
		   {
			   var strValidChars = "0123456789";
			   var strChar;
			   var blnResult = true;
	
			  // if (strString.length == 0) return false;
	
			   //  test strString consists of valid characters listed above
			   for (i = 0; i < obj.value.length && blnResult == true; i++){
			      strChar = obj.value.charAt(i);
			      if (strValidChars.indexOf(strChar) == -1){
			         blnResult = false;
			         obj.value = obj.value.substring(0, obj.value.length-1);
			      }
			   }
			   //return blnResult;
		   }
  	}	
 </script>

<h3>Adição de tarefa</h3>	
<form:form action="_salvaretarefa.do" id="formulario" commandName="tarefa" method="post">
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
			<td>tipo:</td>
			<td>
				<form:select path="motivacao" onchange="showhidefieldMotivacao(this.value);">
					<form:option value="AD" label="Agendada" />
					<core:if test="${tarefa.id == 0}">
						<form:option value="AR" label="Agendar" />
					</core:if>
				</form:select>
			</td>
		</tr>
		<tr>
			<td>duracao:</td>
			<td><form:input path="duracao" onKeyUp="enforceNumericValue(this);"/></td>
		</tr>
		<tr>
			<td>divisivel:</td>
			<td>
				<form:select path="divisivel">
					<form:option value="S" label="Sim" />
					<form:option value="N" label="Não" />
				</form:select>
			</td>           		
		</tr>
		<tr>
			<td>descricao:</td>
			<td><form:textarea path="descricao" onKeyUp="enforceMaxLength(this,255);"/></td>
		</tr>	
		<tr id="trDtData"> 
			<td>Data:</td>
			<td><form:input path="data" name="dateData" id="dateData" onmouseover="
																					$('#dateData').datetimepicker({
																							timeFormat: 'hh:mm',
																							dateFormat: 'dd/mm/yy',
																							separator: ' '
																					});	
																					"/></td>
		</tr>
		<tr id="trDtInicio">
			<td>dataInicio:</td>
			<td><form:input path="dataInicio" name="dateInicio" id="dateInicio" disabled="${tarefa.deAteDesabilitado}" onmouseover="
																					$('#dateInicio').datetimepicker({
																							timeFormat: 'hh:mm',
																							dateFormat: 'dd/mm/yy',
																							separator: ' '
																					});	
																					"/></td>
		</tr>	
		<tr id="trDtFim">
			<td>dataFim:</td>
			<td><form:input path="dataFim" name="dateFim" id="dateFim" disabled="${tarefa.deAteDesabilitado}"  onmouseover="
																					$('#dateFim').datetimepicker({
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
	<FONT color="red"><form:errors path="motivacao" /></FONT><br/>
	<FONT color="red"><form:errors path="dataInicio" /></FONT><br/>
	<FONT color="red"><form:errors path="dataFim" /></FONT><br/>
	<FONT color="red"><form:errors path="duracao" /></FONT><br/>
	<FONT color="red"><form:errors path="divisivel" /></FONT><br/>
	<FONT color="red"><form:errors path="descricao" /></FONT><br/>
</form:form>