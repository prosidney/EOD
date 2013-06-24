$(document).ready(function () {
/* 		$("#conteudo_mostrar").load("home.do", function(response, status, xhr) {
			  if (status == "error") {
			    var msg = "Sorry but there was an error: ";
			    $("#conteudo_mostrar").html(msg + xhr.status + " " + xhr.statusText);
			  }else{
				carregaCalendario();
			  }
		}); */
	$('.selecao').click(function() {
		//alert('Handler for .click() called.');
		$("#conteudo_mostrar").load("_editarmembroform.do", function(response, status, xhr) {
			  if (status == "error") {
			    var msg = "Sorry but there was an error: ";
			    $("#conteudo_mostrar").html(msg + xhr.status + " " + xhr.statusText);
			  }
			});
	});
	$('.selecaoHome').click(function() {
		//alert('Handler for .click() called.');
		$("#conteudo_mostrar").load("_home.do", function(response, status, xhr) {
			  if (status == "error") {
			    var msg = "Sorry but there was an error: ";
			    $("#conteudo_mostrar").html(msg + xhr.status + " " + xhr.statusText);
			  }else{
				carregaCalendario();
			  }
			});
	});
	$('.listarTarefas').click(function() {
		//alert('Handler for .click() called.');
		$("#conteudo_mostrar").load("_listartarefas.do", function(response, status, xhr) {
			  if (status == "error") {
			    var msg = "Sorry but there was an error: ";
			    $("#conteudo_mostrar").html(msg + xhr.status + " " + xhr.statusText);
			  }
			});
	});		
	$('.adicionarTarefa').click(function() {
		//alert('Handler for .click() called.');
		$("#conteudo_mostrar").load("_adicionartarefa.do", function(response, status, xhr) {
			  if (status == "error") {
			    var msg = "Sorry but there was an error: ";
			    $("#conteudo_mostrar").html(msg + xhr.status + " " + xhr.statusText);
			  }else{
				carregaCalendario();
			  }
			});
	});
	$('.adicionarMeta').click(function() {
		//alert('Handler for .click() called.');
		$("#conteudo_mostrar").load("_adicionarmeta.do", function(response, status, xhr) {
			  if (status == "error") {
			    var msg = "Sorry but there was an error: ";
			    $("#conteudo_mostrar").html(msg + xhr.status + " " + xhr.statusText);
			  }else{
				carregaCalendario();
			  }
			});
	});	
	$('.adicionarPerfil').click(function() {
		//alert('Handler for .click() called.');
		$("#conteudo_mostrar").load("_adicionarperfil.do", function(response, status, xhr) {
			  if (status == "error") {
			    var msg = "Sorry but there was an error: ";
			    $("#conteudo_mostrar").html(msg + xhr.status + " " + xhr.statusText);
			  }else{
				carregaCalendario();
			  }
			});
	});	
	$('.listarPerfis').click(function() {
		//alert('Handler for .click() called.');
		$("#conteudo_mostrar").load("_listarperfis.do", function(response, status, xhr) {
			  if (status == "error") {
			    var msg = "Sorry but there was an error: ";
			    $("#conteudo_mostrar").html(msg + xhr.status + " " + xhr.statusText);
			  }else{
				carregaCalendario();
			  }
			});
	});		
	$('.mudarPerfil').click(function() {
		//alert('Handler for .click() called.');
		$("#conteudo_mostrar").load("_mudarperfil.do", function(response, status, xhr) {
			  if (status == "error") {
			    var msg = "Sorry but there was an error: ";
			    $("#conteudo_mostrar").html(msg + xhr.status + " " + xhr.statusText);
			  }
		});
	});		
	
	
});
