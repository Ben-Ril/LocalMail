<?php

namespace managers;

use \SocketManager;

class GroupManager{
    private SocketManager $socketManager;

    public function __construct(SocketManager $socketManager){
        $this->socketManager = $socketManager;
    }

    public function getGroups(): array|null{
        $this->socketManager->sendMessage("GET GROUPS");
        $groups = explode(" +<->+ ", $this->socketManager->readMessage(1));
        if(empty($groups)){return null;}
        return $groups;
    }

    public function createGroup(string $groupName): void{
        $this->socketManager->sendMessage("CREATE GROUP " . $groupName);
    }

    public function deleteGroup(string $groupName): void{
        $this->socketManager->sendMessage("DELETE GROUP " . $groupName);
    }
}