/* 	$('.adicionarTarefa').click(function() {

		//alert('Handler for .click() called.');
	}); */
	
function funcaoTarefa(acao, tarefaId){
	if(acao == 'edit'){
		var action = '_editartarefa.do';
	}else if(acao == 'delete'){
		var action = '_deletaretarefa.do';
	}
	
	$("#conteudo_mostrar").load(action,{acc:acao, idTarefa:tarefaId}, function(response, status, xhr) {
		if (status == "error") {
			var msg = "Sorry but there was an error: ";
			$("#conteudo_mostrar").html(msg + xhr.status + " " + xhr.statusText);
		}
	});
	
}
function funcaoMeta(acao, metaId){
	if(acao == 'edit'){
		var action = '_editarmeta.do';
	}else if(acao == 'delete'){
		var action = '_deletarmeta.do';
	}
	
	$("#conteudo_mostrar").load(action,{acc:acao, idMeta:metaId}, function(response, status, xhr) {
		if (status == "error") {
			var msg = "Sorry but there was an error: ";
			$("#conteudo_mostrar").html(msg + xhr.status + " " + xhr.statusText);
		}
	});
	
}
function funcaoAlerta(acao, alertaId){
	if(acao == 'edit'){
		var action = '_editaralerta.do';
	}else if(acao == 'delete'){
		var action = '_deletaralerta.do';
	}
	
	$("#conteudo_mostrar").load(action,{acc:acao, idAlerta:alertaId}, function(response, status, xhr) {
		if (status == "error") {
			var msg = "Sorry but there was an error: ";
			$("#conteudo_mostrar").html(msg + xhr.status + " " + xhr.statusText);
		}
	});
	
}
function addAlertaTarefa(tarefaId){
	$("#conteudo_mostrar").load('_adicionaAlertaTarefa.do',{idTarefa:tarefaId}, function(response, status, xhr) {
		if (status == "error") {
			var msg = "Sorry but there was an error: ";
			$("#conteudo_mostrar").html(msg + xhr.status + " " + xhr.statusText);
		}
	});
}
function funcaoPerfil(acao, perfilId){
	if(acao == 'edit'){
		var action = '_editarperfil.do';
	}else if(acao == 'delete'){
		var action = '_deletarperfil.do';
	}
	$("#conteudo_mostrar").load(action,{acc:acao, idPerfil:perfilId}, function(response, status, xhr) {
		if (status == "error") {
			var msg = "Sorry but there was an error: ";
			$("#conteudo_mostrar").html(msg + xhr.status + " " + xhr.statusText);
		}
	});
}