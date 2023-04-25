function connect(){
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    if(username.length == 0 || password.length == 0){
        alert("Empty username or password");
        return;
    }

    var xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function() {
      if (this.readyState == 4 && this.status == 200) {
        if(this.responseText == "CORRECT"){
            window.location("./panel.php");
        }else {
            alert("Invalid couple username-password");
            return;
        }
      }
    };
    xmlhttp.open("GET", "https://google.com/connect.php?username=" + username + "&password=" + password, true);
    xmlhttp.send();
}