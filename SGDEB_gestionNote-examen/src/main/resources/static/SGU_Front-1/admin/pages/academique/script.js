
$(function(){   
    $(".group-ec").hide();
  
    if($(".admin-page-content").attr("id")=="form-depart"){
        $("#side-departement").css({"color":"#f38f31", "background-color":"#111"});
    }else  if($(".admin-page-content").attr("id")==="form-filiere"){
        $("#side-filiere").css({"color":"#f38f31", "background-color":"#111"});
        $("#side-departement").css({"color":"black", "background-color":"#eee"});
        $("#side-grille").css({"color":"black", "background-color":"#eee"});
        $("#side-unite").css({"color":"black", "background-color":"#eee"});
        $("#side-ec").css({"color":"black", "background-color":"#eee"});
        $("#side-annee").css({"color":"black", "background-color":"#eee"});
    }else  if($(".admin-page-content").attr("id")==="form-grille"){
        $("#side-grille").css({"color":"#f38f31", "background-color":"#111"});
        $("#side-departement").css({"color":"black", "background-color":"#eee"});
        $("#side-filiere").css({"color":"black", "background-color":"#eee"});
        $("#side-unite").css({"color":"black", "background-color":"#eee"});
        $("#side-ec").css({"color":"black", "background-color":"#eee"});
        $("#side-annee").css({"color":"black", "background-color":"#eee"});
    }else  if($(".admin-page-content").attr("id")==="form-unite"){
        $("#side-unite").css({"color":"#f38f31", "background-color":"#111"});
        $("#side-departement").css({"color":"black", "background-color":"#eee"});
        $("#side-filiere").css({"color":"black", "background-color":"#eee"});
        $("#side-grille").css({"color":"black", "background-color":"#eee"});
        $("#side-ec").css({"color":"black", "background-color":"#eee"});
        $("#side-annee").css({"color":"black", "background-color":"#eee"});
    }else  if($(".admin-page-content").attr("id")==="form-annee"){
        $("#side-annee").css({"color":"#f38f31", "background-color":"#111"});
        $("#side-departement").css({"color":"black", "background-color":"#eee"});
        $("#side-filiere").css({"color":"black", "background-color":"#eee"});
        $("#side-unite").css({"color":"black", "background-color":"#eee"});
        $("#side-ec").css({"color":"black", "background-color":"#eee"});
        $("#side-grille").css({"color":"black", "background-color":"#eee"});
    }

    $(".detail-unite").on("click", function(){
        $("#side-ec").css({"color":"#f38f31", "background-color":"#111"});
        $("#side-departement").css({"color":"black", "background-color":"#eee"});
        $("#side-filiere").css({"color":"black", "background-color":"#eee"});
        $("#side-unite").css({"color":"black", "background-color":"#eee"});
        $("#side-grille").css({"color":"black", "background-color":"#eee"});
        $("#side-annee").css({"color":"black", "background-color":"#eee"});

        $(".group-ec").show();
    });

    $(".close").on("click", function(){
        $(".group-ec").hide();
  
        $("#side-unite").css({"color":"#f38f31", "background-color":"#111"});
        $("#side-departement").css({"color":"black", "background-color":"#eee"});
        $("#side-filiere").css({"color":"black", "background-color":"#eee"});
        $("#side-grille").css({"color":"black", "background-color":"#eee"});
        $("#side-ec").css({"color":"black", "background-color":"#eee"});
        $("#side-annee").css({"color":"black", "background-color":"#eee"});
    });
    
});