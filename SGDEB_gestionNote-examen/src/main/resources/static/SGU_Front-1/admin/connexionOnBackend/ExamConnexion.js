//connexion au front - end en usant de ajax
document.getElementById('submit-btn').addEventListener('click', function() {
    const codeUE = document.getElementById('code-ue').value;
    const codeFiliere = document.getElementById('code-filiere').value;
    const typeEval = document.getElementById('type-eval').value;
    const noteSur = document.getElementById('note-sur').value;
    const annee = document.getElementById('annee').value;
    const session = document.getElementById('session').value;
    const credit = document.getElementById('credit').value;

    const examenData = {
        codeFiliere: codeFiliere,
        typeEval: typeEval,
        noteSur: noteSur,
        anneeAcademique: annee,
        session: session,
        credit: credit
    };

    $.ajax({
        url: `http://localhost:8090/api/note/examens/add/${codeUE}`,
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(examenData),
        success: function(response) {
            alert('Examen créé avec succès');
        },
        error: function(xhr, status, error) {
            console.error('Erreur lors de la création de l\'examen:', error);
            alert('Erreur lors de la création de l\'examen');
        }
    });
});
