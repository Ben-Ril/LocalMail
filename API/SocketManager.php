<?php

use managers\UserManager;
use managers\MailManager;

class SocketManager{
    private UserManager|null $userManager = null;
    public function getUserManager(): UserManager|null{return $this->userManager;}

    private MailManager|null $mailManager = null;
    public function getMailManager(): MailManager|null{return $this->mailManager;}

    private $socket;
    private bool $error = false;
    public function isError(): bool {return $this->error;}

    function __construct() {
        $host = "127.0.0.1";
        $port = 15935;

        $this->socket = socket_create(AF_INET, SOCK_STREAM, SOL_TCP);
        if ($this->socket === false) {
            $this->error = true;
            return;
        }

        $result = socket_connect($this->socket, $host, $port);
        if ($result === false) {
            $this->error = true;
            return;
        }

        if($this->isDBConnected()){
            $this->userManager = new UserManager($this);
            $this->mailManager = new MailManager($this);
        }
    }

    public function isDBConnected(): bool {
        $this->sendMessage("IS DB CONNECTED");
        $response = $this->readMessage(1);
        return $response == "YES";
    }

    public function sendMessage(string $message): void {
        if($this->isError()){return;}
        socket_write($this->socket, $message . "\n", strlen($message . "\n"));
    }

    public function readMessage(int $linesNumber): array | string {
        if($this->isError() || $linesNumber <= 0){return "ERROR";}
        if($linesNumber == 1){return socket_read($this->socket, 16384);}
        $message = "";
        foreach (range(1, $linesNumber) as $lineNumber) {
            $read = socket_read($this->socket, 16384);
            if($lineNumber == 1 && $read == "ERROR"){
                return "ERROR";
            }
            $message .= $read . "\n";
        }
        return explode("\n", $message);
    }

    public function setDataBase(string $url, string $username, string $password): void{
        $this->sendMessage("MODIFY DATABASE " . $url . " " . $username . " " . $password);
    }
    public function getDataBaseURL(): string{
        $this->sendMessage("GET DATABASE");
        return $this->readMessage(1);
    }
}