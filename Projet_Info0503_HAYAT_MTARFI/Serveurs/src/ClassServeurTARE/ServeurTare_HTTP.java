package ClassServeurTARE;

import ClassEnergie.Energie;
import Config.Messenger;

import java.io.IOException;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpContext;
import java.net.InetSocketAddress;

public class ServeurTare_HTTP implements Runnable {

    public final int portTARE;
    public final int portMarche_UDP;
    private final Messenger gestionMessage;
    private final String nomTARE;

    public ServeurTare_HTTP(int portTARE, int portMarche_UDP, String nomTARE) {
        this.portTARE = portTARE;
        this.portMarche_UDP = portMarche_UDP;
        this.nomTARE = nomTARE;
        this.gestionMessage = new Messenger("TARE | " + nomTARE);
    }

    public void run() {
        Tare tare = new Tare(nomTARE, 0);
        HttpServer serveur = null;
        try {
            serveur = HttpServer.create(new InetSocketAddress(8080), 0);
        } catch (IOException e) {
            gestionMessage.afficheMessage("Erreur lors de la création du serveur " + e);
            System.exit(0);
        }

        // /tare1 /tare2 pour deux tare
        serveur.createContext("/tare", new TareHandler(tare, portTARE, portMarche_UDP));
        serveur.setExecutor(null);
        serveur.start();
        gestionMessage.afficheMessage("Démarrage du Trader.");
    }
}