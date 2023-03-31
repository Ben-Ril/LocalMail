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
      xmlhttp.open("GET", url + "&mail=&" + document.getElementById("mailInput").value+"&password"+ document.getElementById("passwordInput").value, true);
      xmlhttp.send();
    }
  }