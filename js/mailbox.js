function sended(){
    sendStatus(false);
}

function received(){
    sendStatus(true);
}

function sendStatus(status){
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            alert(this.responseText);
        }
    };

      
    xmlhttp.open("GET", "../php/authentification.php?" + "boxStatus="+ status + "$sendMail=false", true);
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
    xmlhttp.open("GET", "../php/authentification.php?" + "receiver="+ receiver + "&object=" + object +"&mailContent"+ mailContent + "$sendMail=true", true);
    xmlhttp.send();
}

function disconnect(){
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("GET", "mailbox.php?disconnect=true", true);
    xmlhttp.send();
    location.reload();
}