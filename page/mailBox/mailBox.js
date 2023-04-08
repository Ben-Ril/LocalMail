window.addEventListener("load", place);
window.addEventListener("resize", place);

var isMenuOpen = false;

function place(){
    const windowsHeight = window.innerHeight;
    const windowsWidth = window.innerWidth;
    const isHorizontal = windowsWidth >= windowsHeight;

    const headerHeight = windowsHeight*0.08;
    const headerContentHeight = headerHeight*0.64;
    const headerImageContentHeight = headerContentHeight*0.8;
    const headerPadding = (headerHeight-headerContentHeight)/2;
    const headerImageMargin = (headerContentHeight-headerImageContentHeight)/2;

    const contentHeight = windowsHeight-headerHeight;

    const navMenuIconMargin = headerImageContentHeight/2;
    const navMenuWidth = headerImageContentHeight+navMenuIconMargin*2;

    let body = document.getElementById("body");

    let header = document.getElementById("header");
    let headerIcon = document.getElementsByClassName("headerIcon");
    let searchInput = document.getElementById("searchInput");

    let contentSection = document.getElementById("contentSection");
    let navMenu = document.getElementById("navMenu");
    let navMenuIcon = document.getElementsByClassName("navMenuIcon");
    let navMenuListNameElements = document.getElementsByClassName("navMenuListNameElement");
    let mails = document.getElementsByClassName("mail");
    
    body.style.width = windowsWidth + "px";
    body.style.height = windowsHeight + "px";

    header.style.width = windowsWidth + "px";
    header.style.height = headerHeight-headerPadding*2 + "px";
    header.style.paddingTop = headerPadding + "px";
    header.style.paddingBottom = headerPadding + "px";
    searchInput.style.height = headerContentHeight + "px";
    Array.prototype.forEach.call(headerIcon, function(icon) {
        icon.style.height = headerImageContentHeight + "px";
        icon.style.marginTop = headerImageMargin + "px";
        icon.style.marginBottom = icon.style.marginTop;
        icon.style.marginLeft = navMenuIconMargin + "px";
        icon.style.marginRight = icon.style.marginLeft;
    });

    contentSection.style.widows = windowsWidth + "px";
    contentSection.style.height = contentHeight + "px";
    navMenu.style.width = (!isMenuOpen ? navMenuWidth : navMenuWidth*3.5) + "px";
    navMenu.style.height = contentHeight + "px";
    Array.prototype.forEach.call(navMenuIcon, function(icon) {
        icon.style.height = headerImageContentHeight + "px";
        icon.style.margin = navMenuIconMargin + "px";
    });
    Array.prototype.forEach.call(navMenuListNameElements, function(elem){
        elem.style.display = (isMenuOpen ? "block": "none");
        elem.style.height = headerImageContentHeight/2 + "px";
        elem.style.fontSize = headerImageContentHeight/2 + "px";
        elem.style.marginTop = navMenuIconMargin + headerImageContentHeight/4 + "px";
    });

    //mailListSection.style.height = windowsWidth - Number(navMenu.style.width.split("p")[0]) + "px";

    Array.prototype.forEach.call(mails, function(mail){
        mail.style.width = windowsWidth - Number(navMenu.style.width.split("p")[0]) + "px";
    });
}

function menuPressed(){
    isMenuOpen = !isMenuOpen;

    let navMenuListNameElements = document.getElementsByClassName("navMenuListNameElement");
    let navMenu = document.getElementById("navMenu");
    let menuImageButton = document.getElementById("menuImageButton");
    let mails = document.getElementsByClassName("mail");

    const navMenuDefaultWidth = Number(navMenu.style.width.split("p")[0]);
    const newMailsWidth = (isMenuOpen ? Number(mails[0].style.width.split("p")[0]) - navMenuDefaultWidth*2.5 : Number(mails[0].style.width.split("p")[0]) + (navMenuDefaultWidth - navMenuDefaultWidth/3.5)) + "px";
    if(isMenuOpen){
        Array.prototype.forEach.call(navMenuListNameElements, function(elem){
            elem.style.display = "block";
            elem.animate({
                    opacity: [0,1],
                    color: ["#fff", "#000"]
            }, 200);
        });

        navMenu.animate({
            width: [navMenuDefaultWidth + "px", navMenuDefaultWidth*3.5 + "px"]
        }, 200);

        menuImageButton.setAttribute("src", "../image/close_menu_icon.png");

        navMenu.style.width = navMenuDefaultWidth*3.5 + "px";

        Array.prototype.forEach.call(mails, function(mail){
            mail.animate({
                width: [mail.style.width, newMailsWidth]
            }, 200);

            mail.style.width = newMailsWidth;
        })

    }else{
        Array.prototype.forEach.call(navMenuListNameElements, function(elem){
            elem.style.display = "none";
        });

        navMenu.animate({
            width: [navMenuDefaultWidth + "px", navMenuDefaultWidth/3.5 + "px"]
        }, 200);

        menuImageButton.setAttribute("src", "../image/menu_icon.png");

        navMenu.style.width = navMenuDefaultWidth/3.5 + "px";

        Array.prototype.forEach.call(mails, function(mail){
            mail.animate({
                width: [mail.style.width, newMailsWidth]
            }, 200);

            mail.style.width = newMailsWidth;
        })
    }
}