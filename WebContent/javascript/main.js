
//definie l'environnement

// fonction principal de la page index

function main(id,login,key){
	environnement=new Object();
	environnement.users=[];
	
	if((id!=undefined) && (login!=undefined) && (key!=undefined)){
		environnement.key=key;
		environnement.actif=new User(id,login);
		environnement.friend=[];
		environnement.idfriend=null;
		listfriend();
		Commentfriend();
		
	} 
	else{
		CommentUser();
	}
	
	genererDivConnexion();

 	

}


User.prototype.modifStatus=function(){
		if(this.contact){
			this.contact=false;
			}
			else { this.contact=true;}
}
			


//permet de switcher entrer l'onglet connexion et deconnexion	
function genererDivConnexion(){
	var user=environnement.actif;
	
	
	var disconnec="<a href='connexion.html'>connexion</a> <a href='inscription.html'>enregistrement</a>";

	if(user!=undefined){
		var connec=user.login+"<br /><a href='javascript:deconnexion()'><img src='image/logout.jpg' id='disconect' alt='logout' border='0'/></a>";
		$(".lien").html(connec);
		
	}
	else{
		
		$(".lien").html(disconnec);
			
		}
}		


// permet de stock√© les info de l'amis
 
function User(id,login) {       //prenom,nom,contact){
	this.id=id;
	this.login=login;


	if(environnement==undefined){
		environnement={};
	}
 
	if(environnement.users==undefined){
		environnement.users=[];
	}
 
	}

function Comment(text) {

	$.ajax({
		
		type:"GET",
		url:"http://li328.lip6.fr:8280/ahmed/comment/add",				
		data:"key="+environnement.key+"&text="+text,
		dataType:"json",
		success:CommentairesTraiteReponse,
		error:function(XHR,textStatus,errorThrown){ alert(XHR+" "+textStatus+" "+errorThrown);}
	})
}

function CommentairesTraiteReponse(json){
	var rep="";
	 if(json.error_code!=undefined){
			alert("probleme à la création du commentaire");
		}
		else{
				rep="<td>"+json["login_user"]+":</td><td><b>"+json["text"]+"</b><br />le:"+ json["date"]+"</td>";
				 
	                	
	                	$("table.comm").append('<tr>'+ rep +'</tr')
	                	window.refresh;
	            
		}
}


function deconnexion(){
	$.ajax({
		type:"GET",
		url:"http://li328.lip6.fr:8280/ahmed/user/logout",                        
		data:"key="+environnement.key,
		dataType:"json",
		success:traiteReponseDeconnexion,
		error:function(XHR,textStatus,errorThrown){ alert(XHR+" "+textStatus+" "+errorThrown);}
	});

}

function traiteReponseDeconnexion(json){
	
	if(json.error_code!=undefined){
		alert("probleme à la deconnexion");
	}
	else
			window.location.href="deconnexion.html";
}


function Commentfriend() {

	$.ajax({
		
		type:"GET",
		url:"http://li328.lip6.fr:8280/ahmed/comment/friend",
		data:"key="+environnement.key,
		dataType:"json",
		success:CommentairesfriendTraiteReponse,
		error:function(XHR,textStatus,errorThrown){ alert(XHR+" "+textStatus+" "+errorThrown);}
	});
}


function CommentUser() {

	$.ajax({
		
		type:"GET",
		url:"http://li328.lip6.fr:8280/ahmed/comment/user",					
		dataType:"json",
		success:CommentairesfriendTraiteReponse,
		error:function(XHR,textStatus,errorThrown){ alert(XHR+" "+textStatus+" "+errorThrown);}
	});
}




function CommentairesfriendTraiteReponse(json){
	var rep="";

	 if(json.error_code!=undefined){
			alert("probleme a la création du commentaire");
		}
		else{
				for (var key in json){
					var jsonres=json[key];
					obj = JSON.parse(jsonres)


					
				rep="<td>"+obj["login"]+":</td><td><b>"+obj["text"]+"</b><br />le:"+ obj["date"]+"</td>";
				
				
				
	                	$("table.commfriend").append('<tr>'+ rep +'</tr>')
				}
		}
	            
		}

function listfriend() {

	$.ajax({
		
		type:"GET",
		url:"http://li328.lip6.fr:8280/ahmed/friend/list",
		data:"key="+environnement.key,
		dataType:"json",
		success:ListefriendTraiteReponse,
		error:function(XHR,textStatus,errorThrown){ alert(XHR+" "+textStatus+" "+errorThrown);}
	});
}

