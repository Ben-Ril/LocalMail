<?php
foreach (glob("API/*.php") as $filename){include_once $filename;}
foreach (glob("API/managers/*.php") as $filename){include_once $filename;}
foreach (glob("API/template/*.php") as $filename){include_once $filename;}
require './LanguageManager.php';

$socketManager = new SocketManager();
$languageManager = new LanguageManager();
$mailManager = $socketManager->getMailManager();

session_start();

if($_SERVER["REQUEST_METHOD"] == "GET"){
    if (isset($_GET["boxStatus"])){
        $mails = $mailManager->getMailsByUser($_GET["uuid"], $_GET["boxStatus"] == "true");
    }
    foreach($mails as $mail){
        echo($mail);
    }
}
?>