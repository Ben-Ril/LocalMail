function onload(){
    onresize();
}


function onresize(){
    const screenWidth = window.innerWidth;
    const screenHeight = window.innerHeight;
    const isHorizontal = screenHeight < screenWidth;

    let body = document.getElementById("body");
    let mailSender= document.getElementById("mailSender");
    let mailObject = document.getElementById("mailObject");
    let mailContent = document.getElementById("mailContent");
    let mailReceiver = document.getElementById("mailReceiver");
    let navMenu = document.getElementById("navMenu");
    let newMailButton = document.getElementById("newMailButton");
    let mailReceiveButton = document.getElementById("mailReceiveButton");
    let mailSendedButton = document.getElementById("mailSendedButton");
    let searchBar = document.getElementById("searchBar");

    body.style.height = screenHeight-16+"px";

    mailSender.style.marginBottom = screenHeight*0.01+"px";
    mailSender.style.width = screenHeight*0.35+"px";
    mailSender.style.bottom = "0px";
    mailSender.style.left = screenWidth/2+"px";

    mailObject.style.width = screenWidth*0.35+"px";

    mailReceiver.style.width = screenWidth*0.35+"px";

    mailContent.style.width = screenWidth*0.35+"px";
    mailContent.style.height = screenHeight*0.35+"px";
    
    

    navMenu.style.width = screenWidth*0.2+"px";

    newMailButton.style.width = screenWidth*0.2+"px";

    mailReceiveButton.style.width = screenWidth*0.2+"px";

    mailSendedButton.style.width = screenWidth*0.2+"px";

    searchBar.style.width = screenWidth*0.5+"px";

}

function changeBoxButtons(button){

    const sendedBox = document.getElementById("sendedBox")
    const receptionBox = document.getElementById("receptionBox")
    
    if (button.id == "mailReceiveButton"){
        sendedBox.style.display = "none";
        receptionBox.style.display = "inline";
    }
    else if(button.id == "mailSendButton"){
        receptionBox.style.display = "none";
        sendedBox.style.display = "inline";
    }
}



function newMailButton(button){
    const mailSender = document.getElementById("mailSender");

    if ( mailSender.style.display == "none"){
        mailSender.style.display = "inline";
    }
    else{
        mailSender.style.display = "none";
    }

}
function getRickRoll(content){
    if (content.value == "rickroll") {
        alert("YOU KNEW IT !");
        window.open('https://youtu.be/eR3fLy8d1PU', '_blank');
    }
}
