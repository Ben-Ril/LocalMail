<?php
class LanguageManager{
    private $file;
    private $lang;
    private $language;
    //private $configData;
    private $availableLanguage = array("francais", "english");

    function __construct(){
        $this->file = "../config";
        $content = file($this->file);

        foreach($content as $line){
            
            if(str_starts_with($line, "lang=")){
                $this->language = str_replace("lang=", "", $line);
                break;
            }
        }

        if($this->language == null){die("Invalid configuration");}
        if(!array_key_exists($this->language, $this->availableLanguage)){$this->setLanguage("english");}
        $this->lang = json_decode(file_get_contents('lang/' . $this->language . ".json"), true);

        /*$this->configData = json_decode(file_get_contents('../config.json'),true);

        if(!isset($this->configData["lang"])){die("Invalid Config");}*/
    }

    public function getFromLang(string $parameter):string {
        return (isset($this->lang[$parameter]) ? $this->lang[$parameter] : "non-defined");
    }

    public function setLanguage(string $language):void{
        if(!array_key_exists($language, $this->availableLanguage)){return;}
        $content = file($this->file);
        for($i = 0 ; $i < count($content) ; $i++){
            if(str_starts_with($content[$i], "lang=")){
                $content[$i] = "lang=" . $language;
                break;
            }
        }
        file_put_contents($this->file, $content);
        $this->language = $language;
        $this->lang = json_decode(file_get_contents('lang/' . $this->language . ".json"), true);
    }
}
?>