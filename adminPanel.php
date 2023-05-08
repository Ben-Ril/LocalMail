<?php
foreach (glob("API/*.php") as $filename){include_once $filename;}
foreach (glob("API/managers/*.php") as $filename){include_once $filename;}
foreach (glob("API/template/*.php") as $filename){include_once $filename;}
require './LanguageManager.php';

$socketManager = new SocketManager();
$userManager = $socketManager->getUserManager();
$languageManager = new LanguageManager();

session_start();

if(filesize("../config") == 0){die("Please use the installer to config the service");}
$file = file("../config");
if(count($file) < 5){die("Please use the installer to config the service");}

$adminUser = null;
$adminPassword = null;
foreach($file as $line){
    $line = (array_search($line, $file, true) == count($file)-1 ? $line : substr($line, 0, -2));
    if(str_starts_with($line, "adminUsername=")){
        $line = str_replace("adminUsername=", "", $line);
        $adminUser = $line;
    }
    if(str_starts_with($line, "adminPassword=")){
        $line = str_replace("adminPassword=", "", $line);
        $adminPassword = $line;
    }
}


if(!isset($_SESSION["adminConn"]) || $_SESSION["adminConn"] != ($adminUser . $adminPassword)){
    header("location: mailbox.php");
    die();
}

if($_SERVER["REQUEST_METHOD"] == "GET"){
    if(isset($_GET["create"])){
        $createName = $_GET["name"];
        $createFirstname = $_GET["firstname"];
        $createPassword = $_GET["password"];

        if(isset($_GET["group"])){
            $createGroup = $_GET["group"];
            $user = $userManager ->createUser($createName,$createFirstname,$createPassword,$createGroup);
        }else{
            $user = $userManager->createUser($createName,$createFirstname,$createPassword);
        }
        echo "USER CREATED";
    }

    if(isset($_GET["modify"])){
        $mail = $_GET["mail"];
        $firstname = explode(".", explode("@", $mail)[0])[0];
        $name = explode(".", explode("@", $mail)[0])[1];
        $group = explode("@", $mail)[1];
        
        $user = $userManager->getUserByName($name,$firstname);
        if($user==null){
            echo "INVALID USER";
            exit();
        }

        $newName = $_GET["name"];
        $newFirstName = $_GET["firstname"];
        $newPassword = $_GET["password"];
        $newGroup = $_GET["group"];

        $user->setName(($newName == "") ? $user->getName() : $newName);
        $user->setFirstname(($newFirstName == "") ? $user->getFirstname() : $newFirstName);
        $user->setPassword(($newPassword == "") ? $user->getPassword() : $newPassword);
        $user->setGroup(($newGroup == "") ? $user->getGroup() : $newGroup);
        
        $userManager->modifyUser($user);
    }

    if(isset($_GET["show"])){
        $mail = $_GET["show"];
        $firstname = explode(".", explode("@", $mail)[0])[0];
        $name = explode(".", explode("@", $mail)[0])[1];
        $group = explode("@", $mail)[1];

        $user = $userManager->getUserByName($name,$firstname);
        if($user==null){
            echo "INVALID USER";
            exit();
        }
        echo($user->getName().",".$user->getFirstname().",".$user->getUUID().",".$user->getPassword().",".$user->getGroup());
    }

    if(isset($_GET["changeDB"])){
        $dbUrl = $_GET["url"];
        $dbUsername = $_GET["username"];
        $dbPassword = $_GET["password"];
        
        $file = "../config";
        $content = file($file);
        for($i = 0 ; $i < count($content) ; $i++){
            if($content[$i] == "dbURL"){$content[$i] = $dbUrl;}
            if($content[$i] == "dbUsername"){$content[$i] = $dbUsername;}
            if($content[$i] == "dbPassword"){$content[$i] = $dbPassword;}
        }
        file_put_contents($file, $content);
        $socketManager->restartDB();
        echo "DB CHANGED";
    }
}
?>