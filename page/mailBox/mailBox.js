window.addEventListener("load", onload);
window.addEventListener("resize", place);

var isMenuOpen = false;

async function onload(){
    await place();
    //received();
}

async function place(){
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

    const mailListPaddingLeft = headerImageContentHeight/3;

    let body = document.getElementById("body");

    let header = document.getElementById("header");
    let headerIcon = document.getElementsByClassName("headerIcon");
    let searchInput = document.getElementById("searchInput");

    let contentSection = document.getElementById("contentSection");
    let navMenu = document.getElementById("navMenu");
    let navMenuIcon = document.getElementsByClassName("navMenuIcon");
    let navMenuListNameElements = document.getElementsByClassName("navMenuListNameElement");
    let mails = document.getElementsByClassName("mail");

    let newMailSection = document.getElementById("newMailSection");
    let newMailChilds = document.getElementsByClassName("newMailChild");
    let newMailMessageContent = document.getElementById("newMailMessageContent");

    let mailView = document.getElementById("mailView");
    
    body.style.width = windowsWidth + "px";
    body.style.height = windowsHeight + "px";

    const headerPaddingLeft = (windowsWidth-(headerImageContentHeight*2+navMenuIconMargin*4+windowsWidth/2))/2;
    header.style.height = headerHeight-headerPadding*2 + "px";
    header.style.paddingTop = headerPadding + "px";
    header.style.paddingBottom = headerPadding + "px";
    header.style.paddingLeft = headerPaddingLeft + "px";
    searchInput.style.height = headerContentHeight + "px";
    searchInput.style.width = windowsWidth/2 + "px";
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

    Array.prototype.forEach.call(mails, function(mail){
        mail.style.padding = mailListPaddingLeft + "px";
        mail.style.width = windowsWidth - Number(navMenu.style.width.split("p")[0]) - mailListPaddingLeft*2 + "px";
    });

    newMailSection.style.width = windowsWidth/4 + "px";
    newMailSection.style.height = windowsHeight/2 + "px";
    Array.prototype.forEach.call(newMailChilds, function(newMailChild) {
        newMailChild.style.width = newMailSection.style.width;
        newMailChild.style.height = windowsHeight/16 + "px";
    });

    newMailMessageContent.style.height = windowsHeight/2 - 2*windowsHeight/16 + "px";

    mailView.style.width = windowsWidth/1.5 + "px";
    mailView.style.height = windowsHeight/1.5 + "px";
    mailView.style.top = (windowsHeight - windowsHeight/1.5)/2 + "px";
    mailView.style.left = (windowsWidth - windowsWidth/1.5)/2 + "px";
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

        menuImageButton.setAttribute("src", "./image/close_menu_icon.png");

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

        menuImageButton.setAttribute("src", "./image/menu_icon.png");

        navMenu.style.width = navMenuDefaultWidth/3.5 + "px";

        Array.prototype.forEach.call(mails, function(mail){
            mail.animate({
                width: [mail.style.width, newMailsWidth]
            }, 200);

            mail.style.width = newMailsWidth;
        })
    }
}

function closeMailView(){
    let mailView = document.getElementById("mailView");
    mailView.style.display = "none";
}