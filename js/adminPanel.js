function createUser(){
    let createName = document.getElementById("createName").value;
    let createFirstname = document.getElementById("createFirstanme").value;
    let createPassword = document.getElementById("createPassword").value;
    let createGroup = document.getElementById("createGroup").value;

    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("GET", "adminPanel.php?" + "createName=" + createName + "&createFirstname=" + createFirstname + "&createPassword=" + createPassword + (createGroup != "" ? "&createGroup=" + createGroup : ""), true);
    xmlhttp.send();
}

function modifyUser(){
    let response = "";

    let modifyMail = document.getElementById("modifyMail").value;
    let modifyName = document.getElementById("modifyName").value;
    let modifyFirstname = document.getElementById("modifyFirstname").value;
    let modifyPassword = document.getElementById("modifyPassword").value;
    let modifyGroup = document.getElementById("modifyGroup").value;

    var xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            response = this.responseText;
        }
    };

    xmlhttp.open("GET", "adminPanel.php?" + "modifyMail=" + modifyMail + "&modifyName=" + modifyName + "&mofifyFirstname=" + modifyFirstname + "&modifyPassword" + modifyPassword + "&modifyGroup=" + modifyGroup, true);
    xmlhttp.send();

    if(response != ""){alert(response);}
}

function showInfo(){
    let showName = document.getElementById("showName");
    let showFirstname = document.getElementById("showFirstname");
    let showPassword = document.getElementById("showPassword");
    let showUUID = document.getElementById("showUUID");
    let showGroup = document.getElementById("showGroup");
    let response = "";
    let showMail = document.getElementById("showMail").value;

    var xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            response = this.responseText;
        }
    };
    xmlhttp.open("GET", "adminPanel.php?" + "showMail=" + showMail, true);
    xmlhttp.send();

    if(response != ""){
        let info = response.split(",");
        showName.textContent = "name : " + info[0];
        showFirstname.textContent = "firstname : " + info[1];
        showPassword.textContent = "password : " + info[2];
        showUUID.textContent = "UUID : " + info[3];
        showGroup.textContent = "Group : " + info[4];

    }
    if(response == "invalid_user"){alert(response);}
}

function changeDb(){
    let dbUrl = document.getElementById("dbUrl").value;
    let dbUsername = document.getElementById("dbUsername").value;
    let dbPassword = document.getElementById("dbPassword").value;

    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("GET", "adminPanel.php?" + "&dbUrl=" + dbUrl + "&dbUsername=" + dbUsername + "&dbPassword=" + dbPassword, true);
    xmlhttp.send();
}
