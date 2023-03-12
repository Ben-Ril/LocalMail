function onload(){
    onresize();
}
function onresize(){
    const screenHeight = window.innerHeight;
    const screenWidth = window.innerWidth;
    const isHorizontal = screenHeight < screenWidth;
    const menuSearchContainerWidth = screenWidth*(isHorizontal? 0.6:0.98)
    const menuSearchContainerHeight = screenHeight*(isHorizontal? 0.08:0.05)
    const searchBarWidth = menuSearchContainerWidth*(isHorizontal? 0.8:0.8)
    const searchBarHeight = menuSearchContainerHeight*0.6
    const navMenuWidth = screenWidth*(isHorizontal? 0.3:0.7)
    const navMenuHeight = screenHeight*(isHorizontal? 0.6:0.7)
    const menuFontSize = searchBarHeight*0.8
    const navMenuButtonWidth = navMenuWidth*0.6
    const newMailButtonWidth = screenWidth*0.3
    const mailBoxWidth = screenWidth*0.8
    const mailBoxHeight = screenHeight*0.7


    let body = document.getElementById("body")
    let menuSearchContainer = document.getElementById("menuSearchContainer")
    let navMenu = document.getElementById("navMenu")
    let MenuButton = document.getElementById("MenuButton")
    let newMailButtonContainer = document.getElementById("newMailButtonContainer")
    let newMailButton = document.getElementById("newMailButton")
    let searchBar = document.getElementById("searchBar")

    let navMenuButtonNew = document.getElementById("navMenuButtonNew")
    let navMenuButtonReceived = document.getElementById("navMenuButtonReceived")
    let navMenuButtonSended = document.getElementById("navMenuButtonSended")
    let navMenuButtonAdmin = document.getElementById("navMenuButtonAdmin")

    let receptionBox = document.getElementById("receptionBox")
    let sendedBox = document.getElementById("sendedBox")
    
    body.style.height = screenHeight-16+"px"

    menuSearchContainer.style.width = menuSearchContainerWidth+"px"
    menuSearchContainer.style.height = menuSearchContainerHeight+"px"

    MenuButton.style.height = searchBarHeight + "px"
    MenuButton.style.marginTop = (menuSearchContainerHeight-searchBarHeight)/2+"px"
    MenuButton.style.fontSize = menuFontSize + "px"

    searchBar.style.width = searchBarWidth+"px"
    searchBar.style.height = searchBarHeight+"px"
    searchBar.style.marginTop = (menuSearchContainerHeight-searchBarHeight)/2-1+"px"
    searchBar.style.fontSize = menuFontSize + "px"

    navMenu.style.width = navMenuWidth+"px"
    navMenu.style.height = navMenuHeight+"px"
    navMenu.style.fontSize = menuFontSize+"px"

    navMenuButtonNew.style.width = navMenuButtonWidth+"px"
    navMenuButtonNew.style.fontSize = menuFontSize+"px"  //impossible de mettre une class aux bouttons et de tout appliquer d'un coup pour une raison osbcure
    navMenuButtonNew.style.marginLeft = navMenuWidth/2-navMenuButtonWidth/2+"px"

    navMenuButtonReceived.style.width = navMenuButtonWidth+"px"
    navMenuButtonReceived.style.fontSize = menuFontSize+"px"
    navMenuButtonReceived.style.marginLeft = navMenuWidth/2-navMenuButtonWidth/2+"px"

    navMenuButtonSended.style.width = navMenuButtonWidth+"px"
    navMenuButtonSended.style.fontSize = menuFontSize+"px"
    navMenuButtonSended.style.marginLeft = navMenuWidth/2-navMenuButtonWidth/2+"px"
    
    navMenuButtonAdmin.style.width = navMenuButtonWidth+"px"
    navMenuButtonAdmin.style.fontSize = menuFontSize+"px"
    navMenuButtonAdmin.style.marginLeft = navMenuWidth/2-navMenuButtonWidth/2+"px"

    newMailButtonContainer.style.visibility = (isHorizontal? "hidden":"visible")
    
    newMailButton.style.width = newMailButtonWidth+"px"
    newMailButton.style.fontSize = menuFontSize+"px"

    //receptionBox.style.width = mailBoxWidth+"px"
    //receptionBox.style.height = mailBoxHeight+"px"

    //sendedBox.style.width = mailBoxWidth+"px"
    //sendedBox.style.height = mailBoxHeight+"px"
}

function navMenuButton(button){
    let navMenu = document.getElementById("navMenu")
    let navMenuButton = document.getElementById("navMenuButton")
    let navMenuButtonNew = document.getElementById("navMenuButtonNew")

    const isVisible = navMenu.style.visibility == "visible"


    navMenu.style.visibility = (isVisible? "hidden":"visible")
    
    
}

