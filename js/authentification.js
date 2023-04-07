function requestInfo(str){
    if (str.length == 0) {
      document.getElementById("txtHint").innerHTML = "";
      return;
    } else {
      var xmlhttp = new XMLHttpRequest();
      xmlhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
          alert(this.responseText);
        }
      };

      let mail = document.getElementById("mailInput").value;
      let array = mail.split("@");
      let userName = array[0].split(".")[0];
      let userFirstName = array[0].split(".")[1];
      
      xmlhttp.open("GET", "../php/authentification.php?" + "userName="+ userName + "&userFirstName=" + userFirstName +"&password"+ document.getElementById("passwordInput").value, true);
      xmlhttp.send();
    }
  }