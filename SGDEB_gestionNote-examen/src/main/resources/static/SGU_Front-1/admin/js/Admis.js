
// Sélecteurs pour admissibilite
const anneeAdm = document.querySelector('#anneeAdm');
const filiereAdm = document.querySelector('#filiereAdm');
const niveauAdm = document.querySelector('#niveauAdm');


// Fonction de mise à jour dynamique pour le tableau de la liste des admin(si possible peut etre ignorer hein)
function updateTableProc() {
    const selectedAnnee = anneeAdm.value;
    const selectedFiliere = filiereAdm.value;
    const selectedNiveau = niveauAdm.value;

    alert(`Liste des admis mis à jour pour: Année: ${selectedAnnee}, Filière: ${selectedFiliere}, Niveau: ${selectedNiveau}`);

    // TODO:je sais pas encore ce qui vas ici mais la Logique pour mettre à jour le tableau des résultats en fonction des sélections n'est pas encore totalement definie
}


// Ajout des écouteurs d'événements pour Procès-verbal
if (anneeProc) anneeAdm.addEventListener('change', updateTableProc);
if (filiereProc) filiereAdm.addEventListener('change', updateTableProc);
if (niveauProc) niveauAdm.addEventListener('change', updateTableProc);

//chargement des tableaux ,mbref
document.getElementById('AdmisListe').addEventListener('change', function() {
    var selection = this.value;
    var recapitulatifContent = document.getElementById('recapitulatifContent');
    var admisContent = document.getElementById('admisContent');

    if (selection === 'admis') {
        admisContent.classList.remove('hidden');
        recapitulatifContent.classList.add('hidden');
    } else {
        recapitulatifContent.classList.remove('hidden');
        AdmisContent.classList.add('hidden');
    }
});