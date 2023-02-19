
function onload(){
    let windowWidthSize = window.innerWidth;
    let windowHeightSize = window.innerHeight;
    
    document.getElementById("body").style.height = windowHeightSize-16+"px"

    document.getElementById("mailSender").style.marginBottom = windowHeightSize*0.01+"px";
    document.getElementById("mailSender").style.width = windowHeightSize*0.35+"px";
    document.getElementById("object").style.width = windowWidthSize*0.35+"px";
    document.getElementById("receiver").style.width = windowWidthSize*0.35+"px";
    document.getElementById("content").style.width = windowWidthSize*0.35+"px";
    document.getElementById("content").style.height = windowHeightSize*0.35+"px";
    
    document.getElementById("mailSender").style.bottom = "0px";
    document.getElementById("mailSender").style.left = windowWidthSize/2+"px";

    document.getElementById("navMenu").style.width = windowWidthSize*0.2+"px";
    document.getElementById("newMailButton").style.width = windowWidthSize*0.2+"px";
    document.getElementById("mailReceiveButton").style.width = windowWidthSize*0.2+"px";
    document.getElementById("mailSendButton").style.width = windowWidthSize*0.2+"px";

    document.getElementById("searchBar").style.width = windowWidthSize*0.5+"px";

}

function changeBoxButtons(button){
    if (button.id == "mailReceiveButton"){
        document.getElementById("sendBox").style.display = "none";
        document.getElementById("receptionBox").style.display = "inline";
    }
    else if(button.id == "mailSendButton"){
        document.getElementById("receptionBox").style.display = "none";
        document.getElementById("sendBox").style.display = "inline";
    }
}



function newMailButton(button){
    if ( document.getElementById("mailSender").style.display == "none"){
        document.getElementById("mailSender").style.display = "inline";
    }
    else{
        document.getElementById("mailSender").style.display = "none";
    }

}
function getRickRoll(content){
    if (content.value == "rickroll") {
        window.open('https://youtu.be/eR3fLy8d1PU', '_blank');
    }
}