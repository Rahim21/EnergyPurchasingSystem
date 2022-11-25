<?php

include 'Revendeur.php';

$_SESSION['erreur'] = null;

function verifierFormulaire()
{
    if (empty($_POST['nom']) || empty($_POST['type']) || empty($_POST['quantite_desiree']) || empty($_POST['quantite_min']) || empty($_POST['mode_extraction']) || empty($_POST['origine_desiree']) || empty($_POST['origine_refusee']) || empty($_POST['prix']) || empty($_POST['budget'])) {
        $_SESSION['erreur'] = "Veuillez remplir tous les champs";
        return false;
    }
    if ($_POST['quantite_desiree'] < 0 || $_POST['quantite_min'] < 0 || $_POST['prix'] < 0 || $_POST['budget'] < 0) {
        $_SESSION['erreur'] = "Les valeurs ne peuvent pas être négatives";
        return false;
    }
    if ($_POST['quantite_desiree'] < $_POST['quantite_min']) {
        $_SESSION['erreur'] = "La quantité désirée ne peut pas être supérieur à la quantité minimale";
        return false;
    }
    $_SESSION['erreur'] = null;
    return true;
}

if (isset($_POST['valider'])) {
    if (/*verifierFormulaire()*/true) {
        // création de la commande
        $commande = new Commande($_POST['nom'], $_POST['type'], $_POST['quantite_desiree'], $_POST['quantite_min'], $_POST['mode_extraction'], $_POST['origine_desiree'], $_POST['origine_refusee'], $_POST['prix'], $_POST['budget']);

        // création du revendeur
        if (!isset($_SESSION['revendeur'])) {
            $_SESSION['revendeur'] = new Revendeur("Revendeur", "Reims");
        }
        // ajout de la commande au revendeur
        $_SESSION['revendeur']->ajouterCommande($commande);
        // envoi de la commande au serveur
        $result = $_SESSION['revendeur']->envoyerCommande();
    }
}
?>

<html>

<head>
    <meta charset="UTF-8">
    <title>Commande</title>
</head>

<body>
    <form action="index.php" method="POST">
        <fieldset>
            <legend>Commande</legend>
            <label for="nom">Nom</label>
            <input type="text" name="nom" id="nom" value="Commande 1">
            <br>
            <label for="type">Type d'énergie</label>
            <select name="type" id="type">
                <option value="electricite">Electricité</option>
                <option value="petrole">Pétrole</option>
                <option value="hydrogene">Hydrogène</option>
            </select>
            <br>
            <label for="quantite_desiree">Quantité désirée</label>
            <input type="number" name="quantite_desiree" id="quantite_desiree" min="0" step="0.01">
            <br>
            <label for="quantite_min">Quantité minimale</label>
            <input type="number" name="quantite_min" id="quantite_min" min="0" step="0.01">
            <br>
            <label for="mode_extraction">Mode d'extraction d'origine préconisée</label>
            <select name="mode_extraction" id="mode_extraction">
                <option value="strict">Strict</option>
                <option value="non_strict">Non strict</option>
            </select>
            <br>
            <label for="origine_desiree">Origine géographique désirée</label>
            <input type="text" name="origine_desiree" id="origine_desiree">
            <br>
            <label for="origine_refusee">Origine géographique refusée</label>
            <input type="text" name="origine_refusee" id="origine_refusee">
            <br>
            <label for="prix">Prix maximum par unité d'énergie</label>
            <input type="number" name="prix" id="prix" min="0" step="0.01">
            <br>
            <label for="budget">Budget maximum allouable à la commande</label>
            <input type="number" name="budget" id="budget" min="0" step="0.01">
            <br>
            <input type="submit" value="Envoyer" name="valider">
        </fieldset>
    </form>
    <?php
    if ($_SESSION['erreur'] != null) {
        echo "<p style='color:red'>" . $_SESSION['erreur'] . "</p>";
    }
    ?>
</body>

</html>