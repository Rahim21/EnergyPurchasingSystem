package ClassEnergie;

import java.util.HashMap;
import ClassEnergie.Enum.Pays;
import ClassEnergie.Enum.TypeEnergie;

public class CodeDeSuivi /* implements Comparable<CodeDeSuivi> */ {

        public static String encoder(Energie e) {
                String code = "";
                // idClient et idProducteur ?
                code += String.format("%04d", e.getIdProprietaire());
                code += String.format("%02d", TypeEnergie.getValue(e.getType()).ordinal());

                code += String.format("%03d", Pays.getValue(e.getOrigine()).ordinal());

                code += (int) Math.log10(e.getQuantite()) + 1; // récupérer le nombre de chiffre de la quantité
                code += e.getQuantite();

                // code += (int) Math.log10(e.getPrix()) + 1; // chiffre du prix avant la
                // virgule
                // code += (int) (e.getPrix() * 100); // prix sans décimale

                code += (int) Math.log10(e.getBudget()) + 1; // chiffre du prix avant la virgule
                code += (int) (e.getBudget() * 100); // prix sans décimale

                return code;
        }

        public static Energie decoder(String code) {
                int idProprietaire = Integer.parseInt(code.substring(0, 4)); // 0000 à 9999
                String type = TypeEnergie.values()[Integer.parseInt(code.substring(4,
                                6))].toString();
                String origine = Pays.values()[Integer.parseInt(code.substring(6,
                                9))].toString();
                int tailleQuantite = Integer.parseInt(code.substring(9, 10));
                int quantite = Integer.parseInt(code.substring(10, 10 + tailleQuantite));
                int PrixAvantVirgule = Integer.parseInt(code.substring(10 + tailleQuantite,
                                11 + tailleQuantite));
                double budget = Double
                                .parseDouble(code.substring(11 + tailleQuantite, 11 + tailleQuantite +
                                                PrixAvantVirgule) + "."
                                                + code.substring(11 + tailleQuantite + PrixAvantVirgule));

                // double prix = Double
                // .parseDouble(code.substring(11 + tailleQuantite, 11 + tailleQuantite +
                // PrixAvantVirgule) + "."
                // + code.substring(11 + tailleQuantite + PrixAvantVirgule));
                return new Energie(idProprietaire, type, origine, quantite, budget, true);
        }
}
