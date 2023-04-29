<?php
foreach (glob("API/*.php") as $filename){include_once $filename;}
foreach (glob("API/managers/*.php") as $filename){include_once $filename;}
foreach (glob("API/template/*.php") as $filename){include_once $filename;}
require './LanguageManager.php';
$socketManager = new SocketManager();
$languageManager = new LanguageManager();
session_start();

if(!$socketManager->isDBConnected()){
    $file = fopen("page/unavailable/unavailable.html","r");
    $var = array(
        array("UNAVAILABLE", "unavailable"),
        array("SORRYUNAV", "unavailableMessage")
    );
  
    while(!feof($file))  {
        $result = fgets($file);
        foreach($var as $keyVal){
            $result = str_replace($keyVal[0], $languageManager->getFromLang($keyVal[1]), $result);
        }
        echo $result;
    }
    
    fclose($file);
    die(0);
}

while($socketManager->getUserManager() == null){$i = 0;}
$userManager = $socketManager->getUserManager();

if(isset($_SESSION["connected"]) && isset($_SESSION["uuid"]) && $_SESSION["connected"] === true && $userManager->getUserByID($_SESSION["uuid"]) != null){
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
    $file = fopen("page/authentification/authentification.html", "r");
    $var = array(
        array("AUTHENTIFICATION", "authentification"),
        array("MAIL", "mail"),
        array("PASSWORD", "password"),
        array("LOGIN", "login")
    );

    while(!feof($file))  {
        $result = fgets($file);
        foreach($var as $keyVal){
            $result = str_replace($keyVal[0], $languageManager->getFromLang($keyVal[1]), $result);
        }
        echo $result;
    }
    
    fclose($file);
}

if($_SERVER["REQUEST_METHOD"] == "POST"){
    if(isset($_POST["mail"]) && isset($_POST["password"])){
        $mail = explode(".", $_POST["mail"]);
        $firstname = $mail[0];
        $name = explode("@", $mail[1])[0];
        $group = explode("@", $mail[1])[1];
        $password = $_POST["password"];

        $user = $userManager->getUserByName($name, $firstname);
        if($user === null){
            // Inexistant user
            header("location: mailbox.php");
            return;
        }

        if($user->getPassword() === $password && $user->getGroup() === $group){
            $_SESSION["connected"] = true;
            $_SESSION["uuid"] = $user->getUUID();
            header("location: mailbox.php");
        }
    }
}
if($_SERVER["REQUEST_METHOD"] == "GET"){
    if(isset($_GET['disconnect'])){
        $_SESSION["connected"] = false;
        $_SESSION["uuid"] = "";
        header("location: mailbox.php");
    }
}
?>