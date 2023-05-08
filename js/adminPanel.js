function createUser(){
    let createName = document.getElementById("createName").value;
    let createFirstname = document.getElementById("createFirstname").value;
    let createPassword = document.getElementById("createPassword").value;
    let createGroup = document.getElementById("createGroup").value;

    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("GET", "adminPanel.php?create=yes&name=" + createName + "&firstname=" + createFirstname + "&password=" + createPassword + (createGroup != "" ? "&group=" + createGroup : ""), true);
    xmlhttp.send();
}

function modifyUser(){

    let mail = document.getElementById("modifyMail").value;
    let name = document.getElementById("modifyName").value;
    let firstname = document.getElementById("modifyFirstname").value;
    let password = document.getElementById("modifyPassword").value;
    let group = document.getElementById("modifyGroup").value;

    var xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            let response = this.responseText;
            alert(response);
        }
    };

    xmlhttp.open("GET", "adminPanel.php?modify=yes&mail=" + mail + "&name=" + name + "&firstname=" + firstname + "&password=" + password + "&group=" + group, true);
    xmlhttp.send();
}

function showInfo(){
    let showName = document.getElementById("showName");
    let showFirstname = document.getElementById("showFirstname");
    let showPassword = document.getElementById("showPassword");
    let showUUID = document.getElementById("showUUID");
    let showGroup = document.getElementById("showGroup");
    let showMail = document.getElementById("showMail").value;

    var xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            const response = this.responseText;
            if(response == "" || response == "invalid_user"){
                alert("Invalid User");
                return;
            }
            const userInfos = response.split(",");

            if(userInfos.length != 5){
                alert("ERROR");
                return;
            }

            showName.textContent = userInfos[0];
            showFirstname.textContent = userInfos[1];
            showPassword.textContent = userInfos[3];
            showUUID.textContent = userInfos[2];
            showGroup.textContent = userInfos[4];
        }
    };
    xmlhttp.open("GET", "adminPanel.php?show=" + showMail, true);
    xmlhttp.send();
}

function changeDb(){
    let url = document.getElementById("dbUrl").value;
    let username = document.getElementById("dbUsername").value;
    let password = document.getElementById("dbPassword").value;

    if(url == "" || username == ""){
        alert("Invalid DB infos");
        return;
    }

    var xmlhttp = new XMLHttpRequest();

    xmlhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            const response = this.responseText;
            alert(response);
        }
    }

    xmlhttp.open("GET", "adminPanel.php?changeDB=true&url=" + url + "&username=" + username + "&password=" + password, true);
    xmlhttp.send();
}
