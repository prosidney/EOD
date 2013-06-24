/**
 * 
 */
function carregaCalendario(){
	$.getJSON('_carregarEventos.do', function(data) {
	  var tarefas = new Array();
	  var metas = new Array();
	  var alertas = new Array();
	
	  $.each(data, function(key, val) {
		  	if(key == 'tarefas'){
		  		for (var count = 0 ; count < val.length ; count++){
		  			var indice = val[count];
		  			tarefas[count] = indice;
		  		}
		  	}
		  	if(key == 'metas'){
		  		for (var count = 0 ; count < val.length ; count++){
		  			var indice = val[count];
		  			metas[count] = indice;
		  		}
		  	}
		  	if(key == 'alertas'){
		  		for (var count = 0 ; count < val.length ; count++){
		  			var indice = val[count];
		  			alertas[count] = indice;
		  		}
		  	}		  	
		}
	  );
	  
	  $('#calendar').fullCalendar({
			header: {
				left: 'prev,next today',
				center: 'title',
				right: 'month,basicWeek,basicDay'
			},
			dayNames:['Domingo', 'Segunda', 'Terça', 'Quarta',
			          'Quinta', 'Sexta', 'Sabado'],					          
			dayNamesShort:['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'S\u00E1b'],
			monthNames: ['Janeiro','Fevereiro','Março','Abril','Maio','Junho','Julho',
			             'Agosto','Setembro','Outubro','Novembro','Dezembro'],  
			monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set',  
							  'Out','Nov','Dez'],  
			editable: false,
			events: [/*
				{
					title: 'All Day Event',
					start: new Date(y, m, 1)
				},
				{
					title: 'Long Event',
					start: new Date(y, m, d-5),
					end: new Date(y, m, d-2)
				},
				{
					id: 999,
					title: 'evento repetido',
					start: new Date(y, m, d-3, 16, 0),
					allDay: false
				},
				{
					id: 999,
					title: 'evento repetido',
					start: new Date(y, m, d+4, 16, 0),
					allDay: false
				},
				{
					title: 'derrr',
					start: new Date(y, m, d, 10, 30),
					allDay: false
				},
				{
					title: 'Lunch',
					start: new Date(y, m, d, 12, 0),
					end: new Date(y, m, d, 14, 0),
					allDay: false
				},
				{
					title: 'Birthday Party',
					start: new Date(y, m, d+1, 19, 0),
					end: new Date(y, m, d+1, 22, 30),
					allDay: false	
				},
				{
					title: 'Click for Google',
					start: new Date(y, m, 28),
					end: new Date(y, m, 29),
					url: 'http://google.com/'
				}*/
			]/**,
		    eventClick: function(event, element) {

		        event.title = "CLICKED!";

		        $('#calendar').fullCalendar('updateEvent', event);

		    },
			dayClick: function(date, allDay, jsEvent, view) {

		        if (allDay) {
		            alert('Clicked on the entire day: ' + date);
		        }else{
		            alert('Clicked on the slot: ' + date);
		        }

		        alert('Coordinates: ' + jsEvent.pageX + ',' + jsEvent.pageY);

		        alert('Current view: ' + view.name);

		        // change the day's background color just for fun
		        $(this).css('background-color', 'red');

		    }/**,
		    
		    eventMouseover: function( event, jsEvent, view ) { 
				alert("eventMouseover");
			}
		    */
		});
	  
		for (var count = 0 ; count < tarefas.length ; count++){
			var indice = tarefas[count];
			var dataFim = indice.dataFimD;
			
			$('#calendar').fullCalendar('renderEvent', {
				  title: indice.nome,
				  color: 'green',
				  start: new Date(indice.dataInicioD.ano, indice.dataInicioD.mes, indice.dataInicioD.dia),
				  end: new Date(indice.dataFimD.ano, indice.dataFimD.mes, indice.dataFimD.dia)
				}, true).fullCalendar('addEvent', {
				  title: indice.nome,
				  color: 'green',
				  start: new Date(indice.dataInicioD.ano, indice.dataInicioD.mes, indice.dataInicioD.dia),
				  end: new Date(dataFim.ano, dataFim.mes, dataFim.dia)
				}, true);
		}	  
		
		for (var count = 0 ; count < metas.length ; count++){
			var indice = metas[count];
			//var ate = indice.ateD;
			
			$('#calendar').fullCalendar('renderEvent', {
				  title: indice.nome,
				  color: 'blue',
				  start: new Date(indice.ate.ano, indice.ate.mes, indice.ate.dia),
				  end: new Date(indice.ate.ano, indice.ate.mes, indice.ate.dia)
				}, true).fullCalendar('addEvent', {
				  title: indice.nome,
				  color: 'blue',
				  start: new Date(indice.ate.ano, indice.ate.mes, indice.ate.dia),
				  end: new Date(indice.ate.ano, indice.ate.mes, indice.ate.dia)
				}, true);
		}			
		
		for (var count = 0 ; count < alertas.length ; count++){
			var indice = alertas[count];
			//var ate = indice.ateD;
			
			$('#calendar').fullCalendar('renderEvent', {
				  title: indice.nome,
				  color: 'red',
				  start: new Date(indice.data.ano, indice.data.mes, indice.data.dia),
				  end: new Date(indice.data.ano, indice.data.mes, indice.data.dia)
				}, true).fullCalendar('addEvent', {
				  title: indice.nome,
				  color: 'red',
				  start: new Date(indice.data.ano, indice.data.mes, indice.data.dia),
				  end: new Date(indice.data.ano, indice.data.mes, indice.data.dia)
				}, true);
		}		
	  
	});
}