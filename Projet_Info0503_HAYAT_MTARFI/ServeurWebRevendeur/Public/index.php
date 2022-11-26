<?php
// Important pour récupérer les URI depuis notre rooter : .htaccess
session_start();
require '../vendor/autoload.php';
$uri = $_SERVER['REQUEST_URI'];
$router = new AltoRouter();

$router->map('GET', '/', 'landing', 'landing');
// auth
$router->map('GET|POST', '/login', 'Auth/login', 'login');
$router->map('GET', '/logout', 'Auth/logout', 'logout');
$router->map('GET|POST', '/register', 'Auth/register', 'register');
$router->map('GET', '/home', 'Auth/home', 'home');
// commande
$router->map('GET', '/commande', 'Commande/index', 'commande');
$router->map('GET', '/commande-create', 'Commande/create', 'commande_create');
$router->map('POST', '/commande-store', 'Commande/store', 'commande_store');
// production
$router->map('GET', '/production', 'Production/index', 'production');

/*
Exemple d'utilisation :
<a href="<?= $router->generate('login') ?>">Page login</a>
OU
$router->map('GET', '/article/[*:slug]-[i:id]', 'article/create', 'create'); // /article/nimporte-quoi-5
donc pour appeler ce lien : <a href="<?= $router->generate('create', ['slug' => 'nimporte-quoi', 'id' => 5]) ?>">Créer un article</a>
*/

$match = $router->match();
if (is_array($match)) {

    if (is_callable($match['target'])) {
        call_user_func_array($match['target'], $match['params']);
    } else {
        $params = $match['params'];
        if ($match['target'] === 'landing') {
            require('../Controller/AuthController.php');
            $auth = new App\AuthController("User.json");
            $user = $auth->user();
            require('../View/landing.php');
        } elseif ($match['target'] === 'Auth/login' || $match['target'] === 'Auth/register') {
            require('../View/Layouts/auth_top.php');
            require("../View/{$match['target']}.php");
            require('../View/Layouts/auth_bottom.php');
        } else {
            require('../Controller/AuthController.php');
            $auth = new App\AuthController("User.json");
            $user = $auth->user();
            if ($user === null) {
                header('Location: ' . $router->generate('login'));
                exit();
            }
            require('../View/Layouts/header.php');
            require("../View/{$match['target']}.php");
            require('../View/Layouts/footer.php');
        }
        // Affichage des alertes
        new App\AlertController();
    }
} else {
    echo '404';
    // créer une page 404
}
