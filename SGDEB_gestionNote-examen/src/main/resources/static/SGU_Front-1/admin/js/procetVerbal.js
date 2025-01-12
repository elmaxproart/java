// scripts.js

// Sélecteurs pour Procès-verbal récapitulatif
const anneeRecap = document.querySelector('#anneeRecap');
const filiereRecap = document.querySelector('#filiereRecap');
const niveauRecap = document.querySelector('#niveauRecap');

// Sélecteurs pour Procès-verbal
const anneeProc = document.querySelector('#anneeProc');
const filiereProc = document.querySelector('#filiereProc');
const niveauProc = document.querySelector('#niveauProc');
const matiereProc = document.querySelector('#matiereProc');

// Fonction de mise à jour dynamique pour le tableau Procès-verbal récapitulatif
function updateTableRecap() {
    const selectedAnnee = anneeRecap.value;
    const selectedFiliere = filiereRecap.value;
    const selectedNiveau = niveauRecap.value;

    console.log(`Recapitulatif mis à jour pour: Année: ${selectedAnnee}, Filière: ${selectedFiliere}, Niveau: ${selectedNiveau}`);

    // TODO: Logique pour mettre à jour le tableau des résultats en fonction des sélections
}

// Fonction de mise à jour dynamique pour le tableau Procès-verbal
function updateTableProc() {
    const selectedAnnee = anneeProc.value;
    const selectedFiliere = filiereProc.value;
    const selectedNiveau = niveauProc.value;
    const selectedMatiere = matiereProc.value;

    console.log(`Procès-verbal mis à jour pour: Année: ${selectedAnnee}, Filière: ${selectedFiliere}, Niveau: ${selectedNiveau}, Matière: ${selectedMatiere}`);

    // TODO: Logique pour mettre à jour le tableau des résultats en fonction des sélections
}

// Ajout des écouteurs d'événements pour Procès-verbal récapitulatif
if (anneeRecap) anneeRecap.addEventListener('change', updateTableRecap);
if (filiereRecap) filiereRecap.addEventListener('change', updateTableRecap);
if (niveauRecap) niveauRecap.addEventListener('change', updateTableRecap);

// Ajout des écouteurs d'événements pour Procès-verbal
if (anneeProc) anneeProc.addEventListener('change', updateTableProc);
if (filiereProc) filiereProc.addEventListener('change', updateTableProc);
if (niveauProc) niveauProc.addEventListener('change', updateTableProc);
if (matiereProc) matiereProc.addEventListener('change', updateTableProc);

<!-- JavaScript pour gérer les changements entre les tableaux -->

document.getElementById('procetVerbaleSelector').addEventListener('change', function() {
    var selection = this.value;
    var recapitulatifContent = document.getElementById('recapitulatifContent');
    var verbaleContent = document.getElementById('verbaleContent');

    if (selection === 'recapitulatif') {
        recapitulatifContent.classList.remove('hidden');
        verbaleContent.classList.add('hidden');
    } else {
        recapitulatifContent.classList.add('hidden');
        verbaleContent.classList.remove('hidden');
    }
});