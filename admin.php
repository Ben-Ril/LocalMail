<?php
session_start();

$adminAcces = fopen("adminAcces.php", "a+");
if(filesize("adminAcces.php") == 0){die("Please use the installer to config the admin user");}
$content = explode("\\+-+\\", fread($adminAcces, filesize("adminAcces.php")));
if(empty($content)){die("Please use the installer to config the admin user");}

$adminUser = null;
$adminPassword = null;
foreach($content as $line){
    if(str_starts_with($line, "name=")){
        $line = str_replace("name=", "", $line);
        $adminUser = $line;
    }
    if(str_starts_with($line, "password=")){
        $line = str_replace("password=", "", $line);
        $adminPassword = $line;
    }
}

if($adminUser == null || $adminUser == ""){die("Please use the installer to config the admin user :3");}

if(isset($_SESSION["adminConn"]) && $_SESSION["adminConn"] == ($adminUser . $adminPassword)){
    include("page/admin/panel.html");
}else{
    include("page/admin/connection.html");
}

if($_SERVER["REQUEST_METHOD"] == "POST"){
    $username = $_POST["username"];
    $password = $_POST["password"];

    if($username == $adminUser && $password == $adminPassword){
        $_SESSION["adminConn"] = $username . $password;
        header("location: admin.php");
    }
}

if($_SERVER["REQUEST_METHOD"] == "GET"){
    if(isset($_GET["disconnect"])){
        $_SESSION["adminConn"] = "";
        header("location: admin.php");
    }
}
?>