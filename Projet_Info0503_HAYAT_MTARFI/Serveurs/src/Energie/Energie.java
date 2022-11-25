import java.io.Serializable;
import org.json.JSONObject;

/**
 * Classe correspondant a une energie, utilisée pour l'envoi d'energie
 * 
 * @author HAYAT Rahim & MTARFI Souhail
 */
public class Energie implements Serializable {

    private String codeDeSuivie;
    private int idActeur; // Client: 1 && Producteur: 2
    private int idProprietaire; // id du proprietaire de l'energie depuis la BDD/JSON
    private String type;
    private String origine;
    private int quantite;
    private double prix;

    /**
     * Constructeur par défaut
     */
    public Energie() {
        this(0, "Type inconnu", "Origine inconnue", 0, 0.0);
        this.codeDeSuivie = CodeDeSuivi.encoder(this);
    }

    /**
     * Constructeur par initialisation
     * 
     * @param prenom le prénom de la energie
     * @param nom    le nom de la energie
     */
    public Energie(int idProprietaire, String type, String origine, int quantite, double prix) {
        this.idProprietaire = idProprietaire;
        this.type = type;
        this.origine = origine;
        this.quantite = quantite;
        this.prix = prix;
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
     * Modifie le prix
     * 
     * @param prix le nouveau prix
     */
    public void setPrix(double prix) {
        this.prix = prix;
    }

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
        // return "Energie du Propriétaire: " + idProprietaire + " (" + quantite + "
        // kWh, " + prix + " euros, " + origine
        // + ", " + type
        // + ")";

        return "Energie du Propriétaire: " + idProprietaire + " (" + type + ", " + origine + ", " + quantite + " kWh, "
                + prix + " euros)";
    }

    public JSONObject toJSON() {
        JSONObject mon_obj = new JSONObject();
        mon_obj.put("idProprietaire", idProprietaire);
        mon_obj.put("quantite", quantite);
        mon_obj.put("prix", prix);
        mon_obj.put("origine", origine);
        mon_obj.put("type", type);
        return mon_obj;
    }

    public static Energie fromJSON(String json) {
        JSONObject object = new JSONObject(json);
        int idProprietaire = object.getInt("idProprietaire");
        int quantite = object.getInt("quantite");
        double prix = object.getDouble("prix");
        String origine = object.getString("origine");
        String type = object.getString("type");
        return new Energie(idProprietaire, type, origine, quantite, prix);
    }
}