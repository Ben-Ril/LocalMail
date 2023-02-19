<?php

namespace template;

class User{
    private string $uuid;
    private string $name;
    private string $firstname;
    private string $password;
    private string $group;
    private bool $admin;

    public function __construct(string $uuid, string $name, string $firstname, string $password, string $group = "none", bool $admin = false){
        $this->uuid = $uuid;
        $this->name = $name;
        $this->firstname = $firstname;
        $this->password = $password;
        $this->group = $group;
        $this->admin = $admin;
    }

    public function getUUID(): string {return $this->uuid;}

    public function getName(): string {return $this->name;}
    public function setName(string $name): User {$this->name = $name; return $this;}

    public function getFirstname(): string {return $this->firstname;}
    public function setFirstname(string $firstname): User {$this->firstname = $firstname; return $this;}

    public function getPassword(): string {return $this->password;}
    public function setPassword(string $password): User {$this->password = $password; return $this;}

    public function getGroup(): string{return $this->group;}
    public function setGroup(string $group): User {$this->group = $group; return $this;}

    public function isAdmin(): bool {return $this->admin;}
    public function setAdmin(bool $admin): User {$this->admin = $admin; return $this;}
}