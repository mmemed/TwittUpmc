function inscrip(formulaire){
	var login=formulaire.login.value;
	var pass=formulaire.password.value;
	var nom=formulaire.nom.value;
	var prenom=formulaire.prenom.value;
	var mail=formulaire.mail.value;
	
	var ok=verif_formulaire(login,pass,nom,prenom,mail);
	if(ok==true ) {
		registerUser(login,pass,nom,prenom,mail);
		
	 }	
 }
 
 function verif_formulaire(login,pass,nom,prenom,mail){
 	 var reg=/.*@.+\..+/;
	 
	 if(login.length==0){
		 func_error("login obligatoire");
		 return false;
	 }
	 if(pass.length==0){
		 func_error("mot de passe obligatoire");
		 return false;
	 }
	 if(nom.length==0){
		 func_error("nom obligatoire");
		 return false;
	 }
	 if(prenom.length==0){
		 func_error("prÃ©nom obligatoire");
		 return false;
	 }
	 if(pass.length==0){
		 func_error("mail de passe obligatoire");
		 return false;
	 }
	 if(!reg.test(mail)){
		 func_error("le mail est incorrect");
		 return false;
	 }
	 return true;
 }



function registerUser(login,pass,nom,prenom,mail){
	
	$.ajax( {
			type: "GET",
			url: "http://li328.lip6.fr:8280/ahmed/user/create",
			data: "login="+login+"&password="+pass+"&nom="+nom+"&prenom="+prenom+"&mail="+mail,
			dataType: "json",
			success: traiteReponseRegister,
			error:function(XHR,textStatus,errorThrown){ alert(XHR+" "+textStatus+" "+errorThrown);}
	
						
		});
}


function traiteReponseRegister(json){
	if(json.error_code!=undefined){
		func_error("l'utilisateur n'a pas pu être crée");
	}
	else
	{
		$("#formulaire").html("Compte cr&eacute;&eacute;");
		window.location.href="index.html";
	}
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
	