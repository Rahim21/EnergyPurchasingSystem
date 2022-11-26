<?php

namespace App;

use Model\User;

require('../Model/User.php');

class AuthController
{
    private $json;

    public function __construct(string $json)
    {
        $this->json = '../Database/' . $json;
    }

    public function user(): ?User
    {
        if (session_status() === PHP_SESSION_NONE) {
            session_start();
        }
        $id = $_SESSION['auth'] ?? null;
        if ($id === null) {
            return null;
        }
        // à l'aide de l'id $id, on va chercher l'utilisateur dans le fichier json et retourner un objet User
        $users = json_decode(file_get_contents($this->json), true);
        foreach ($users as $user) {
            if ($user['id'] === $id) {
                return new User($user['id'], $user['lastname'], $user['firstname'], $user['email'], $user['password'], $user['role']);
            }
        }
        return null;
    }

    public function login(string $email, string $password): ?User
    {
        // trouve l'utilisateur correspondant à l'email
        $users = json_decode(file_get_contents($this->json), true);
        $user = null;
        if ($users !== null) {
            foreach ($users as $u) {
                if ($u['email'] === $email) {
                    $user = $u;
                    break;
                }
            }
        }
        if ($user === null) {
            return null;
        }

        // vérifie le mot de passe
        if (password_verify($password, $user['password'])) {
            // connecte l'utilisateur + session
            if (session_status() === PHP_SESSION_NONE) {
                session_start();
            }
            $_SESSION['auth'] = $user['id'];
            return new User($user['id'], $user['lastname'], $user['firstname'], $user['email'], $user['password'], $user['role']);
        } else {
            return null;
        }
    }

    public function register(string $lastname, string $firstname, string $email, string $password, string $role): ?User
    {
        // vérifie que l'email n'est pas déjà utilisé
        $users = json_decode(file_get_contents($this->json), true);
        if ($users !== null) {
            foreach ($users as $u) {
                if ($u['email'] === $email) {
                    return null;
                }
            }
        }
        // ajoute l'utilisateur
        $id = $users === null ? 1 : count($users) + 1;
        $user = new User(
            $id,
            $lastname,
            $firstname,
            $email,
            password_hash($password, PASSWORD_DEFAULT),
            $role
        );
        $users[] = $user;
        file_put_contents($this->json, json_encode($users, JSON_PRETTY_PRINT));

        // connecte l'utilisateur + session
        if (session_status() === PHP_SESSION_NONE) {
            session_start();
        }
        $_SESSION['auth'] = $user->id;
        return $user;
    }

    public function requireRole(string ...$roles): void
    {
        $user = $this->user();
        if ($user === null || !in_array($user->role, $roles)) {
            header('Location: home?invalid=forbid');
            exit();
        }
    }
}
