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
    if (isset($_GET["boxStatus"]) && isset($_SESSION["connected"]) && isset($_SESSION["uuid"]) && $_SESSION["connected"] == true && $userManager->getUserByID($_SESSION["uuid"]) != null){
        $separator = "//<->//";
        $mails = $mailManager->getMailsByUser($_SESSION["uuid"], $_GET["boxStatus"] == "true");

        if($mails != null && count($mails) != 0){
            $mailsArray = array();
            foreach($mails as $mail){
                $mailUUID = $mail->getMailUUID();
                $sender = $userManager->getUserByID($mail->getSenderUUID());
                $mailSender = ($sender == null ? "ERROR" : $sender->getFirstname() . "." . $sender->getName() . "@" . $sender->getGroup());
                $receivers = "";
                foreach($mail->getReceiversUUIDs() as $rUUID){
                    $ru = $userManager->getUserByID($rUUID);
                    if($ru != null){
                        $receivers = $receivers . $ru->getFirstname() . "." . $ru->getName() . "@" . $ru->getGroup() . " ";
                    }
                }
                $receivers = substr($receivers, 0 , -1);

                $dateTimestamp = $mail->getDateTimestamp();
                $object = $mail->getObject();
                $content = $mail->getContent();

                $mailArray = array("uuid" => $mailUUID, "sender" => $mailSender, "receivers" => $receivers, "date" => $dateTimestamp, "object" => $object, "content" => $content);
                array_push($mailsArray, $mailArray);
            }
            echo json_encode($mailsArray);
        }else{echo "NO MAIL";}
    }
}

$socketManager->disconnect();
?>