
function connex(formulaire){
	var login=formulaire.login.value;
	var pass=formulaire.pass.value;
	var ok=verif_formulaire(login,pass);

	if(ok==true ) {
		connecte(login,pass);
	 }	
 }
 
 function verif_formulaire(login,pass){
	 
	 if(login.length==0){
		 func_error("login obligatoire");
		 return false;
	 }
	 if(pass.length==0){
		 func_error("mot de passe obligatoire");
		 return false;
	 }
	 return true;
 }
 
 function func_error(msg){
	var msg_box="<div id='msg_err_connexion'>"+msg+"</div>";
	var tab=$("#msg_err_connexion");
	if(tab.length==0){
	$("#formulaire").prepend(msg_box);}
	else{
		tab.replaceWith(msg_box);
		}
	$("#msg_err_connexion").css('color','red');
	}
	
	
function connecte(login,pass){
	$.ajax({
		type:"GET",
		url:"http://li328.lip6.fr:8280/ahmed/user/login",                        
		data:"login="+login+"&password="+pass,
		dataType:"json",
		success:traiteReponseConnexion,
		error:function(XHR,textStatus,errorThrown){ alert(XHR+" "+textStatus+" "+errorThrown);}
	});

}

function traiteReponseConnexion(json){
	
	if(json.error_code!=undefined){
		func_error("utilisateur inexistant");
	}
	else{
			
			window.location.href="accueil.jsp?id="+json.id+"&login="+json.login+"&key="+json.key;
	
			
				
		}
}

