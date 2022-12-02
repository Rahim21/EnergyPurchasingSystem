package ClassServeurTARE;

import Config.Messenger;

import java.io.IOException;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpContext;
import java.net.InetSocketAddress;

public class ServeurTare_HTTP implements Runnable {

    public final int portEnvoiMarche;
    public final int portEcouteMarche;
    private final Messenger gestionMessage;
    private final String nomTARE;

    public ServeurTare_HTTP(int portEnvoiMarche, int portEcouteMarche, String nomTARE) {
        this.portEnvoiMarche = portEnvoiMarche;
        this.portEcouteMarche = portEcouteMarche;
        this.nomTARE = nomTARE;
        this.gestionMessage = new Messenger("TARE | " + nomTARE);
    }

    public void run() {
        Tare tare = new Tare(nomTARE, 0);
        HttpServer serveur = null;
        try {
            serveur = HttpServer.create(new InetSocketAddress(8080), 0);
        } catch (IOException e) {
            System.err.println("Erreur lors de la création du serveur " + e);
            System.exit(0);
        }

        // /tare1 /tare2 pour deux tare
        serveur.createContext("/tare", new TareHandler(tare, portEnvoiMarche, portEcouteMarche));
        serveur.setExecutor(null);
        serveur.start();
        gestionMessage.afficheMessage("Démarrage du Trader.");
        System.out.println("Serveur du Marché de gros démarré. Pressez CRTL+C pour arrêter.");
    }
}