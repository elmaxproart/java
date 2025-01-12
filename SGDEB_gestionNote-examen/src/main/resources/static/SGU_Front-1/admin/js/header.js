const menuButton = document.getElementById("menuButton");
const menu = document.getElementById("menu");
const closeMenu = document.getElementById("closeMenu");

menuButton.addEventListener("click", () => {
    menu.style.display = "block";
});

closeMenu.addEventListener("click", () => {
    menu.style.display = "none";
});

// Fermer le menu après avoir cliqué sur une option
const menuItems = document.querySelectorAll('.menu-item');
menuItems.forEach(item => {
    item.addEventListener('click', () => {
        menu.style.display = 'none';
    });
});

