
function onload(){
    let screenSizeHeight=window.innerHeight;
    let screenSizeWidth=window.innerWidth;
    console.log(screenSizeWidth,screenSizeHeight)
    let sectionSizeWidth=document.getElementById("formulaire").scrollWidth;
    let sectionSizeHeight=document.getElementById("formulaire").scrollHeight;
    document.getElementById("formulaire").style.marginTop =  screenSizeHeight/2-sectionSizeHeight/2+"px";
    document.getElementById("formulaire").style.marginLeft =  screenSizeWidth/2-sectionSizeWidth/2+"px";
    if (screenSizeWidth < screenSizeHeight){
        document.getElementById("body").style.backgroundImage = "url('../page/image/backgroundAuthentificationRotated.webp')"
        
    }
    else{
        document.getElementById("body").style.backgroundImage = "url('../page/image/backgroundAuthentification.webp')"
    }

    let inputWidth = document.getElementById("email").scrollWidth
    let sendButtonWidth = document.getElementById("sendButton").scrollWidth
    document.getElementById("sendButton").style.marginLeft = inputWidth/2-sendButtonWidth/2+"px"
}

