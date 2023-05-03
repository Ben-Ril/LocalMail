<?php
foreach (glob("API/*.php") as $filename){include_once $filename;}
foreach (glob("API/managers/*.php") as $filename){include_once $filename;}
foreach (glob("API/template/*.php") as $filename){include_once $filename;}
require './LanguageManager.php';

$socketManager = new SocketManager();
if(!$socketManager->isDBConnected()){die("ERROR");}
$languageManager = new LanguageManager();
$mailManager = $socketManager->getMailManager();
$userManager = $socketManager->getUserManager();

session_start();

if($_SERVER["REQUEST_METHOD"] == "GET"){
    if (isset($_GET["boxStatus"])){
        $separator = "//<->//";
        $mails = $mailManager->getMailsByUser($_GET["uuid"], $_GET["boxStatus"] == "true");

        if($mails != null && count($mails) != 0){
            $mailsArray = array();
            foreach($mails as $mail){
                $mailUUID = $mail->getMailUUID();
                $mailSender = $mail->getSenderUUID();
                $receivers = $mail->getReceiversUUIDs();
                $dateTimestamp = $mail->getDateTimestamp();
                $object = $mail->getObject();
                $content = $mail->getContent();

                $mailArray = array("uuid" => $mailUUID, "sender" => $mailSender, "receivers" => $receivers, "date" => $dateTimestamp, "object" => $object, "content" => $content);
                array_push($mailsArray, $mailArray);
            }
            echo json_encode($mailArray);
        }else{echo "NO MAIL";}
    }
}
?>