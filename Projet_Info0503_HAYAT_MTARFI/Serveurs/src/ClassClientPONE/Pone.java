package ClassClientPONE;

import org.json.JSONObject;

public class Pone {
    private int idPone;
    private String nom;

    public Pone(int idPone, String nom) {
        this.idPone = idPone;
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public int getIdPone() {
        return idPone;
    }

    @Override
    public String toString() {
        return "Pone[" + getIdPone() + "] " + getNom();
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("idPone", getIdPone());
        json.put("nom", this.nom);
        return json;
    }

    public static Pone fromJSON(String json) {
        JSONObject object = new JSONObject(json);
        int idPone = object.getInt("idPone");
        String nom = object.getString("nom");
        return new Pone(idPone, nom);
    }
}