async function loadComponents(id, file) {
    const response = await fetch(file);
    const html = await response.text();

    document.getElementById(id).innerHTML = html;
}

loadComponents("navbar-container", "../components/navbar.html");
loadComponents("footer-container", "../components/footer.html");