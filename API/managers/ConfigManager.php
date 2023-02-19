<?php

namespace managers;

use \SocketManager;

class ConfigManager{
    private SocketManager $socketManager;

    public function __construct(SocketManager $socketManager){
        $this->socketManager = $socketManager;
    }

    public function setLanguage(string $language): void{
        if(strlen($language) != 2){return;}
        $this->socketManager->sendMessage("MODIFY LANG " . $language);
    }
    public function getLanguage(): string{
        $this->socketManager->sendMessage("GET LANG");
        return $this->socketManager->readMessage("1");
    }

    public function setDataBase(string $url, string $username, string $password): void{
        $this->socketManager->sendMessage("MODIFY DATABASE " . $url . " " . $username . " " . $password);
    }
    public function getDataBaseURL(): string{
        $this->socketManager->sendMessage("GET DATABASE");
        return $this->socketManager->readMessage(1);
    }
}