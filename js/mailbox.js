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
                    const sender = json[k]["sender"];
                    const receivers = json[k]["receivers"];
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
                    mail.setAttribute("object", object);
                    mail.setAttribute("content", content);
                    mail.setAttribute("sender", sender);
                    mail.setAttribute("receivers", receivers);
                    mail.onclick = openMailView(mail);
                        
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
    let receiversArray = document.getElementById("destinatorsInput").value.split();
    let receivers = ""
    Array.prototype.forEach(receiversArray, function(receiver){
        while(receiver.startsWith(" ")){receiver = receiver.substr(1);}
        while(receiver.endsWith(" ")){receiver = receiver.substr(0,receiver.length-1);}
        if(receiver != ""){
            receivers = receiver + "-%-";
        }
    });
    receivers = receivers.substring(0,receivers.length-3);
    let object = document.getElementById("objectInput").value;
    let mailContent = document.getElementById("newMailMessageContent");

    var xmlhttp = new XMLHttpRequest();
    /*xmlhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            alert(this.responseText);
        }
    };
    xmlhttp.onload = function(){
        let isSent = this.responseText;
    }*/
    xmlhttp.open("GET", "mailGateway.php?" + "receiver="+ receivers + "&object=" + object +"&mailContent"+ mailContent + "$sendMail=true", true);
    xmlhttp.send();
}

function disconnect(){
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("GET", "mailbox.php?disconnect=true", true);
    xmlhttp.send();
    location.reload();
}

function openMailView(mail){
    let mailView = document.getElementById("mailView");
    document.getElementById("mailViewMails").textContent = mail.getAttribute("sender") + " > " + mail.getAttribute("receivers");
    document.getElementById("mailViewTitle").textContent = mail.getAttribute("object");
    document.getElementById("mailViewContent").textContent = mail.getAttribute("content");
    mailView.style.visibility = "block";
}

0616430027