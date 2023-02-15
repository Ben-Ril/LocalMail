document.getElementById("sendButton").onclick = function(){
    var httpRequest = new XMLHttpRequest();
    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value
    
    httpRequest.open("POST", url);
    httpRequest.responseType = "text";
    httpRequest.send(email+";"+password);
    httpRequest.onload = function(){
        if(httpRequest.status===201){
            console.log("Post réussi")
        }
    }
}