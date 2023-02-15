document.getElementById("sendButton").onclick = function(){
    var httpRequest = new XMLHttpRequest();
    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value
    
    httpRequest.open("POST", url);
    httpRequest.responseType = "text";
    xhr.setRequestHeader('Content-type', 'text; charset=UTF-8')
    httpRequest.send("email="+email+"&password="+password);
    httpRequest.onload = function(){
        if(httpRequest.status===201){
            console.log("Post réussi")
        }
    }
}