<?php
use managers\MailManager;
include 'phar://API.phar/SocketManager.php';
require './LanguageManager.php';
$socketManager = new SocketManager();
$languageManager = new LanguageManager();
$mailManager = $socketManager->getMailManager();
session_start();

if(!$socketManager->isDBConnected()){
    include('page/unavailable/unavailable.html');
}

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
}

if($_SERVER["REQUEST_METHOD"] == "POST"){
    if(isset($_POST["mail"]) && isset($_POST["password"])){
        $mail = explode(".", $_POST["mail"]);
        $name = $mail[0];
        $firstname = explode("@", $mail[1])[0];
        $password = $_POST["password"];

        $user = $userManager->getUserByName($name, $firstname);
        if($user === null){
            // Inexistant user
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
}
if($_SERVER["REQUEST_METHOD" == "GET"] && $_GET["sendMail" == true]){
    $receiversInput = $_GET["receiver"];
    $receiversOutput = explode(",", $receiversInput);
    $receiversUUID = array();
    foreach($receiversOutput as $i){
        $i = explode("@", $i);
        $i = explode(".", $i[0]);
        $receiverUUID= $userManager->getUserByName($i[0],$i[1]);
        array_push($receiversUUID, $receiverUUID);
    }
    foreach($receiversUUID as $i){
        if($i==null){
            echo("invalid receiver");
        }
    }
    $senderUUID = $_SESSION["uuid"];
    $object = $_GET["object"];
    $content = $_GET["mailContent"];

    $mail = $mailManager->createMail($senderUUID, $receiversUUID, $object, $content);
}
?>
