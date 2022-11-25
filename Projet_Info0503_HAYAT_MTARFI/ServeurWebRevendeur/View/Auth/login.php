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
    if ($auth->login($_POST['email'], $_POST['password'])) {
        header('Location: ' . $router->generate('home') . '?login=success');
        exit();
    }
    $error = true;
}
?>
<form method="POST" action="" class="login100-form validate-form">
    <span class="login100-form-title">
        Connectez-vous à votre compte <strong>SALE</strong>
        
    </span>

    <?php if ($error) : ?>
        <div class="alert alert-danger">
            Identifiant ou mot de passe incorrect
        </div>
    <?php endif; ?>

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

    <div class="container-login100-form-btn">
        <button type="submit" class="login100-form-btn">Se connecter</button>
    </div>

    <div class="text-center p-t-136">
        <a class="txt2" href="<?= $router->generate('register') ?>">
            Créer un compte
            <i class="fa fa-long-arrow-right m-l-5" aria-hidden="true"></i>
        </a>
    </div>
</form>