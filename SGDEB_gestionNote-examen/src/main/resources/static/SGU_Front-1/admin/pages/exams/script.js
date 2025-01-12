$('document').ready(function(){
    $(".end-btn").on('click', function(){
        if($(this).text() === "Afficher Résultat"){
            $('.result').show();
            $(this).text("Cacher Résultat");
            $('.admin-body').css({'display':'flex', 'flex-direction':'row', 'gap':'20px'});
            $('.content').css({'width':'60%'});
            $('.admin-form').css({'width':'59.5%'}); 
            $('.anonym-form input').css({'width':'75%'});
            $('.result').css({'width':'40%'});
        }else  if($(this).text() === "Cacher Résultat"){
            $('.result').hide();
            $('.content').css({'width':'100%'});
            $('.admin-form').css({'width':'100%'}); 
            $('.anonym-form input').css({'width':'100%'});
            $(this).text("Afficher Résultat");
        }  
    });
  
    $("#file-list").hide();
    $(".warning-text").hide();
    
    $("#file-input").on("change", function(){
        $("#file-list").show();
        $("#btn-import1").show();
    });

    $("#btn-import3").on('click', function(){
        $(".note-body").hide();
        $(".section-doc").show();
        $(this).hide();
    });

    $("#btn-import1").on('click', function(){
        if($("#file-input").val() !== ""){
            $("#btn-import3").show();
            $(".note-body").show();
            $(".section-doc").hide();
        }else{
            $(".note-body").hide();
            $(".section-doc").show();
        }

    });

    $("#btn-import2").on('click', function(){
        if($("#filiere").val() !== "" && $("#annee").val() !== "" && $("#ue").val() !== "" && $("#eval").val() !== "" 
        && $("#session").val() !== "" && $("#note-sur").val() !== "" && $("#fichier").val() !== "" && $("#anonymat").val() !== ""){
            $(".note-body").show();
            $(".warning-text").hide();
        }else{
            $(".note-body").hide();
            $(".warning-text").show();
        }
        /*if($(this).text() === "Exécuter"){
            $(this).text("Cacher");}
        else if($(this).text("Cacher")){
            $(this).text("Exécuter");
            $(".note-body").hide();
        }*/
    });

    $(".btn-type").on('click', function(){
        if($(this).text() === "Importation Rapide"){
            $(".note-body").hide();
            $("#btn-import3").hide();
            $(this).text("Importation Sélection");
            $(".section-doc").show();
            $(".section-selec").hide();
        }else if($(this).text() === "Importation Sélection"){
            $(".note-body").hide();
            $("#btn-import3").hide();
            $(this).text("Importation Rapide");
            $(".section-doc").hide();
            $(".section-selec").show();
        }
    });
});

const dropZone = document.getElementById('drop-zone');
const fileInput = document.getElementById('file-input');
const fileList = document.getElementById('file-list');

// Fonction pour traiter les fichiers
function handleFiles(files) {
    fileList.innerHTML = '';  // Vider la liste des fichiers avant d'ajouter les nouveaux
    for (const file of files) {
        const listItem = document.createElement('div');
        listItem.innerText = `Nom : ${file.name} - Taille : ${file.size} bytes`;
        fileList.appendChild(listItem);
    }
}

// Empêcher le comportement par défaut des événements drag and drop
['dragenter', 'dragover', 'dragleave', 'drop'].forEach(eventName => {
    dropZone.addEventListener(eventName, e => e.preventDefault());
});

// Ajouter la classe hover lors du dragover
dropZone.addEventListener('dragover', () => {
    dropZone.classList.add('hover');
});

// Retirer la classe hover lorsque le fichier n'est plus au-dessus
dropZone.addEventListener('dragleave', () => {
    dropZone.classList.remove('hover');
});

// Lors du drop de fichiers
dropZone.addEventListener('drop', (e) => {
    dropZone.classList.remove('hover');
    const files = e.dataTransfer.files;
    handleFiles(files);
});

// Clic sur la zone déclenche l'input file
dropZone.addEventListener('click', () => {
    fileInput.click();
});

// Lorsqu'un fichier est sélectionné via l'input
fileInput.addEventListener('change', () => {
    const files = fileInput.files;
    handleFiles(files);
});
