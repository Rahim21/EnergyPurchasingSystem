package Enum;

public enum Pays {
    // indetifer chaque pays avec son indicatif telephonique
    FRANCE("FRANCE"), // 000
    ALLEMAGNE("ALLEMAGNE"), // 001
    ESPAGNE("ESPAGNE"), // 002
    ITALIE("ITALIE"), // 003
    PORTUGAL("PORTUGAL"), // 004
    ROUMANIE("ROUMANIE"), // 005
    GRECE("GRECE"), // 006
    BELGIQUE("BELGIQUE"), // 007
    SUISSE("SUISSE"), // 008
    AUTRICHE("AUTRICHE"), // 009
    PAYS_BAS("PAYS_BAS"), // 010
    DANEMARK("DANEMARK"), // 011
    SUEDDE("SUEDDE"), // 012
    NORVEGE("NORVEGE"), // 013
    FINLANDE("FINLANDE"), // 014
    POLOGNE("POLOGNE"), // 015
    TCHECOSLOVAQUIE("TCHECOSLOVAQUIE"), // 016
    HONGRIE("HONGRIE"), // 017
    BULGARIE("BULGARIE"), // 018
    REPUBLIQUE_TCHEQUE("REPUBLIQUE_TCHEQUE"), // 019
    SLOVAQUIE("SLOVAQUIE"), // 020
    ESTONIE("ESTONIE"), // 021
    LETTONIE("LETTONIE"), // 022
    LITUANIE("LITUANIE"), // 023
    UKRAINE("UKRAINE"), // 024
    MOLDAVIE("MOLDAVIE"), // 025
    BOSNIE("BOSNIE"), // 026
    CROATIE("CROATIE"), // 027
    MACEDOINE("MACEDOINE"), // 028
    SERBIE("SERBIE"), // 029
    SLOVENIE("SLOVENIE"), // 030
    ALBANIE("ALBANIE"), // 031
    MONTENEGRO("MONTENEGRO"), // 032
    MACEDOINE_DU_NORD("MACEDOINE_DU_NORD"), // 033
    BULGARIE_DU_NORD("BULGARIE_DU_NORD"), // 034
    GRECE_DU_NORD("GRECE_DU_NORD"), // 035
    TURQUIE("TURQUIE"), // 036
    ARMENIE("ARMENIE"), // 037
    AZERBAIDJAN("AZERBAIDJAN"), // 038
    GEORGIE("GEORGIE"), // 039
    MAROC("MAROC"), // 040
    TUNISIE("TUNISIE"), // 041
    ALGERIE("ALGERIE"), // 042
    LIBYE("LIBYE"), // 043
    MAURITANIE("MAURITANIE"), // 044
    SENEGAL("SENEGAL"), // 045
    GAMBIE("GAMBIE"), // 046
    GUINEE("GUINEE"), // 047
    GUINEE_EQUATORIALE("GUINEE_EQUATORIALE"), // 048
    COTE_D_IVOIRE("COTE_D_IVOIRE"), // 049
    GHANA("GHANA"), // 050
    TOGO("TOGO"), // 051
    BENIN("BENIN"), // 052
    NIGER("NIGER"), // 053
    NIGERIA("NIGERIA"), // 054
    CHAD("CHAD"), // 055
    CAMEROUN("CAMEROUN"), // 056
    CENTRAFRIQUE("CENTRAFRIQUE"), // 057
    CONGO("CONGO"), // 058
    ANGOLA("ANGOLA"), // 059
    GABON("GABON"), // 060
    REPUBLIQUE_DEMOGRAPHIQUE_DU_CONGO("REPUBLIQUE_DEMOGRAPHIQUE_DU_CONGO"), // 061
    REPUBLIQUE_CENTRAFRICAINE("REPUBLIQUE_CENTRAFRICAINE"), // 062
    SUDAN("SUDAN"), // 063
    ERYTHREE("ERYTHREE"), // 064
    ETIOPIE("ETIOPIE"), // 065
    SOMALIE("SOMALIE"), // 066
    DJIBOUTI("DJIBOUTI"), // 067
    KENYA("KENYA"), // 068
    TANZANIE("TANZANIE"), // 069
    OUGANDA("OUGANDA"), // 070
    RWANDA("RWANDA"), // 071
    BURUNDI("BURUNDI"), // 072
    ZAMBIE("ZAMBIE"), // 073
    ZIMBABWE("ZIMBABWE"), // 074
    MOZAMBIQUE("MOZAMBIQUE"), // 075
    CHINE("CHINE"), // 076
    JAPON("JAPON"), // 077
    COREE_DU_SUD("COREE_DU_SUD"), // 078
    INDE("INDE"), // 079
    BANGLADESH("BANGLADESH"), // 080
    NEPAL("NEPAL"), // 081
    BIRMANIE("BIRMANIE"), // 082
    LAOS("LAOS"), // 083
    VIETNAM("VIETNAM"), // 084
    THAILANDE("THAILANDE"), // 085
    MALAISIE("MALAISIE"), // 086
    SINGAPOUR("SINGAPOUR"), // 087
    PHILIPPINES("PHILIPPINES"), // 088
    INDONESIE("INDONESIE"), // 089
    AUSTRALIE("AUSTRALIE"), // 090
    NOUVELLE_ZELANDE("NOUVELLE_ZELANDE"), // 091
    CANADA("CANADA"), // 092
    ETATS_UNIS("ETATS_UNIS"), // 093
    MEXIQUE("MEXIQUE"), // 094
    CUBA("CUBA"), // 095
    DOMINICAINE("DOMINICAINE"), // 096
    HAITI("HAITI"), // 097
    GUATEMALA("GUATEMALA"), // 098
    SALVADOR("SALVADOR"), // 099
    HONDURAS("HONDURAS"), // 100
    NICARAGUA("NICARAGUA"), // 101
    COSTA_RICA("COSTA_RICA"), // 102
    PANAMA("PANAMA"); // 103

    private String nom;

    Pays(String nom) {
        this.nom = nom;
    }

    public String toString() {
        return nom;
    }

    public static Pays getValue(String s) {
        s = s.toUpperCase();
        for (Pays p : Pays.values()) {
            if (p.nom.equals(s))
                return p;
        }
        return null;
    }
}