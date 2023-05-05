<?php
foreach (glob("API/*.php") as $filename){include_once $filename;}
foreach (glob("API/managers/*.php") as $filename){include_once $filename;}
foreach (glob("API/template/*.php") as $filename){include_once $filename;}
require './LanguageManager.php';

$socketManager = new SocketManager();
$userManager = $socketManager->getUserManager();
$configManager = $socketManager->getConfigManager();
$languageManager = new LanguageManager();

if($_SERVER["REQUEST_METHOD"] == "GET"){

    if(isset($_GET["createName"])){
        $createName = $_GET["createName"];
        $createFirstname = $_GET["createFirstname"];
        $createPassword = $_GET["createPassword"];

        if(isset($_GET["createGroup"])){
            $createGroup = $_GET["createGroup"];
            $user = $userManager ->createUser($createName,$createFirstname,$createPassword,$createGroup);
        }
        else{
            $user = $userManager->createUser($createName,$createFirstname,$createPassword);
        }

    }

    if(isset($_GET["modifyMail"])){
        $modifyMail = $_GET["modifyMail"];
        $modifyMail = explode("@",$modifyMail);
        $modifyMail = explode(".", $modifyMail[0]);
        
        $user = $userManager->getUserByName($modifyMail[0],$modifyMail[1]);
        if($user==null){
            echo("invalid_user");
            exit();
        }
        
        $list = array(
            "name"=>($_GET["modifyName"] == "" ? $user->getName() : $_GET["modifyName"]),
            "firstname"=>($_GET["modifyFirstname"] == "" ? $user->getFirstname() : $_GET["modifyFirstname"]),
            "password"=>($_GET["modifyPassword"] == "" ? $user->getPassword() : $_GET["modifyPassword"]),
            "group"=>($_GET["modifyGroup"] == "" ? $user->getGroup() : $_GET["modifyGroup"])
        );
        $user->setName($list["name"]);
        $user->setFirstname($list["firstname"]);
        $user->setPassword($list["password"]);
        $user->setGroup($list["group"]);
        $userManager->modifyUser($user);
    }

    if(isset($_GET["showMail"])){
        $showMail = $_GET["showMail"];
        $showMail = explode("@",$showMail);
        $showMail = explode(".", $showMail[0]);

        $user = $userManager->getUserByName($showMail[0],$showMail[1]);
        if($user==null){
            echo("invalid_user");
            exit();
        }
        echo($user->getName().",".$user->getFirstname().",".$user->getUUID().",".$user->getPassword().",".$user->getGroup());
    }

    if(isset($_GET["dbUrl"])){
        $dbUrl = $_GET["dbUrl"];
        $dbUsername = $_GET["dbUsername"];
        $dbPassword = $_GET["dbPassword"];

        $configManager->setDataBase($dbUrl,$dbUsername,$dbPassword);
    }
}
?>