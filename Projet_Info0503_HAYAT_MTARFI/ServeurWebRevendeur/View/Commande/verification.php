<?php

use Model\Commande;

$resultat_certif = null;
if (isset($_POST['verification'])) {
    $message = $_POST['verification'];
    $commande_a_verif = Commande::decode($message);
    $commande_a_verif->afficher();

    $resultat_certif = Model\Revendeur::envoyerRequete($commande_a_verif);
}
if ($resultat_certif == "CERTIFICATION VALIDE") {
    header('Location: ' . $router->generate('commande') . '?success=certificat');
    exit();
} else if ($resultat_certif == "CERTIFICATION INVALIDE") {
    header('Location: ' . $router->generate('commande') . '?invalid=certificat');
    exit();
} else {
    header('Location: ' . $router->generate('commande') . '?invalid=certif-none');
    exit();
}
