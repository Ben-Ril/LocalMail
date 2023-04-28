<?php
include 'API.phar';
$socketManager = new SocketManager();
$userManager = $socketManager->getUserManager();
$userName = $_GET["userName"];
$userFirstName = $_GET['userFirstName'];
$userPassword = $_GET['password'];
$gettedUser = $userManager->getUserByName($userName, $userFirstName);
if ($gettedUser == null){
    echo"";
}
else{
    $password = $gettedUser->getPassword();
    if ($password==$userPassword){
        session_start();
        $_SESSION["UUID"] = $gettedUser->getUUID();
        header('Location: '.$_SERVER['SERVER_NAME'].'/mailBox.php');
    }

}

?>
