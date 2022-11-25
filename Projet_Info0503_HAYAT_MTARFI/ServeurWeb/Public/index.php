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
            require_once('../Controller/AuthController.php');
            $auth = new App\AuthController("User.json");
            $user = $auth->user();
            require_once('../View/landing.php');
        } elseif ($match['target'] === 'Auth/login' || $match['target'] === 'Auth/register') {
            require_once('../View/Layouts/auth_top.php');
            require_once("../View/{$match['target']}.php");
            require_once('../View/Layouts/auth_bottom.php');
        } else {
            require_once('../Controller/AuthController.php');
            $auth = new App\AuthController("User.json");
            $user = $auth->user();
            if ($user === null) {
                header('Location: ' . $router->generate('login'));
                exit();
            }
            require_once('../View/Layouts/header.php');
            require_once("../View/{$match['target']}.php");
            require_once('../View/Layouts/footer.php');
        }
        // alerte accès
        if (isset($_GET['forbid'])) {
            echo "<div class='alert alert-danger' style='width: 300px; position: absolute; top: 10px; right: 10px;'>
                L'accès à cette page est interdit.
            </div>";
        }
        if (isset($_GET['login'])) {
            echo "<div class='alert alert-success' role='alert' style='width: 300px; position: absolute; top: 10px; right: 10px;'>
                Vous êtes connecté !
            </div>";
        }
        echo "<script>setTimeout(() => {document.querySelector('.alert').remove();}, 3000);</script>";
    }
} else {
    echo '404';
    // créer une page 404
}
