function onload(){
    onresize();
}

function onresize(){
    const screenHeight = window.innerHeight;
    const screenWidth = window.innerWidth;
    const isHorizontal = screenHeight < screenWidth;
    const formWidth = screenWidth/(isHorizontal? 4 : 2);
    const height = screenHeight/4;
    const inputWidth = formWidth*(isHorizontal? 0.6 : 0.8);
    const inputHeight = height/10;
    const inputFontSize = height/(isHorizontal? 12 : 14);
    const inputPadding = height/20;
    const marginTop = height/20;

    document.getElementById("body").style.backgroundImage = (isHorizontal? "url('./image/backgroundAuthentification.webp')" : "url('./image/backgroundAuthentificationRotated.webp')");

    let form = document.getElementById("form");
    let mailInput = document.getElementById("mailInput");
    let passwordInput = document.getElementById("passwordInput");
    let loginButton = document.getElementById("connectButton");

    form.style.top = screenHeight/2-height/2 + "px";
    form.style.left = screenWidth/2-formWidth/2 + "px";
    form.style.width = formWidth + "px";
    form.style.padding = inputPadding + "px";
    form.style.height = "fit-content";
    
    mailInput.style.width = inputWidth + "px";
    mailInput.style.height = inputHeight + "px";
    mailInput.style.fontSize = inputFontSize + "px";
    mailInput.style.padding = inputPadding + "px";
    mailInput.style.marginTop = marginTop + "px";

    passwordInput.style.width = inputWidth + "px";
    passwordInput.style.height = inputHeight + "px";
    passwordInput.style.fontSize = inputFontSize + "px";
    passwordInput.style.padding = inputPadding + "px";
    passwordInput.style.marginTop = marginTop + "px";

    loginButton.style.height = inputHeight*2 + "px";
    loginButton.style.fontSize = inputFontSize + "px";
    loginButton.style.padding = inputPadding + "px";
    loginButton.style.marginTop = marginTop + "px";
}