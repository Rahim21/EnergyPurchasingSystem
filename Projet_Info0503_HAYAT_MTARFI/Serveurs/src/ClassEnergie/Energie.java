package ClassEnergie;

import java.io.Serializable;
import org.json.JSONObject;

/**
 * Classe correspondant a une energie, utilisée pour l'envoi d'energie
 * 
 * @author HAYAT Rahim & MTARFI Souhail
 */
public class Energie implements Serializable {

    private String codeDeSuivie;
    private int idProprietaire;
    private String type;
    private String origine;
    private int quantite;
    private double prix;
    private double budget;
    private boolean client;

    /**
     * Constructeur par défaut
     */
    public Energie() {
        this(0, "Type inconnu", "Origine inconnue", 0, 0.0, false);
        this.codeDeSuivie = CodeDeSuivi.encoder(this);
    }

    /**
     * Constructeur par initialisation
     * 
     * @param idProprietaire l'id du propriétaire de l'énergie
     * @param type le type de l'énergie
     * @param origine l'origine de l'énergie
     * @param quantite la quantité de l'énergie
     * @param prix le prix de l'énergie
     * @param budget le budget de client
     * @param client client/pone
     */
    public Energie(int idProprietaire, String type, String origine, int quantite, double monnaie, boolean client) {
        this.idProprietaire = idProprietaire;
        this.type = type;
        this.origine = origine;
        this.quantite = quantite;
        this.client = client;
        this.budget = monnaie;
        this.prix = monnaie;
        this.codeDeSuivie = CodeDeSuivi.encoder(this);
    }

    /**
     * Retourne l'id du proprietaire de l'energie
     * 
     * @return l'id du proprietaire
     */
    public int getIdProprietaire() {
        return idProprietaire;
    }

    // getter de codeDeSuivie
    public String getCodeDeSuivie() {
        return codeDeSuivie;
    }

    /**
     * Retourne la quantité
     * 
     * @return la quantité
     */
    public int getQuantite() {
        return quantite;
    }

    /**
     * Modifie la quantité
     * 
     * @param quantite la nouvelle quantité
     */
    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    /**
     * Retourne le prix
     * 
     * @return le prix
     */
    public double getPrix() {
        return prix;
    }

    /**
     * Retourne le prix
     * 
     * @return le prix
     */
    public double getBudget() {
        return budget;
    }

    /**
     * Modifie le prix
     * 
     * @param prix le nouveau prix
     */
    // public void setPrix(double prix) {
    // this.prix = prix;
    // }

    /**
     * Retourne l'origine
     * 
     * @return l'origine
     */
    public String getOrigine() {
        return origine;
    }

    /**
     * Modifie l'origine
     * 
     * @param origine la nouvelle origine
     */
    public void setOrigine(String origine) {
        this.origine = origine;
    }

    /**
     * Retourne le type
     * 
     * @return le type
     */
    public String getType() {
        return type;
    }

    /**
     * Modifie le type
     * 
     * @param type le nouveau type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Retourne une chaîne de caractères correspondant à l'objet
     * 
     * @return une chaîne de caractères correspondant à l'objet
     */
    public String toString() {
        return "Energie du Propriétaire: " + idProprietaire + " (" + type + ", " + origine + ", " + quantite + " MWh, "
                + prix + " euros)";
    }

    public JSONObject toJSON() {
        JSONObject mon_obj = new JSONObject();
        mon_obj.put("idProprietaire", idProprietaire);
        mon_obj.put("type", type);
        mon_obj.put("origine", origine);
        mon_obj.put("quantite", quantite);
        if(client){
            mon_obj.put("budget", budget);
        } else {
            mon_obj.put("prix", prix);
        }
        return mon_obj;
    }

    public static Energie fromJSON(String json) {
        JSONObject object = new JSONObject(json);
        int idProprietaire = object.getInt("idProprietaire");
        String type = object.getString("type");
        String origine = object.getString("origine");
        int quantite = object.getInt("quantite");
        double monnaie;
        boolean client = false;
        if(object.has("prix")){
            double prix = object.getDouble("prix");
            return new Energie(idProprietaire, type, origine, quantite, prix, false);
        }
        double budget = object.getDouble("budget");
        return new Energie(idProprietaire, type, origine, quantite, budget, true);
    }
}