<?php
require './LanguageManager.php';
$languageManager = new LanguageManager();

session_start();

//$adminAcces = fopen("../config", "a+");
if(filesize("../config") == 0){die("Please use the installer to config the service");}
$file = file("../config");
if(count($file) < 5){die("Please use the installer to config the service");}
//$content = explode("AAA", fread($adminAcces, filesize("../config")));
//if(empty($content)){die("Please use the installer to config the service");}

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

if($adminUser == null || $adminUser == ""){die("Please use the installer to config the admin user");}

if(isset($_SESSION["adminConn"]) && $_SESSION["adminConn"] == ($adminUser . $adminPassword)){
    $file = fopen("page/admin/panel.html","r");
    $var = array(
        array("ADMIN_PANEL", "adminPanel"),
        array("CREATE_USER", "createUser"),
        array("MODIFY_USER", "modifyUser"),
        array("SAVE_CHANGES", "save"),
        array("SHOW_INFOS", "showInfos"),
        array("ONAME", "name"),
        array("OFIRSTNAME", "firstname"),
        array("OPASSWORD", "password"),
        array("OGROUP", "group"),
        array("SHOW", "show"),
        array("CHANGE_DB_INFOS", "changeDBInfos"),
        array("SEND", "send"),
        array("USERNAME", "username"),
        array("MAIL", "mail"),
        array("ONAME", "name"),
        array("NEW_NAME", "newName"),
        array("NEW_FIRSTNAME", "newFirstname"),
        array("NEW_PASSWORD", "newPassword"),
        array("NEW_GROUP", "newGroup")
    );

    while(!feof($file))  {
        $result = fgets($file);
        foreach($var as $keyVal){
            $result = str_replace($keyVal[0], $languageManager->getFromLang($keyVal[1]), $result);
        }
        echo $result;
    }
    
    fclose($file);
}else{
    $file = fopen("page/admin/connection.html","r");
    $var = array(
        array("ADMIN_PANEL_CONNECTION", "adminPanelConnection"),
        array("USERNAME", "username"),
        array("PASSWORD", "password"),
        array("LOGIN", "login")
    );

    while(!feof($file))  {
        $result = fgets($file);
        foreach($var as $keyVal){
            $result = str_replace($keyVal[0], $languageManager->getFromLang($keyVal[1]), $result);
        }
        echo $result;
    }
    
    fclose($file);
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