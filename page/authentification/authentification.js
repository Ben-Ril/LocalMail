
/*function dd(){
    let screenSizeHeight=window.innerHeight;
    let screenSizeWidth=window.innerWidth;
    console.log(screenSizeWidth,screenSizeHeight)
    let sectionSizeWidth=document.getElementById("formulaire").scrollWidth;
    let sectionSizeHeight=document.getElementById("formulaire").scrollHeight;
    document.getElementById("formulaire").style.marginTop =  screenSizeHeight/2-sectionSizeHeight/2+"px";
    document.getElementById("formulaire").style.marginLeft =  screenSizeWidth/2-sectionSizeWidth/2+"px";
    if (screenSizeWidth < screenSizeHeight){
        document.getElementById("body").style.backgroundImage = "url('../image/backgroundAuthentificationRotated.webp')"
        
    }
    else{
        document.getElementById("body").style.backgroundImage = "url('../image/backgroundAuthentification.webp')"
    }

    let inputWidth = document.getElementById("email").scrollWidth
    let sendButtonWidth = document.getElementById("sendButton").scrollWidth
    document.getElementById("sendButton").style.marginLeft = inputWidth/2-sendButtonWidth/2+"px"
}*/

function onload(){
    const screenWidth = window.innerWidth;
    const screenHeight = window.innerHeight;

    let form = document.getElementById("form");
    const formWidth = screenWidth/4;
    const formHeight = screenHeight/4;
    form.style.width = formWidth + "px";
    form.style.height = formHeight + "px";

    form.style.top = screenHeight/2-formHeight/2 + "px";
    form.style.left = screenWidth/2-formWidth/2 + "px";


    let mailInput = document.getElementById("mailInput");
    mailInput.style.width = formWidth*0.8 + "px";
    let passwordInput = document.getElementById("passwordInput");
    passwordInput.style.width = formWidth*0.8 + "px";
}