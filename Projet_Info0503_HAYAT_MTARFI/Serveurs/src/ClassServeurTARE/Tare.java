package ClassServeurTARE;

import org.json.JSONObject;

public class Tare {
    private String nom;
    private int estimationTemps;

    public Tare(String nom, int estimationTemps) {
        this.nom = nom;
        this.estimationTemps = estimationTemps;
    }

    public String getNom() {
        return nom;
    }

    public int getEstimationTemps() {
        return estimationTemps;
    }

    public void setEstimationTemps(int estimationTemps) {
        this.estimationTemps = estimationTemps;
    }

    @Override
    public String toString() {
        return "Nom :" + nom + ", Estimation du temps : " + estimationTemps;
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("nom", this.nom);
        json.put("estimationTemps", this.estimationTemps);
        return json;
    }

    public static Tare fromJSON(String json) {
        JSONObject object = new JSONObject(json);
        String nom = object.getString("nom");
        int estimationTemps = object.getInt("estimationTemps");
        return new Tare(nom, estimationTemps);
    }
}