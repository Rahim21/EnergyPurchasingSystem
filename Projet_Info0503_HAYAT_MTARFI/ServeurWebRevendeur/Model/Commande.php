<?php

class Commande implements JsonSerializable
{
    private $nom;
    private $type;
    private $quantite;
    private $quantite_min;
    private $mode_extraction;
    private $origine_desiree;
    private $origine_refusee;
    private $prix;
    private $budget;

    public function __construct($nom, $type, $quantite, $quantite_min, $mode_extraction, $origine_desiree, $origine_refusee, $prix, $budget)
    {
        $this->nom = $nom;
        $this->type = $type;
        $this->quantite = $quantite;
        $this->quantite_min = $quantite_min;
        $this->mode_extraction = $mode_extraction;
        $this->origine_desiree = $origine_desiree;
        $this->origine_refusee = $origine_refusee;
        $this->prix = $prix;
        $this->budget = $budget;
    }

    public function afficher()
    {
        echo "Nom : " . $this->nom . "<br>";
        echo "Type d'énergie: " . $this->type . "<br>";
        echo "Quantité désirée: " . $this->quantite . "<br>";
        echo "Quantité minimale: " . $this->quantite_min . "<br>";
        echo "Mode d'extraction d'origine préconisée: " . $this->mode_extraction . "<br>";
        echo "Origine géographique désirée: " . $this->origine_desiree . "<br>";
        echo "Origine géographique refusée: " . $this->origine_refusee . "<br>";
        echo "Prix maximum par unité d'énergie: " . $this->prix . "<br>";
        echo "Budget maximum allouable à la commande: " . $this->budget . "<br><br>";
    }

    public function jsonSerialize() // il est appelé automatiquement par json_encode()
    {
        $json['nom'] = $this->nom;
        $json['type'] = $this->type;
        $json['quantite'] = $this->quantite;
        $json['quantite_min'] = $this->quantite_min;
        $json['mode_extraction'] = $this->mode_extraction;
        $json['origine_desiree'] = $this->origine_desiree;
        $json['origine_refusee'] = $this->origine_refusee;
        $json['prix'] = $this->prix;
        $json['budget'] = $this->budget;
        return $json;
    }

    public static function fromJSON($json)
    {
        $obj = json_decode($json);
        return new Commande($obj->nom, $obj->type, $obj->quantite, $obj->quantite_min, $obj->mode_extraction, $obj->origine_desiree, $obj->origine_refusee, $obj->prix, $obj->budget);
    }
}
