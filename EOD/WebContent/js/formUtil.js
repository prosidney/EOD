function showhidefieldMotivacao(motivacao){
	//alert(motivacao);
	
	
	if(motivacao == 'AD'){
		document.getElementById("dateInicio").disabled = true;
		document.getElementById("dateInicio").value = "";
		
		document.getElementById("dateFim").disabled = true;
		document.getElementById("dateFim").value = "";
		
		document.getElementById("dateData").disabled = false;
	}else{
		document.getElementById("dateInicio").disabled = false;
		
		document.getElementById("dateFim").disabled = false;
		
		document.getElementById("dateData").disabled = true;
		document.getElementById("dateData").value = "";
	}
/*	switch (mySel){
		case '1': document.getElementById("hideablearea").style.visibility = "hidden";break;
		case '2': document.getElementById("hideablearea").style.visibility = "visible";
		case '3': document.getElementById("hideablearea").style.visibility = "visible";break;
		case '4': document.getElementById("hideablearea").style.visibility = "hidden";
   }*/
	
}