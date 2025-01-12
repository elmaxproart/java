document.addEventListener('DOMContentLoaded', function() {
    const modelSelect = document.getElementById('modelSelect');
    const natureSelect = document.getElementById('natureSelect');
    const departementSelect = document.getElementById('departementSelect');
    const filiereSelect = document.getElementById('filiereSelect')

    // Définir les options disponibles pour chaque modèle
    const options = {
        note: ['PV_UE', 'RELEVE DE NOTES', 'ATTESTATION'],
        correction: ['CORRIGE', 'FEUILLE DE CORRECTION'],
        deliberation: ['PV_DELIBERATION', 'DECISION FINALE'],
        documents: ['CERTIFICAT DE SCOLARITE', 'DIPLOME'],
        fiche: ['FICHE_DE_PRESENCE', 'FICHE_EVALUATION']
    };

     const optionDep = {
        Informatique:['ICT4D','INFO_FONDA'],
        Mathematique:['Mathematique'],
        GeoSciences:['GeoSciences'],
        BiosSciences:['BioSciences'],
        Chimie:['Chimie'],
        BOV:['BOV'],
        BOA:['BOA'],
        STU:['STU'],
    };

    // Fonction pour mettre à jour les options de "Nature"
    function updateNatureOptions(selectedModel) {
        // Vider les options actuelles de "Nature"
        natureSelect.innerHTML = '';

        // Ajouter les nouvelles options
        options[selectedModel].forEach(function(option) {
            const opt = document.createElement('option');
            opt.value = option;
            opt.textContent = option;
            natureSelect.appendChild(opt);
        });
    }

     function updateFiliereOptions(selectedDepartement) {
        // Vider les options actuelles de "Filiere"
        filiereSelect.innerHTML = '';

        // Ajouter les nouvelles Filiere
        optionDep[selectedDepartement].forEach(function(option) {
            const opt = document.createElement('option');
            opt.value = option;
            opt.textContent = option;
            filiereSelect.appendChild(opt);
        });
    }

    // Écouter les changements du modèle
    modelSelect.addEventListener('change', function() {
        const selectedModel = modelSelect.value;
        updateNatureOptions(selectedModel);
    });

 departementSelect.addEventListener('change', function() {
        const selectedDepartement = departementSelect.value;
        updateFiliereOptions(selectedDepartement);
    });
    // Initialiser les options de "Nature" en fonction du modèle sélectionné au chargement
    updateNatureOptions(modelSelect.value);
    updateFiliereOptions(departementSelect.value);
});

    // Gestion de l'affichage des champs Matricule et Nom
    const modelSelect = document.getElementById('modelSelect');
    const matriculeField = document.getElementById('matriculeField');
    const nomField = document.getElementById('nomField');

    modelSelect.addEventListener('change', function() {
        const selectedModel = modelSelect.value;
        if (selectedModel === 'documents' || selectedModel === 'fiche') {
            matriculeField.style.display = 'block';
            nomField.style.display = 'block';
        } else {
            matriculeField.style.display = 'none';
            nomField.style.display = 'none';
        }
    });

    // Affichage du tableau quand on clique sur "Executer"
    const executerBtn = document.getElementById('executerBtn');
    const tableRecap = document.getElementById('tableRecap');

    executerBtn.addEventListener('click', function(event) {
        event.preventDefault();
        tableRecap.style.display = 'block';
    });