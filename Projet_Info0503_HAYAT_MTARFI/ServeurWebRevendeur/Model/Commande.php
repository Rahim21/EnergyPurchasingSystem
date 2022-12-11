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
    private $tab_type = ["ELERCTRICITE", "GAZ_NATUREL", "ESSENCE", "DIESEL", "SUPERCARBURANTS_SP95", "SUPERCARBURANTS_SP98", "GAZOLE", "GPL", "SUPERETHANOL", "SANS_CONTRAINTE"];
    private $tab_status = ["ENVOYER", "EN COURS", "LIVRER"];
    private $tab_pays = ["FRANCE", "ALBANIE", "ALGERIE", "ALLEMAGNE", "ANGOLA", "ARMENIE", "AUSTRALIE", "AUTRICHE", "AZERBAIDJAN", "BANGLADESH", "BELGIQUE", "BENIN", "BIRMANIE", "BOSNIE", "BULGARIE", "BULGARIE_DU_NORD", "BURUNDI", "CAMEROUN", "CANADA", "CENTRAFRIQUE", "CHAD", "CHINE", "CONGO", "COREE_DU_SUD", "COSTA_RICA", "COTE_D_IVOIRE", "CROATIE", "CUBA", "DANEMARK", "DJIBOUTI", "DOMINICAINE", "ERYTHREE", "ESPAGNE", "ESTONIE", "ETATS_UNIS", "ETIOPIE", "FINLANDE", "GABON", "GAMBIE", "GEORGIE", "GHANA", "GRECE", "GRECE_DU_NORD", "GUATEMALA", "GUINEE", "GUINEE_EQUATORIALE", "HAITI", "HONDURAS", "HONGRIE", "INDE", "INDONESIE", "ITALIE", "JAPON", "KENYA", "LAOS", "LETTONIE", "LIBYE", "LITUANIE", "MACEDOINE", "MACEDOINE_DU_NORD", "MALAISIE", "MAROC", "MAURITANIE", "MEXIQUE", "MOLDAVIE", "MONTENEGRO", "MOZAMBIQUE", "NEPAL", "NICARAGUA", "NIGER", "NIGERIA", "NORVEGE", "NOUVELLE_ZELANDE", "OUGANDA", "PANAMA", "PAYS_BAS", "PHILIPPINES", "POLOGNE", "PORTUGAL", "REPUBLIQUE_CENTRAFRICAINE", "REPUBLIQUE_DEMOGRAPHIQUE_DU_CONGO", "REPUBLIQUE_TCHEQUE", "ROUMANIE", "RWANDA", "SALVADOR", "SENEGAL", "SERBIE", "SINGAPOUR", "SLOVAQUIE", "SLOVENIE", "SOMALIE", "SUDAN", "SUEDDE", "SUISSE", "TANZANIE", "TCHECOSLOVAQUIE", "THAILANDE", "TOGO", "TUNISIE", "TURQUIE", "UKRAINE", "VIETNAM", "ZAMBIE", "ZIMBABWE", "SANS_CONTRAINTE"];

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

    public function getIdProprietaire()
    {
        return $this->idProprietaire;
    }

    public function getType()
    {
        return $this->type;
    }

    public function getOrigine()
    {
        return $this->origine;
    }

    public function getQuantite()
    {
        return $this->quantite;
    }

    public function getBudget()
    {
        return $this->budget;
    }
    public function getTabType()
    {
        return $this->tab_type;
    }
    public function getTabStatus()
    {
        return $this->tab_status;
    }
    public function getTabPays()
    {
        return $this->tab_pays;
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

    public static function encode($commande): string
    {
        $code = "";
        $code .= sprintf("%04d", $commande->getIdProprietaire());
        $code .= sprintf("%02d", array_search($commande->getType(), $commande->getTabType())); // on doit ajouter les enum dans le php aussi
        $code .= sprintf("%03d", array_search(($commande->origine), $commande->getTabPays())); // on doit ajouter les enum dans le php aussi
        $code .= mb_strlen($commande->getQuantite());
        $code .= $commande->getQuantite();
        $code .= mb_strlen($commande->getBudget());
        $code .= $commande->getBudget() * 100;
        return $code;
    }

    public static function decode($code): Commande
    {
        $tab_type = ["ELERCTRICITE", "GAZ_NATUREL", "ESSENCE", "DIESEL", "SUPERCARBURANTS_SP95", "SUPERCARBURANTS_SP98", "GAZOLE", "GPL", "SUPERETHANOL", "SANS_CONTRAINTE"];
        $tab_status = ["ENVOYER", "EN COURS", "LIVRER"];
        $tab_pays = ["FRANCE", "ALBANIE", "ALGERIE", "ALLEMAGNE", "ANGOLA", "ARMENIE", "AUSTRALIE", "AUTRICHE", "AZERBAIDJAN", "BANGLADESH", "BELGIQUE", "BENIN", "BIRMANIE", "BOSNIE", "BULGARIE", "BULGARIE_DU_NORD", "BURUNDI", "CAMEROUN", "CANADA", "CENTRAFRIQUE", "CHAD", "CHINE", "CONGO", "COREE_DU_SUD", "COSTA_RICA", "COTE_D_IVOIRE", "CROATIE", "CUBA", "DANEMARK", "DJIBOUTI", "DOMINICAINE", "ERYTHREE", "ESPAGNE", "ESTONIE", "ETATS_UNIS", "ETIOPIE", "FINLANDE", "GABON", "GAMBIE", "GEORGIE", "GHANA", "GRECE", "GRECE_DU_NORD", "GUATEMALA", "GUINEE", "GUINEE_EQUATORIALE", "HAITI", "HONDURAS", "HONGRIE", "INDE", "INDONESIE", "ITALIE", "JAPON", "KENYA", "LAOS", "LETTONIE", "LIBYE", "LITUANIE", "MACEDOINE", "MACEDOINE_DU_NORD", "MALAISIE", "MAROC", "MAURITANIE", "MEXIQUE", "MOLDAVIE", "MONTENEGRO", "MOZAMBIQUE", "NEPAL", "NICARAGUA", "NIGER", "NIGERIA", "NORVEGE", "NOUVELLE_ZELANDE", "OUGANDA", "PANAMA", "PAYS_BAS", "PHILIPPINES", "POLOGNE", "PORTUGAL", "REPUBLIQUE_CENTRAFRICAINE", "REPUBLIQUE_DEMOGRAPHIQUE_DU_CONGO", "REPUBLIQUE_TCHEQUE", "ROUMANIE", "RWANDA", "SALVADOR", "SENEGAL", "SERBIE", "SINGAPOUR", "SLOVAQUIE", "SLOVENIE", "SOMALIE", "SUDAN", "SUEDDE", "SUISSE", "TANZANIE", "TCHECOSLOVAQUIE", "THAILANDE", "TOGO", "TUNISIE", "TURQUIE", "UKRAINE", "VIETNAM", "ZAMBIE", "ZIMBABWE", "SANS_CONTRAINTE"];

        // récupérer les 4 premier int pour l'id du propriétaire
        $idProprietaire = intval(substr($code, 0, 4));
        // récupérer les 2 int suivant pour le type donc récupère son index dans le tableau
        $type = $tab_type[intval(substr($code, 4, 2))];
        // récupérer les 3 int suivant pour l'origine donc récupère son index dans le tableau
        $origine = $tab_pays[intval(substr($code, 6, 3))];
        // récupérer le nombre de chiffre qui représente la quantité
        $nbChiffreQuantite = intval(substr($code, 9, 1));
        // récupérer la quantité
        $quantite = intval(substr($code, 10, $nbChiffreQuantite));
        // budget 11 jusqu'à la fin
        $budget = intval(substr($code, 11 + $nbChiffreQuantite, strlen($code) - 11)) / 100;
        return new Commande($idProprietaire, $type, $origine, $quantite, $budget);
    }
}
