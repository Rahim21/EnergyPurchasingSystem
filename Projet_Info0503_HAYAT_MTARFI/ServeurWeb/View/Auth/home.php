<h1>Page HOME</h1>

<?php if (isset($_GET['login'])) : ?>
    <div class="alert alert-success" role="alert" style="width: 300px; position: absolute; top: 10px; right: 10px;">
        Vous êtes connecté !
    </div>
    <script>
        setTimeout(() => {
            document.querySelector('.alert').remove();
        }, 3000);
    </script>
<?php endif; ?>

<?php if ($user) : ?>
    <p>Bonjour <?= $user->lastname ?> <?= $user->firstname ?></p>
    <p>Vous êtes connecté en tant que <?= $user->role ?></p>
    <a href="<?= $router->generate('logout') ?>" class="btn btn-primary">Se déconnecter</a>
<?php endif; ?>