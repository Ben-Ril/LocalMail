<?php

namespace managers;

use template\User;

class UserManager{
    private \SocketManager $socketManager;

    public function __construct(\SocketManager $socketManager){
        $this->socketManager = $socketManager;
    }

    public function createUser(string $name, string $firstname, string $password, string $group = "none", bool $isAdmin = false): void{
        $this->socketManager->sendMessage("CREATE USER " . $name . " +<->+ " . $firstname . " +<->+ " . $password . " +<->+ " . $group . " +<->+ " . $isAdmin);
    }

    public function deleteUser(string $userUUID): void{
        $this->socketManager->sendMessage("DELETE USER " . $userUUID);
    }

    public function getUserByID(string $uuid): User|null{
        $this->socketManager->sendMessage("GET USER UUID " . $uuid);
        return $this->getBackUser();
    }

    public function getUserByName(string $name, string $firstname): User|null{
        $this->socketManager->sendMessage("GET USER UUID +<->+ " . $name . " +<->+ " . $firstname);
        return $this->getBackUser();
    }

    public function getUsers(array $users): array|null{
        foreach ($users as $user) {
            if(explode(" ", $user) == 2){

            }elseif(strlen($users == 9)){

            }else{return null;}
        }
    }

    private function getBackUser(): User|null{
        $userInfo = $this->socketManager->readMessage(5);
        if($userInfo == "ERROR"){return null;}
        return new User($userInfo[0], $userInfo[1], $userInfo[2], (strtoupper($userInfo[3]) == "TRUE"), ((strtoupper($userInfo[4]) == "TRUE")));
    }
}