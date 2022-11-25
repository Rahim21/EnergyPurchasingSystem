<?php

namespace Model;

class User
{
    public $id;
    public $lastname;
    public $firstname;
    public $email;
    public $password;
    public $role;

    public function __construct($id, $lastname, $firstname, $email, $password, $role)
    {
        $this->id = $id;
        $this->lastname = $lastname;
        $this->firstname = $firstname;
        $this->email = $email;
        $this->password = $password;
        $this->role = $role;
    }
}
