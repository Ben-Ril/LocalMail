
function onload(){
    let windowWidthSize = window.innerWidth;
    let windowHeightSize = window.innerHeight;
    

    document.getElementById("mailSender").style.marginBottom = windowHeightSize*0.01+"px";
    document.getElementById("mailSender").style.width = windowHeightSize*0.35+"px";
    document.getElementById("object").style.width = windowWidthSize*0.35+"px";
    document.getElementById("receiver").style.width = windowWidthSize*0.35+"px";
    document.getElementById("content").style.width = windowWidthSize*0.35+"px";
    document.getElementById("content").style.height = windowHeightSize*0.35+"px";
    
    //document.getElementById("mailSender").style.bottom = "0px"
    //document.getElementById("mailSender").style.right = windowWidthSize/2+"px"
    //console.log(windowWidthSize)
}
function newMailButton(button){
    if ( document.getElementById("mailSender").style.display == "none"){
        document.getElementById("mailSender").style.display = "inline";
    }
    else{
        document.getElementById("mailSender").style.display = "none";
    }

}
function getRickRoll(){
    window.open('https://youtu.be/eR3fLy8d1PU', '_blank');
}