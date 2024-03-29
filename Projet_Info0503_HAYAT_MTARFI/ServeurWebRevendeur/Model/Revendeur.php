<?php

namespace Model;

use JsonSerializable;

class Revendeur implements JsonSerializable
{
    private $nom;
    private $adresse;
    // private $commandes;
    public $commande;

    public function __construct($nom = "Johne Doe", $adresse = "Rue Boulevard Port-Royal")
    {
        $this->nom = $nom;
        $this->adresse = $adresse;
        // $this->commandes = array();
        $this->commande = $this->commande;
    }

    public function ajouterCommande($commande)
    {
        // $this->commandes[] = $commande;
        $this->commande = $commande;
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
                'content' => 'COMMANDE:::' . $data
            ]
        ];

        // Envoi de la requête et lecture du JSON reçu
        // URL : l'adresse locale vers tare.html
        $URL = "http://localhost:8080/tare.html";
        $contexte  = stream_context_create($options);
        $jsonTexte = @file_get_contents($URL, false, $contexte);
        // dd($jsonTexte);
        // fromJSON pour créer un objet de type Revendeur ensuite on affiche les commandes

        // $revendeur = Revendeur::fromJSON($jsonTexte);
        // echo "Affichage côté revendeur PHP : <br><br>";
        // if (isset($revendeur)) {
        //     $revendeur->afficherCommandes();
        //     $revendeur->updateCommande();
        // }

        // dd($revendeur);
        // action du revendeur après réception de la réponse depuis le Marche de Gros [à faire]
    }
    // Mettre à jour la commande avec le certificat et le code de suivi ?
    public function updateCommande()
    {

        // suppression de la commande du revendeur
        $this->commande = null;

        // Dans le fichier Commande.json, mettre à jour la commande du client avec l'id de la session avec $_SESSION['auth']
        // appel CommandeController->updateCommande()
        // App\CommandeController::updateCommande($_SESSION['auth'], $this->commande->getCodeDeSuivi());

    }

    public function verif_certificat()
    {
        // Le client envoie le CodeDeSuivi ? Certificat ? au serveur
    }

    public function afficherCommandes()
    {
        if (isset($this->commande)) {
            // foreach ($this->commandes as $commande) {
            //     $commande->afficher();
            // }
            $this->commande->afficher();
        }
    }

    public function getCodeDeSuivi()
    {
        return $this->commande->getCodeDeSuivi();
    }

    // retourne un array ?
    public function jsonSerialize(): array
    {
        // $json['nom'] = $this->nom;
        // $json['adresse'] = $this->adresse;
        // $json['commandes'] = $this->commandes;
        $json['commande'] = $this->commande;
        return $json;
    }

    public static function fromJSON($json): ?Revendeur
    {

        $json = substr($json, 0, strrpos($json, '}') + 1);
        $json = '{"commande":' . $json . '}';
        // dd($json);
        $obj = json_decode($json, true);
        if (isset($obj)) {
            // $revendeur = new Revendeur($obj['nom'], $obj['adresse']);
            $revendeur = new Revendeur();
            if (isset($obj['commande'])) {
                // foreach ($obj['commandes'] as $commande) {
                //     $revendeur->ajouterCommande(new Commande($commande['idProprietaire'], $commande['type'], $commande['origine'], $commande['quantite'], $commande['budget']));
                // }
                $revendeur->ajouterCommande(new Commande($obj['commande']['idProprietaire'], $obj['commande']['type'], $obj['commande']['origine'], $obj['commande']['quantite'], $obj['commande']['budget']));
            }
            // $revendeur->ajouterCommande(new Commande($obj['idProprietaire'], $obj['type'], $obj['origine'], $obj['quantite'], $obj['budget']));
            return $revendeur;
        }
        return null;
    }

    public static function envoyerRequete($donnee)
    {
        $data = urlencode(json_encode($donnee));

        $options = [
            'http' =>
            [
                'method'  => 'POST',
                'header'  => 'Content-type: application/x-www-form-urlencoded',
                'content' => 'VERIFICATION:::' . $data
            ]
        ];

        // Envoi de la requête et lecture du JSON reçu
        // URL : l'adresse locale vers tare.html
        $URL = "http://localhost:8080/tare.html";
        $contexte  = stream_context_create($options);
        $jsonTexte = @file_get_contents($URL, false, $contexte);
        $resultat_certif = substr($jsonTexte, 0, strpos($jsonTexte, "\x00"));
        return $resultat_certif;
    }
}
