<?php

namespace Model;

use JsonSerializable;

class Commande implements JsonSerializable
{
    private $idProprietaire;
    private $type;
    private $origine;
    private $quantite;
    private $budget;
    private $codeDeSuivi;
    private $date = null;

    public function __construct(int $idProprietaire, string $type, string $origine, int $quantite, float $budget)
    {
        $this->idProprietaire = $idProprietaire;
        $this->type = $type;
        $this->origine = $origine;
        $this->quantite = $quantite;
        $this->budget = $budget;
        $this->codeDeSuivi = "En Attente de traitement";
        if ($this->date == null) {
            $this->date = date("d/m/Y");
        }
    }

    public function afficher()
    {
        echo "Identifiant du propriétaire : " . $this->idProprietaire . "<br>";
        echo "Type d'énergie: " . $this->type . "<br>";
        echo "Quantité désirée: " . $this->quantite . "<br>";
        echo "Origine géographique : " . $this->origine . "<br>";
        echo "Budget maximum allouable à la commande: " . $this->budget . "<br><br>";
    }

    public function getCodeDeSuivi()
    {
        return $this->codeDeSuivi;
    }

    public function getDate()
    {
        return $this->date;
    }

    // type de retour array ?
    public function jsonSerialize(): array
    {
        $json['idProprietaire'] = $this->idProprietaire;
        $json['type'] = $this->type;
        $json['origine'] = $this->origine;
        $json['quantite'] = $this->quantite;
        $json['budget'] = $this->budget;
        return $json;
    }

    public static function fromJSON($json)
    {
        $obj = json_decode($json);
        return new Commande($obj->idProprietaire, $obj->type, $obj->origine, $obj->quantite, $obj->budget);
    }
}
