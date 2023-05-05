function sended(){
    sendStatus(true);
}

function received(){
    sendStatus(false);
}

function sendStatus(isSendBox){
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("GET", "mailGateway.php?" + "boxStatus="+ isSendBox, true);
    xmlhttp.timeout = 10;

    xmlhttp.onreadystatechange = function(){
        if(xmlhttp.readyState == 4 && xmlhttp.status==200){
            const response = this.responseText;
            const mailListSection = document.getElementById("mailListSection");
            while(mailListSection.firstChild){
                mailListSection.removeChild(mailListSection.firstChild);
            }

            if(response != "NO MAIL" && response != "ERROR"){
                alert(response);
                const json = JSON.parse(response);
                    
                Object.keys(json).forEach(function(k){
                    const uuid = json[k]["uuid"];
                    // @mail
                    const sender = json[k]["sender"];
                    // String de @mails
                    let receivers = json[k]["receivers"];
                    receivers = (receivers.includes(" ") ? receivers.split(" ") : receivers);
                    let date = new Date(Number(json[k]["date"])*1000);
                    date = date.toLocaleString();
                    const object = json[k]["object"];
                    const content = json[k]["content"];

                    const mail = document.createElement("section");
                    const mailInerSection = document.createElement("section");
                    const mailObject = document.createElement("p");
                    const mailSender = document.createElement("p");
                    const mailDate = document.createElement("p");
                    const mailContentPreview = document.createElement("p");

                    mail.className = "mail";
                        
                    mailObject.className = "mailObject mailP";
                    mailObject.textContent = object;

                    mailSender.className = "senderMail mailP";
                    mailSender.textContent = sender;

                    mailDate.className = "mailDate mailP";
                    mailDate.textContent = date;

                    mailContentPreview.className = "mailContentPreview mailP";
                    mailContentPreview.textContent = (content.length > 100 ? String.prototype.substring(0,100,content) + "..." : content);

                    mailInerSection.appendChild(mailObject);
                    mailInerSection.appendChild(mailSender);
                    mailInerSection.appendChild(mailDate);

                    mail.appendChild(mailInerSection);
                    mail.appendChild(mailContentPreview);

                    mailListSection.appendChild(mail);
                    place();
                });
            }
        }
    }

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
    xmlhttp.open("GET", "mailBox.php?" + "receiver="+ receiver + "&object=" + object +"&mailContent"+ mailContent + "$sendMail=true", true);
    xmlhttp.send();
}

function disconnect(){
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("GET", "mailbox.php?disconnect=true", true);
    xmlhttp.send();
    location.reload();
}

function openMailView(mailUUID){

}