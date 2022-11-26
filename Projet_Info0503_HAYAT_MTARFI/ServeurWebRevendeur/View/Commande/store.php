<?php
require('../Controller/CommandeController.php');
$commande = new App\CommandeController("Commande.json");
$commande->storeCommande();

$commande->getCommande($_SESSION['auth']);
header('Location: ' . $router->generate('commande') . '?success=send');
exit();
