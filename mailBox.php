<?php
include 'API.phar';
require './LanguageManager.php';
//$socketManager = new SocketManager();
$languageManager = new LanguageManager();
session_start();

//if(!$socketManager->isDBConnected()){
//    include('page/unavailable/unavailable.html');
//}

//$userManager = $socketManager->getUserManager();

$_SESSION["connected"] = true;
$_SESSION["uuid"] = "ffe";

if(isset($_SESSION["connected"]) && isset($_SESSION["uuid"]) && $_SESSION["connected"] === true /*&& $userManager->getUserByID($_SESSION["uuid"]) != null*/){
    //include('page/mailBox/mailBox.html');
    $file = fopen("page/mailBox/mailBox.html","r");
    $var = array(
        array("OMAILBOX", "mailbox"),
        array("OSEARCH", "search"),
        array("ONEW_MAIL", "newMail"),
        array("OSENDED_MAIL", "sendedMail"),
        array("ORECEIVED_MAIL", "receivedMail"),
        array("OLOGOUT", "logout"),
        array("ODESTINATOR", "destinator"),
        array("OOBJECT", "mailObject"),
        array("OMAIL_MESSAGE_CONTENT", "mailContent"),
        array("OSEND", "send")
    );
  
    while(!feof($file))  {
        $result = fgets($file);
        foreach($var as $keyVal){
            $result = str_replace($keyVal[0], $languageManager->getFromLang($keyVal[1]), $result);
        }
        echo $result;
    }
    
    fclose($file);
}else{
    include("page/authentification/authentification.html");
}

/*if($_SERVER["REQUEST_METHOD"] == "POST"){
    if(isset($_POST["mail"]) && isset($_POST["password"])){
        $mail = explode(".", $_POST["mail"]);
        $name = $mail[0];
        $firstname = explode("@", $mail[1])[0];
        $password = $_POST["password"];

        $user = $userManager->getUserByName($name, $firstname);
        if($user === null){
            // Utilisateur innexistant
            header("location: mailbox.php");
        }
    
        if($user->getPassword() === $password){
            $_SESSION["connected"] = true;
            $_SESSION["uuid"] = $user->getUUID();
            header("location: mailbox.php");
        }else{
            header("location: mailbox.php");
        }
    }
}*/
?>