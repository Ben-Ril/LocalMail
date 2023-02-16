<?php

class SocketManager{
    private $socket;
    private $error = false;
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
        }
    }

    public function sendMessage(string $message){
        if($this->isError()){return;}
        socket_write($this->socket, $message, strlen($message));
    }

    public function readMessage(int $linesNumber): string{
        if($this->isError() || $linesNumber <= 0){return "ERROR";}

        $message = "";
        foreach (range(0, $linesNumber) as $lineNumber){
            $message .= "\n" . socket_read($this->socket, 16384);
        }
        return $message;
    }
}