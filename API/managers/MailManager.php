<?php

namespace managers;

use template\Mail;
use \SocketManager;

class MailManager{
    private SocketManager $socketManager;

    public function __construct(SocketManager $socketManager){
        $this->socketManager = $socketManager;
    }

    public function createMail(string $senderUUID, array $receiversUUID, string $object, string $content, array $attachments = null): Mail{
        $receivers = "";
        foreach ($receiversUUID as $receiver) {
            if($this->socketManager->getUserManager()->getUserByID($receiver) != null){
                $receivers .= $receiver . "<-->";
            }
        }
        $receivers = substr($receivers, 0, -4);

        $date = time();

        $attachmentsList = "";
        if($attachments != null){
            foreach ($attachments as $attachment) {$attachmentsList .= $attachment . "<-->";}
            $attachmentsList = substr($attachmentsList, 0, -4);
        }else{$attachmentsList = "none";}

        $this->socketManager->sendMessage("CREATE MAIL " . $senderUUID . " //<->// " . $receivers . " //<->// " . $object . " //<->// " . $content . " //<->// " . $date . " //<->// " . $attachmentsList);
        return $this->getMail($this->socketManager->readMessage(1));
    }

    public function getMail(string $uuid): Mail|null{
        $this->socketManager->sendMessage("GET USER UUID " . $uuid);
        return $this->getBackMail();
    }

    public function getMailsByUser(string $userUUID, bool $sender): array|null{
        if($this->socketManager->getUserManager()->getUserByID($userUUID) == null){return null;}

        $this->socketManager->sendMessage("GET MAILS " . ($sender ? "SENDER" : "RECEIVER") . $userUUID);
        $mailNumber = intval($this->socketManager->readMessage(1));

        if($mailNumber == null || $mailNumber == 0){return null;}

        $mailsArray = array();
        for($i = 0 ; $i < $mailNumber ; $i++){
            $mail = $this->getBackMail();
            $mailsArray[] = $mail;
        }
        return $mailsArray;
    }

    private function getBackMail(): Mail|null{
        $userInfo = $this->socketManager->readMessage(7);
        if($userInfo == "ERROR"){return null;}
        return new Mail($userInfo[0], $userInfo[1], explode("<-->", $userInfo[2]), $userInfo[3], $userInfo[4], $userInfo[5], explode("<-->", $userInfo[6]));
    }
}