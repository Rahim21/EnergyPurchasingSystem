package ServeurTARE;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.Headers;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.URLDecoder;
import java.net.MalformedURLException;

import ClassEnergie.Energie;
import Config.Messenger;

import org.json.JSONArray;
import org.json.JSONObject;

class TareHandler implements HttpHandler {

    private Tare tare;
    public final int portEcouteMarche;
    public final int portEnvoiMarche;
    private final Messenger gestionMessage;

    public TareHandler(Tare tare, int portEnvoiMarche, int portEcouteMarche) {
        this.tare = tare;
        this.portEcouteMarche = portEcouteMarche;
        this.portEnvoiMarche = portEnvoiMarche;
        this.gestionMessage = new Messenger("TARE ");
    }

    public void handle(HttpExchange t) {
        String reponse = "";

        // Récupération des données
        URI requestedUri = t.getRequestURI();
        String query = requestedUri.getRawQuery();

        // Utilisation d'un flux pour lire les données du message Http
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(t.getRequestBody(), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            System.err.println("Erreur lors de la récupération du flux " + e);
            System.exit(0);
        }

        // Récupération des données en POST
        try {
            query = br.readLine();
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture d'une ligne " + e);
            System.exit(0);
        }

        // Affichage des données
        if (query == null)
            reponse += "Aucune";
        else {
            try {
                query = URLDecoder.decode(query, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                query = "";
            }
            reponse += query;
        }

        // Envoi de l'en-tête Http
        try {
            Headers h = t.getResponseHeaders();
            // Content-type: application/x-www-form-urlencoded
            h.set("Content-Type", "text/html; charset=utf-8");
            t.sendResponseHeaders(200, reponse.getBytes().length);
        } catch (IOException e) {
            System.err.println("Erreur lors de l'envoi de l'en-tête : " + e);
            System.exit(0);
        }

        // Envoi du corps (données HTML)
        try {
            OutputStream os = t.getResponseBody();
            os.write(reponse.getBytes());
            os.close();
        } catch (IOException e) {
            System.err.println("Erreur lors de l'envoi du corps : " + e);
        }

        gestionMessage.afficheMessage("Lu  " + reponse);

        // Création de l'Energie
        JSONObject obj = new JSONObject(reponse);
        JSONObject commande = obj.getJSONObject("commande");
        Energie energie = Energie.fromJSON(commande.toString());
        System.out.println("Energie : " + energie);
    }
}
