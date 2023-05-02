<?php

namespace managers;

use template\User;
use \SocketManager;

class UserManager{
    private SocketManager $socketManager;

    public function __construct(SocketManager $socketManager){
        $this->socketManager = $socketManager;
    }

    public function createUser(string $name, string $firstname, string $password, string $group = "none"): User{
        $this->socketManager->sendMessage("CREATE USER " . $name . " +<->+ " . $firstname . " +<->+ " . $password . " +<->+ " . $group);
        return $this->getUserByName($name, $firstname);
    }

    public function deleteUser(string $userUUID): void{
        $this->socketManager->sendMessage("DELETE USER " . $userUUID);
    }

    public function modifyUser(User $user): void{
        $this->socketManager->sendMessage("MODIFY USER " . $user->getUUID() . " +<->+ " . $user->getName() . " +<->+ " . $user->getFirstname() . " +<->+ " . $user->getPassword() . " +<->+ " . $user->getGroup());
    }

    public function getUserByID(string $uuid): User|null{
        $this->socketManager->sendMessage("GET USER UUID " . $uuid);
        return $this->getBackUser();
    }

    public function getUserByName(string $name, string $firstname): User|null{
        $this->socketManager->sendMessage("GET USER NAME //<->// " . $name . " //<->// " . $firstname);
        return $this->getBackUser();
    }

    public function getUsersByInfos(array $usersInfo): array{
        $usersArray = array();
        foreach ($usersInfo as $userInfo) {
            if(explode(" ", $userInfo) == 2){
                $ui = explode(" ", $userInfo);
                $user = $this->getUserByName($ui[0], $ui[1]);
                if($user == null){$user = $this->getUserByName($ui[1], $ui[0]);}

                if($user != null){$usersArray[] = $user;}
            }elseif(strlen($userInfo) == 9){
                $user = $this->getUserByID($userInfo);
                if($user != null){$usersArray[] = $user;}
            }
        }
        return $usersArray;
    }

    private function getBackUser(): User|null{
        $userInfo = $this->socketManager->readMessage(5);
        if($userInfo == "ERROR"){return null;}
        return new User($userInfo[0], $userInfo[1], $userInfo[2], $userInfo[3], $userInfo[4]);
    }
}