<?php

use managers\UserManager;
use managers\MailManager;
use managers\GroupManager;

class SocketManager{
    private UserManager $userManager;
    public function getUserManager(): UserManager{return $this->userManager;}

    private MailManager $mailManager;
    public function getMailManager(): MailManager{return $this->mailManager;}

    private GroupManager $groupManager;
    public function getGroupManager(): GroupManager{return $this->groupManager;}

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

        $this->userManager = new UserManager($this);
        $this->mailManager = new MailManager($this);
        $this->groupManager = new GroupManager($this);
    }

    public function isDBConnected(): bool {
        $this->sendMessage("IS DB CONNECTED");
        $response = $this->readMessage(1);
        return $response == "YES";
    }

    public function sendMessage(string $message): void {
        if($this->isError()){return;}
        socket_write($this->socket, $message, strlen($message));
    }

    public function readMessage(int $linesNumber): array | string {
        if($this->isError() || $linesNumber <= 0){return "ERROR";}

        $message = "";
        foreach (range(0, $linesNumber) as $lineNumber) {
            $read = socket_read($this->socket, 16384);
            if($linesNumber == 1 && $read == "ERROR"){
                return "ERROR";
            }
            $message .= "\n" . $read;
        }
        return explode("\n", $message);
    }
}