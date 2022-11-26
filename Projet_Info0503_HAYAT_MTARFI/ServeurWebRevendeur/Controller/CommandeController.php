<?php

namespace App;

use Model\Commande;
use Model\Revendeur;

// require('../Model/Commande.php');

class CommandeController
{
    private $json;

    public function __construct(string $json)
    {
        $this->json = '../Database/' . $json;
    }

    public function storeCommande()
    {
        if (isset($_POST['valider'])) {
            if (isset($_POST['type']) && isset($_POST['origine']) && isset($_POST['quantite']) && isset($_POST['budget'])) {
                $idProprietaire = $_SESSION['auth'];
                $type = $_POST['type'];
                $origine = $_POST['origine'];
                $quantite = $_POST['quantite'];
                $budget = floatval($_POST['budget']);

                // création de l'objet Commande
                $commande = new Commande($idProprietaire, $type, $origine, $quantite, $budget);

                // enregistrement dans le fichier json au format [ '1':{commande1}, '2':{commande2}, ... ]
                $json = file_get_contents($this->json);
                $json = json_decode($json, true);
                $json[$idProprietaire][] = $commande->jsonSerialize();
                $json = json_encode($json, JSON_PRETTY_PRINT);
                file_put_contents($this->json, $json);

                // Le Revendeur
                $revendeur = new Revendeur();
                // ajout de la commande au revendeur
                $revendeur->ajouterCommande($commande);
                // envoi de la commande au serveur
                $result = $revendeur->envoyerCommande();
            } else {
                header('Location: commande-create?invalid=send');
                exit();
            }
        }
    }

    public function getCommande($id)
    {
        $json = file_get_contents($this->json);
        $json = json_decode($json, true);
        // vérifier si l'id existe avant de retourner le $json
        if (isset($json[$id])) {
            return $json[$id];
        }
        return null;
    }
}
