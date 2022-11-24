<?php

use App\AuthController;

require('../Controller/AuthController.php');
$error = false;

$auth = new AuthController("User.json");
if ($auth->user() !== null) {
    header('Location: ' . $router->generate('home'));
    exit();
}
if (!empty($_POST)) {
    $auth = new AuthController("User.json");
    $user = $auth->register($_POST['lastname'], $_POST['firstname'], $_POST['email'], $_POST['password'], $_POST['role']);
    if ($user) {
        header('Location: ' . $router->generate('home'));
        exit();
    }
    $error = true;
}
?>
<form method="POST" action="" class="login100-form validate-form">
    <span class="login100-form-title">
        Créer un compte chez <strong>SALE</strong>
    </span>

    <div class="wrap-input100 validate-input" data-validate="Un nom est requis">
        <input type="text" name="lastname" id="lastname" placeholder="Nom" class="input100 " required>
        <span class="focus-input100"></span>
        <span class="symbol-input100">
            <i class="fa fa-envelope" aria-hidden="true"></i>
        </span>
    </div>

    <div class="wrap-input100 validate-input" data-validate="Un prénom est requis">
        <input type="text" name="firstname" id="firstname" placeholder="Prénom" class="input100 " required>
        <span class="focus-input100"></span>
        <span class="symbol-input100">
            <i class="fa fa-envelope" aria-hidden="true"></i>
        </span>
    </div>

    <div class="wrap-input100 validate-input" data-validate="Un email valide est requis: exemple@email.xyz">
        <input type="email" name="email" id="email" placeholder="Email" class="input100 " required>
        <span class="focus-input100"></span>
        <span class="symbol-input100">
            <i class="fa fa-envelope" aria-hidden="true"></i>
        </span>
    </div>

    <div class="wrap-input100 validate-input" data-validate="Un mot de passe est requis">
        <input type="password" name="password" id="password" placeholder="Mot de passe" class="input100" required>
        <span class="focus-input100"></span>
        <span class="symbol-input100">
            <i class="fa fa-lock" aria-hidden="true"></i>
        </span>
    </div>

    <!-- champs select pour le role : CLIENT ou PRODUCTEUR -->
    <div class="wrap-input100 validate-input" data-validate="Un rôle est requis">
        <select name="role" id="role" class="input100" required>
            <option value="CLIENT">Client</option>
            <option value="PRODUCTEUR">Producteur</option>
        </select>
        <span class="focus-input100"></span>
        <span class="symbol-input100">
            <i class="fa fa-lock" aria-hidden="true"></i>
        </span>
    </div>

    <div class="container-login100-form-btn">
        <button type="submit" class="login100-form-btn">S'inscrire</button>
    </div>

    <div class="text-center p-t-136">
        <a class="txt2" href="<?= $router->generate('login') ?>">
            Se connecter
            <i class="fa fa-long-arrow-right m-l-5" aria-hidden="true"></i>
        </a>
    </div>
</form>