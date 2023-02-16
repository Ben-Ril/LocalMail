<?php

class SocketManager{
    function __construct(){
        $host = "127.0.0.1";
        $port = 15935;

        // Création du socket
        $socket = socket_create(AF_INET, SOCK_STREAM, SOL_TCP);
        if ($socket === false) {
            echo "Erreur : socket_create() a échoué : " . socket_strerror(socket_last_error()) . "\n";
            exit();
        }

        // Connexion au serveur Java
        $result = socket_connect($socket, $host, $port);
        if ($result === false) {
            echo "Erreur : socket_connect() a échoué : " . socket_strerror(socket_last_error()) . "\n";
            exit();
        }

        $message = "GET LANG\n";
        socket_write($socket, $message, strlen($message));

        /*$in = socket_read($socket, 4);
        $length = unpack("N", $in)[1];
        $data = socket_read($socket, $length);*/
        $in = socket_read($socket, 2048);
        echo "Reçut: " . $in;

        $message = "CLOSE\n";
        socket_write($socket, $message, strlen($message));
        echo "msg envoyé";

        // Fermeture du socket
        socket_close($socket);
        echo "</br>Socket Fermé";
    }
}