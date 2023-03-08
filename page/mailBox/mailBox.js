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
    const navMenuWidth = screenWidth*(isHorizontal? 0.08:0.7)
    const menuFontSize = searchBarHeight*0.8


    let body = document.getElementById("body")
    let menuSearchContainer = document.getElementById("menuSearchContainer")
    let navMenu = document.getElementById("navMenu")
    let navMenuButton = document.getElementById("navMenuButton")
    let newMailButton = document.getElementById("newMailButton")
    let searchBar = document.getElementById("searchBar")

    body.style.height = screenHeight+"px"

    menuSearchContainer.style.width = menuSearchContainerWidth+"px"
    menuSearchContainer.style.height = menuSearchContainerHeight+"px"

    navMenuButton.style.height = searchBarHeight + "px"
    navMenuButton.style.marginTop = (menuSearchContainerHeight-searchBarHeight)/2+"px"
    navMenuButton.style.fontSize = menuFontSize + "px"

    searchBar.style.width = searchBarWidth+"px"
    searchBar.style.height = searchBarHeight+"px"
    searchBar.style.marginTop = (menuSearchContainerHeight-searchBarHeight)/2-1+"px"
    searchBar.style.fontSize = menuFontSize + "px"

    navMenu.style.width = navMenuWidth+"px"

    newMailButton.style.visibility = (isHorizontal? "hidden":"visible")
}

function navMenuButton(button){
    let navMenu = document.getElementById("navMenu")
    let navMenuButton = document.getElementById("navMenuButton")

    const isVisible = navMenu.style.visibility == "visible"

    navMenu.style.visibility = (isVisible? "hidden":"visible")
    
    
}

