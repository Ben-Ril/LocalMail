window.addEventListener("load", onWindowLoad);
window.addEventListener("resize", onWindowResize);

function onWindowLoad() {
  onWindowResize();
}
function onWindowResize(){
    const screenHeight = window.innerHeight;
    const screenWidth = window.innerWidth;
    const isHorizontal = screenHeight < screenWidth;
    const menuSearchContainerWidth = screenWidth*(isHorizontal? 0.6:0.98);
    const menuSearchContainerHeight = screenHeight*(isHorizontal? 0.08:0.05);
    const searchBarWidth = menuSearchContainerWidth*(isHorizontal? 0.75:0.8);
    const searchBarHeight = menuSearchContainerHeight*0.6;
    const navMenuWidth = screenWidth*(isHorizontal? 0.3:0.7);
    const navMenuHeight = screenHeight*(isHorizontal? 0.37:0.16);
    const menuFontSize = searchBarHeight*0.8;
    const navMenuButtonWidth = navMenuWidth*0.6;
    const newMailButtonWidth = screenWidth*0.3;
    const mailBoxWidth = screenWidth*0.97;
    const mailBoxHeight = screenHeight*(isHorizontal?0.87:0.93);
    const newMailWidth = screenWidth*(isHorizontal?0.4:0.7);
    const newMailheight = screenHeight*(isHorizontal?0.6:0.7);
    const newMailClassWidth = newMailWidth * 0.97;
    const messageHeight = newMailheight*(isHorizontal?0.7:0.86);
    const closeButtonWitdh = newMailClassWidth*0.15


    let body = document.getElementById("body");
    let menuSearchContainer = document.getElementById("menuSearchContainer");
    let navMenu = document.getElementById("navMenu");
    let menuButton = document.getElementById("menuButton");
    let newMailButtonContainer = document.getElementById("newMailButtonContainer");
    let newMailButton = document.getElementById("newMailButton");
    let searchBar = document.getElementById("searchBar");
    let newMail = document.getElementById("newMail");
    let newMailClass = document.getElementsByClassName("newMailClass");
    let message = document.getElementById("message");
    let navMenuButtons = document.getElementsByClassName("navMenuButtons");
    let closeButton = document.getElementById("closeButton");
    let receptionBox = document.getElementById("receptionBox");
    let sendedBox = document.getElementById("sendedBox");
    
    body.style.screenWidth = screenWidth + "px";
    body.style.height = screenHeight+"px";

    menuSearchContainer.style.width = menuSearchContainerWidth+"px";
    menuSearchContainer.style.height = menuSearchContainerHeight+"px";

    menuButton.style.height = searchBarHeight + "px";
    menuButton.style.marginTop = (menuSearchContainerHeight-searchBarHeight)/2+"px";
    menuButton.style.fontSize = menuFontSize + "px";

    searchBar.style.width = searchBarWidth+"px";
    searchBar.style.height = searchBarHeight+"px";
    searchBar.style.marginTop = (menuSearchContainerHeight-searchBarHeight)/2-1+"px";
    searchBar.style.fontSize = menuFontSize + "px";

    navMenu.style.width = navMenuWidth+"px";
    navMenu.style.height = navMenuHeight+"px";
    navMenu.style.fontSize = menuFontSize+"px";
    Array.prototype.forEach.call(navMenuButtons, function(elem){
        elem.style.width = navMenuButtonWidth+"px";
        elem.style.fontSize = menuFontSize+"px";
        elem.style.marginLeft = navMenuWidth/2-navMenuButtonWidth/2+"px";
    });

    newMailButtonContainer.style.visibility = (isHorizontal? "hidden":"visible");
    
    newMailButton.style.width = newMailButtonWidth+"px";
    newMailButton.style.fontSize = menuFontSize+"px";

    newMail.style.width = newMailWidth+"px";
    newMail.style.height = newMailheight+"px";
    Array.prototype.forEach.call(newMailClass, function(elem) {
        elem.style.fontSize = menuFontSize+"px";
        elem.style.width = newMailClassWidth+"px" ;
    });
    message.style.height = messageHeight+"px";
    closeButton.style.width = closeButtonWitdh+"px";
    closeButton.style.fontSize = menuFontSize+"px";


    receptionBox.style.width = mailBoxWidth+"px";
    receptionBox.style.height = mailBoxHeight+"px";

    sendedBox.style.width = mailBoxWidth+"px";
    sendedBox.style.height = mailBoxHeight+"px";
}

function navMenuButton(){
    let navMenu = document.getElementById("navMenu");
    const isVisible = navMenu.style.visibility == "visible";

    navMenu.style.visibility = (isVisible? "hidden":"visible");
}

function newMailButton(button){
    let newMail = document.getElementById("newMail");
    let newMailButtonContainer = document.getElementById("newMailButtonContainer");
    const isVisible = newMail.style.visibility == "visible";
    const screenHeight = window.innerHeight;
    const screenWidth = window.innerWidth;
    const isHorizontal = screenHeight < screenWidth;

    newMail.style.visibility = (isVisible? "hidden":"visible");
    newMailButtonContainer.style.visibility = (isVisible? "visible":"hidden");
    newMailButtonContainer.style.visibility = (isHorizontal? "hidden":"visible");
}

function mailBoxButton(button){
    let receptionBox = document.getElementById("receptionBox");
    let sendedBox = document.getElementById("sendedBox");
    
    switch(button.id){
        case "navMenuButtonReceived": 
            sendedBox.style.visibility = "hidden";
            receptionBox.style.visibility = "visible";
            break
        case "navMenuButtonSended":
            receptionBox.style.visibility = "hidden";
            sendedBox.style.visibility = "visible";
    }
}

