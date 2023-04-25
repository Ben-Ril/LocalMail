function sended(){
    sendStatus("sended");
}

function received(){
    sendStatus("received");
}

function sendStatus(boxStatus){
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
        alert(this.responseText);
    }
    };

    let boxStatus = str;
      
    xmlhttp.open("GET", "../php/authentification.php?" + "boxStatus="+ boxStatus, true);
    xmlhttp.send();
}

function sendMail(){
    let receiver = document.getElementById("destinatorInput").value;
    let object = document.getElementById("objectInput").value;
    let mailContent = document.getElementById("newMailMessageContent");

    var xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
        alert(this.responseText);
    }
    };
    xmlhttp.onload = function(){
        let isSent = this.responseText;
    }
    xmlhttp.open("GET", "../php/authentification.php?" + "receiver="+ receiver + "&object=" + object +"&mailContent"+ mailContent, true);
    xmlhttp.send();
}
