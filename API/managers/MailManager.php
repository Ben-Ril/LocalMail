<?php

namespace managers;

use template\Mail;

class MailManager{
    private \SocketManager $socketManager;

    public function __construct(\SocketManager $socketManager){
        $this->socketManager = $socketManager;
    }

    public function getUserByID(string $uuid): Mail|null{
        $this->socketManager->sendMessage("GET USER UUID " . $uuid);
        return $this->getBaskMail();
    }

    public function getUserByName(string $name, string $firstname): Mail|null{
        $this->socketManager->sendMessage("GET USER UUID +<->+ " . $name . " +<->+ " . $firstname);
        return $this->getBaskMail();
    }

    private function getBaskMail(): Mail|null{
        $userInfo = $this->socketManager->readMessage(5);
        if($userInfo == "ERROR"){return null;}
        return new Mail($userInfo[0], $userInfo[1], $userInfo[2], $userInfo[3], $userInfo[4], $userInfo[5]);
    }
}