<!DOCTYPE html>
<html>
<head>
<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />
<link rel="stylesheet" href="style.css" />
<link rel="stylesheet" href="stylebutton.css" />
<script type="text/javascript" src="javascript/jquery.js"></script>  
<script  type="text/javascript" src="javascript/main.js"></script>

<!-- Ajout d'une icone dans l'onglet du navigateur du titre -->
<link href="image/logo.png" rel="shortcut icon" type="image/x-icon" />
<title>TwittUpmc</title>
</head>

    <body>
		<script type="text/javascript">
	 function go(){
		 
		 	<% String id=request.getParameter("id");
		 	System.out.println(id);
		 		String login=request.getParameter("login");
		 		String key=request.getParameter("key");	 
		 			if((id!=null) && (login!=null) && (key!=null)){
		 				out.println("main('"+id+"','"+login+"','"+key+"');");
		 			}
		 			else out.println("main()");
		 			%>
	 }
	 	$(go);
	</script>
	<div id='blackscreen' style='display:none;'></div> <!-- fond noir --> <div class='box_mv' style='display:none;'> <!-- fenetre -->
<div class='top_box'> <span class='close_box'> Fermer</span> </div>
<div class='content_box'><h4>liste des utilisateurs:</h4><table id="tablelisteUser">
</table>
</div>
</div>
		<div id="header">
		<span class="logo"> 
            <img class="imagelogo" src="image/logo.png" alt="logo" />
			<h2>Le réseau pour etudiant</h2> 
           </span>
			<span class="zone_recherche">
				<form id="requete" action="javascript:(function(){return ; })()" onSubmit="javascript:search()" >
					<TEXTAREA id="recher" nom="recherche" rows=1 cols=30 onFocus="this.value=''">rechercher</TEXTAREA>
					<span>
					<input type="submit" name="recherche" value="lancer" class="button_recherche" /> 
					</span>
					<br> Friends<input id="friendOnly" type="checkbox" name="FriendsOnly" />
				</form>
			</span> 
			<span class="lien">
			
			</span>
			
        </div>
        
		<div id="banniere_image">
        </div>
           
		 <section>
		  
		  <div id="zone_new">
		  <form method="get" action="javascript:(function(){return ; })()" onSubmit="javascript:Comment(document.getElementById('com').value)" >
				<TEXTAREA id="com" nom="commentaire" rows=2 cols=50 onFocus="this.value=''">Exprimez vous...</TEXTAREA>
				<input type="submit" name="comment" value="envoyer" class="button_new" /> 
			</form>
			</div>
			
			<div id="listcom">
			
			
			<table class="comm">
		 	</table>
			 <h2>Les news</h2>
			 <table class="commfriend">
			 
			 </table>
             </div>
			 
			<div id="zone_static">
                  <h3>zone static </h3>
                  <div class="btn_box">
                  <form id="requete3" action="javascript:(function(){return ; })()"  onSubmit="javascript:listUser()">
                  <input type='submit'class="listeUserbutton" name="userlist" value="liste des users" /> 
                  </form>
                  </div>
            </div>
            
        </section>
         
        <footer>
            <p>Copyright Ahmed - Tous droits réservés<br /><p>
        </footer>
        <script>$('.btn_box').click( function(){
$('#blackscreen').fadeIn(200);
$('.box_mv').fadeIn(450);
});

$('.close_box').click( function(){
$('#blackscreen').fadeOut(200);
$('.box_mv').fadeOut(450);
window.location.reload(true); 
});
</script>
         
    </body>
	
	
</html>
