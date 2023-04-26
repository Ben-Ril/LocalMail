<?php
include 'API.phar';
$socketManager = new SocketManager();
session_start();

if(!$socketManager->isDBConnected()){
    include('page/unavailable/unavailable.html');
}

$userManager = $socketManager->getUserManager();

if(isset($_SESSION["connected"]) && isset($_SESSION["uuid"]) && $_SESSION["connected"] === true && $userManager->getUserByID($_SESSION["uuid"]) != null){
    include('page/mailBox/mailBox.html');
}else{
    include("page/authentification/authentification.html");
}

if($_SERVER["REQUEST_METHOD"] == "POST"){
    if(isset($_POST["mail"]) && isset($_POST["password"])){
        $mail = explode(".", $_POST["mail"]);
        $name = $mail[0];
        $firstname = explode("@", $mail[1])[0];
        $password = $_POST["password"];

        $user = $userManager->getUserByName($name, $firstname);
        if($user === null){
            // Utilisateur innexistant
            header("location: mailbox.php");
        }
    
        if($user->getPassword() === $password){
            $_SESSION["connected"] = true;
            $_SESSION["uuid"] = $user->getUUID();
            header("location: mailbox.php");
        }else{
            header("location: mailbox.php");
        }
    }
}
?>