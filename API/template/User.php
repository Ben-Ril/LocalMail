<?php

namespace template;

class User{
    private string $uuid;
    private string $name;
    private string $firstname;
    private string $group;
    private bool $admin;

    public function __construct(string $uuid, string $name, string $firstname, string $group, bool $admin){
        $this->uuid = $uuid;
        $this->name = $name;
        $this->firstname = $firstname;
        $this->group = $group;
        $this->admin = $admin;
    }

    public function getUUID(): string {return $this->uuid;}

    public function getName(): string {return $this->name;}
    public function setName(string $name): void {$this->name = $name;}

    public function getFirstname(): string {return $this->firstname;}
    public function setFirstname(string $firstname): void {$this->firstname = $firstname;}

    public function getGroup(): string{return $this->group;}
    public function setGroup(string $group): void{$this->group = $group;}

    public function isAdmin(): bool {return $this->admin;}
    public function setAdmin(bool $admin): void {$this->admin = $admin;}
}