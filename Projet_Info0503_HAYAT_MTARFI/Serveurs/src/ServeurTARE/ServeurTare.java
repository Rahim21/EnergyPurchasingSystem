package ServeurTARE;

import java.io.IOException;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpContext;
import java.net.InetSocketAddress;

import Config.Messenger;

public class ServeurTare implements Runnable {

    public final int portEnvoiMarche;
    public final int portEcouteMarche;
    private final Messenger gestionMessage;

    public ServeurTare(int portEnvoiMarche, int portEcouteMarche) {
        this.portEnvoiMarche = portEnvoiMarche;
        this.portEcouteMarche = portEcouteMarche;
        this.gestionMessage = new Messenger("TARE ");
    }

    public void run() {
        Tare tare = new Tare("Trader", 0);
        HttpServer serveur = null;
        try {
            serveur = HttpServer.create(new InetSocketAddress(8080), 0);
        } catch (IOException e) {
            System.err.println("Erreur lors de la création du serveur " + e);
            System.exit(0);
        }

        serveur.createContext("/tare", new TareHandler(tare, portEnvoiMarche, portEcouteMarche));
        serveur.setExecutor(null);
        serveur.start();
        gestionMessage.afficheMessage("Démarrage du Trader " + tare.getNom());
        System.out.println("[ Tare : " + tare.getNom() + "] : Démarrage du Trader");
        System.out.println("Serveur du Marché de gros démarré. Pressez CRTL+C pour arrêter.");
    }
}