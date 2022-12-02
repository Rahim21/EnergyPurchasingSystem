package ClassServeurAMI;

import ClassEnergie.Energie;
import ClassChiffrementAES.ChiffrementAES;
import Config.Messenger;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Classe correspondant à un serveur TCP multithreadé.
 */
public class ServeurMulti_AMI_TCP implements Runnable{

    public final int portAMI;
    // public final int portAMIchiffre;
    public final Messenger gestionMessage;

    public ServeurMulti_AMI_TCP(int portAMI) {
        this.portAMI = portAMI;
        // this.portAMIchiffre = portAMIchiffre;
        this.gestionMessage = new Messenger("AMI");
    }

    public void run() {

        // Création de la socket serveur
        ServerSocket socketServeur = null;
        try {
            socketServeur = new ServerSocket(portAMI);
        } catch (IOException e) {
            gestionMessage.afficheMessage("Création de la socket impossible : " + e);
            System.exit(0);
        }

        // Attente des connexions des clients
        try {
            Socket socketClient;
            while (true) {
                socketClient = socketServeur.accept();
                ThreadAMI t = new ThreadAMI(socketClient);
                t.start();
            }
        } catch (IOException e) {
            gestionMessage.afficheMessage("Erreur lors de l'attente d'une connexion : " + e);
            System.exit(0);
        }

        // Fermeture de la socket
        try {
            socketServeur.close();
        } catch (IOException e) {
            gestionMessage.afficheMessage("Erreur lors de la fermeture de la socket : " + e);
            System.exit(0);
        }
    }

}