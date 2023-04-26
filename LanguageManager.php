<?php
class LanguageManager{
    private $lang;
    private $language;
    private $configData;

    function __construct(){
        $this->configData = json_decode(file_get_contents('../config.json'),true);

        if(!isset($this->configData["lang"])){die("Invalid Config");}
        
        $this->lang = json_decode(file_get_contents('lang/' . $this->configData["lang"] . ".json"),true);
        $this->language = $this->configData["lang"];
    }

    public function getFromLang(string $parameter):string {
        return (isset($this->lang[$parameter]) ? $this->lang[$parameter] : "non-defined");
    }

    public function setLanguage(string $language):void{
        $availableLanguage = array("french", "english");
        if(!array_key_exists($language, $availableLanguage)){return;}
        $this->configData["lang"] = $language;
        file_put_contents('./defaultDB.php', json_encode($this->configData));
    }
}
?>