function ListefriendTraiteReponse(json){
	var rep="";

	 if(json.error_code!=undefined){
			alert("probleme pour la liste d'amis");
		}
		else{
				
				for (var key in json){
					environnement.friend[key]=json[key];
					
				}
		}
	 
	 
}


function listUser() {

	$.ajax({
		
		type:"GET",
		url:"http://li328.lip6.fr:8280/ahmed/user/list",
		data:"key="+environnement.key,
		dataType:"json",
		success:ListeUserTraiteReponse,
		error:function(XHR,textStatus,errorThrown){ alert(XHR+" "+textStatus+" "+errorThrown);}
	});
}

function ListeUserTraiteReponse(json){
	var rep="";
	
	var verif=0;
	var tab=environnement.friend;
	var user=environnement.actif;
	
	 if(json.error_code!=undefined){
			alert("probleme pour la liste d'user");
		}
		else{
				
				for (var key in json){
					
					if(json[key]!=user.login){
						
						for(var key2 in tab){
						
						if(json[key]==tab[key2]){
							
							verif++;
						}
						}
						
							if(verif>0){
								rep="<td>"+json[key]+"</td><td><input type='button' id='"+json[key]+"1' name='"+json[key]+"' value='+' style='visibility:hidden' onClick='javascript:AddRemoveUser(this)'/></td><td><input type='button' id='"+json[key]+"2' name='"+json[key]+"' value='-' onClick='javascript:AddRemoveUser(this)'/></td>"; 
							
							}
							else {
								rep="<td>"+json[key]+"</td><td><input id='"+json[key]+"1' type='button' name='"+json[key]+"' value='+' onClick='javascript:AddRemoveUser(this)'/></td><td><input type='button' id='"+json[key]+"2' name='"+json[key]+"' value='-' style='visibility:hidden' onClick='javascript:AddRemoveUser(this)'/></td>";
							}
							 $(".content_box table").append("<tr>"+rep+"</tr>");
						
							 verif=0;
						}
				}
					
	          }
				
		}
	            
		
function AddRemoveUser(button) {
	  var login=button.name;
	  environnement.idfriend=login;
	if(button.value=='+'){
	$.ajax({
		
		type:"GET",
		url:"http://li328.lip6.fr:8280/ahmed/friend/add",
		data:"key="+environnement.key+"&logfriend="+login,
		dataType:"json",
		success:AddUserTraiteReponse,
		error:function(XHR,textStatus,errorThrown){ alert(XHR+" "+textStatus+" "+errorThrown);}
	});
}
else {
	
		$.ajax({
			
			type:"GET",
			url:"http://li328.lip6.fr:8280/ahmed/friend/remove",
			data:"key="+environnement.key+"&logfriend="+login,
			dataType:"json",
			success:RemoveUserTraiteReponse,
			error:function(XHR,textStatus,errorThrown){ alert(XHR+" "+textStatus+" "+errorThrown);}
		});
}
}

function AddUserTraiteReponse(json){
	var login=environnement.idfriend;
	
	if(json.error_code!=undefined){
		alert("probleme à l'ajout de l'user");
	}
	else{
		
		document.getElementById(""+login+"1").style.visibility="hidden";
			document.getElementById(""+login+"2").style.visibility="visible";
}
}

function RemoveUserTraiteReponse(json){
	var login=environnement.idfriend;
	
	
	if(json.error_code!=undefined){
		alert("probleme à la suppréssion de l'user");
	}
	else{
		document.getElementById(""+login+"2").style.visibility="hidden";
			document.getElementById(""+login+"1").style.visibility="visible";
}
}


function search () {
	var friends=($("#friendOnly").get(0).checked) ? 1 : 0;
	var query=$("#recher").val();
	$.ajax({
		type:"GET",
		url:"http://li328.lip6.fr:8280/ahmed/friend/search",					
		data:"key="+environnement.key+"&Queryfriend="+query,
		dataType:"text",
		success:RechercheCommentairesTraiteReponse,
		error:function(XHR,textStatus,errorThrown){ alert(XHR+" "+textStatus+" "+errorThrown);}
	})
}

function RechercheCommentairesTraiteReponse(json){
	var rep="le rÈsultat de votre recherche:";

	if(json.error_code!=undefined){
		alert("probleme à la recherche");
	}
	else{
			
			for (var key in json){
				rep+=""+json[key]+"";
			}
			
			alert(rep);
	}
}