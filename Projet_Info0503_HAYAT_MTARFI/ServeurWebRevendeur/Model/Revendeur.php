<?php

namespace Model;

use JsonSerializable;

class Revendeur implements JsonSerializable
{
    private $nom;
    private $adresse;
    private $commandes;

    public function __construct($nom = "Johne Doe", $adresse = "Rue Boulevard Port-Royal")
    {
        $this->nom = $nom;
        $this->adresse = $adresse;
        $this->commandes = array();
    }

    public function ajouterCommande($commande)
    {
        $this->commandes[] = $commande;
    }

    // on envoie la commande avec le nom et l'adresse du revendeur au serveur
    public function envoyerCommande()
    {
        // encoder l'objet Revendeur
        $data = urlencode(json_encode($this));

        $options = [
            'http' =>
            [
                'method'  => 'POST',
                'header'  => 'Content-type: application/x-www-form-urlencoded',
                'content' => $data
            ]
        ];

        // Envoi de la requête et lecture du JSON reçu
        // URL : l'adresse locale vers tare.html
        $URL = "http://localhost:8080/tare.html";
        $contexte  = stream_context_create($options);
        $jsonTexte = @file_get_contents($URL, false, $contexte);

        // fromJSON pour créer un objet de type Revendeur ensuite on affiche les commandes

        $revendeur = Revendeur::fromJSON($jsonTexte);
        echo "Affichage côté revendeur PHP : <br><br>";
        if (isset($revendeur)) {
            $revendeur->afficherCommandes();
        }
    }

    public function afficherCommandes()
    {
        if (isset($this->commandes)) {
            foreach ($this->commandes as $commande) {
                $commande->afficher();
            }
        }
    }

    // retourne un array ?
    public function jsonSerialize(): array
    {
        $json['nom'] = $this->nom;
        $json['adresse'] = $this->adresse;
        $json['commandes'] = $this->commandes;
        return $json;
    }

    public static function fromJSON($json): ?Revendeur
    {
        $obj = json_decode($json, true);
        if (isset($obj)) {
            $revendeur = new Revendeur($obj['nom'], $obj['adresse']);
            if (isset($obj['commandes'])) {
                foreach ($obj['commandes'] as $commande) {
                    $revendeur->ajouterCommande(new Commande($commande['nom'], $commande['type'], $commande['quantite'], $commande['quantite_min'], $commande['mode_extraction'], $commande['origine_desiree'], $commande['origine_refusee'], $commande['prix'], $commande['budget']));
                }
            }
            return $revendeur;
        }
        return null;
    }
}