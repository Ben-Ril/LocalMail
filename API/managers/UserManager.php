<?php

namespace managers;

use template\User;
use \SocketManager;

class UserManager{
    private SocketManager $socketManager;

    public function __construct(SocketManager $socketManager){
        $this->socketManager = $socketManager;
    }

    public function createUser(string $name, string $firstname, string $password, string $group = "none", bool $isAdmin = false): User{
        $this->socketManager->sendMessage("CREATE USER " . $name . " +<->+ " . $firstname . " +<->+ " . $password . " +<->+ " . $group . " +<->+ " . $isAdmin);
        return $this->getUserByName($name, $firstname);
    }

    public function deleteUser(string $userUUID): void{
        $this->socketManager->sendMessage("DELETE USER " . $userUUID);
    }

    public function modifyUser(User $user): void{
        $this->socketManager->sendMessage("MODIFY USER " . $user->getUUID() . " +<->+ " . $user->getName() . " +<->+ " . $user->getFirstname() . " +<->+ " . $user->getPassword() . " +<->+ " . $user->getGroup() . " +<->+ " . $user->isAdmin());
    }

    public function getUserByID(string $uuid): User|null{
        $this->socketManager->sendMessage("GET USER UUID " . $uuid);
        return $this->getBackUser();
    }

    public function getUserByName(string $name, string $firstname): User|null{
        $this->socketManager->sendMessage("GET USER UUID +<->+ " . $name . " +<->+ " . $firstname);
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

    public function getUsersByGroup(string $groupName): array|null{
        $groups = $this->socketManager->getGroupManager()->getGroups();
        if($groups == null){return null;}

        foreach($groups as $group){
            if(str_contains($group, $groupName)){
                $users = array();
                $entryNumber = intval(explode(" ", $group)[1]);
                if($entryNumber == 0){return $users;}
                $this->socketManager->sendMessage("GET USERS " . explode(" ", $group)[0]);
                for($i = 0 ; $i < $entryNumber ; $i++){$users[] = $this->getBackUser();}
                return $users;
            }
        }
        return null;
    }

    private function getBackUser(): User|null{
        $userInfo = $this->socketManager->readMessage(6);
        if($userInfo == "ERROR"){return null;}
        return new User($userInfo[0], $userInfo[1], $userInfo[2], $userInfo[3], (strtoupper($userInfo[4]) == "TRUE"), ((strtoupper($userInfo[5]) == "TRUE")));
    }
}