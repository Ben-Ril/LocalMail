function createUser(){
    let createName = document.getElementById("createName").value;
    let createFirstname = document.getElementById("createFirstanme").value;
    let createPassword = document.getElementById("createPassword").value;
    let createGroup = document.getElementById("createGroup").value;

    var xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
        alert(this.responseText);
    }
    };

    if (createGroup != ""){
        xmlhttp.open("GET", "adminPanel.php?"+ "createUser=true" + "&createName=" + createName + "&createFirstname=" + createFirstname + "&createPassword=" + createPassword + "&createGroup=" + createGroup, true);
    }
    else{xmlhttp.open("GET", "adminPanel.php?"+ "createUser= true" + "&createName=" + createName + "&createFirstname=" + createFirstname + "&createPassword=" + createPassword, true);}
    xmlhttp.send();
}

function modifyUser(){
    let modifyMail = document.getElementById("modifyMail").value;
    let modifyName = document.getElementById("modifyName").value;
    let modifyFirstname = document.getElementById("modifyFirstname").value;
    let modifyPassword = document.getElementById("modifyPassword").value;
    let modifyGroup = document.getElementById("modifyGroup").value;

    var xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
        alert(this.responseText);
    }
    };

    xmlhttp.open("GET", "adminPanel.php?" + "modifyMail=" + modifyMail + "&modifyUser=true" + "&modifyName=" + modifyName + "&mofifyFirstname=" + modifyFirstname + "&modifyPassword" + modifyPassword + "&modifyGroup=" + modifyGroup, true);
    xmlhttp.send();
}

function showInfo(){
    let showMail = document.getElementById("showMail").value;

    var xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
        alert(this.responseText);
    }
    };
    xmlhttp.open("GET", "adminPanel.php?" + "showInfo=true" + "&showMail=" + showMail, true);
    xmlhttp.send();
}

function changeDb(){
    let dbUrl = document.getElementById("dbUrl").value;
    let dbUsername = document.getElementById("dbUsername").value;
    let dbPassword = document.getElementById("dbPassword").value;

    var xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
        alert(this.responseText);
    }
    };
    xmlhttp.open("GET", "adminPanel.php?" + "changeDb=true" + "&dbUrl=" + dbUrl + "&dbUsername=" + dbUsername + "&dbPassword=" + dbPassword, true);
    xmlhttp.send();
}
