/* 	$('.adicionarTarefa').click(function() {

		//alert('Handler for .click() called.');
	}); */
	
function escolhePerfil(idPerfil){
	$("#conteudo_mostrar").load('_escolheperfil.do',{idPerfil:idPerfil}, function(response, status, xhr) {
		if (status == "error") {
			var msg = "Sorry but there was an error: ";
			$("#conteudo_mostrar").html(msg + xhr.status + " " + xhr.statusText);
		  }else{
				carregaCalendario();
		  }
	});
	
}