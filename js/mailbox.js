function sended(){
    sendStatus(false);
}

function received(){
    sendStatus(true);
}

function sendStatus(isReceiveBox){
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            const response = this.responseText;
            if(response != "NO MAIL"){
                const json = JSON.parse(response);
                const mailListSection = document.getElementById("mailListSection");
                mailListSection.innerHTML = ""; 
                Object.keys(json).forEach(function(k){
                    const uuid = json[k]["uuid"];
                    // @mail
                    const sender = json[k]["sender"];
                    // String de @mails
                    const receivers = json[k]["receivers"].split(" ");
                    let date = new Date(json[k]["date"]);
                    day = `${date.getDay}/${date.getMonth}/${date.getFullYear} ${date.getHours}:${date.getMinutes}`;
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
                    mailContentPreview.textContent = (content.length() > 100 ? String.prototype.substring(0,100,content) + "..." : content);

                    mailInerSection.appendChild(mailObject);
                    mailInerSection.appendChild(mailSender);
                    mailInerSection.appendChild(mailDate);

                    mailListSection.appendChild(mailInerSection);
                    mailListSection.appendChild(mailContentPreview);
                });
            }
        }
    };

    xmlhttp.open("GET", "mailGateway.php?" + "isReceiver="+ (isReceiveBox ? "true" : "false"), true);
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