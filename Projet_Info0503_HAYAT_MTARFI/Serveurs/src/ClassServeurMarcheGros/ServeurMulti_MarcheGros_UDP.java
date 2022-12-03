package ClassServeurMarcheGros;

import ClassEnergie.Energie;
import Config.Messenger;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.SocketException;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import org.json.JSONObject;

import java.net.UnknownHostException;

/**
 * Classe correspondant à un serveur UDP multithreadé.
 * Le client envoie ensuite la chaine 'Au revoir' et lit une réponse.
 * Le numéro de port du serveur est spécifié dans la classe ServeurUDP.
 */
public class ServeurMulti_MarcheGros_UDP implements Runnable {

    public final int portMarche_UDP;
    public final int portAMI_TCP;
    private final Messenger gestionMessage;
    public static int IdEnergie = 1;

    public ServeurMulti_MarcheGros_UDP(int portMarche_UDP, int portAMI_TCP) {
        this.portMarche_UDP = portMarche_UDP;
        this.portAMI_TCP = portAMI_TCP;
        this.gestionMessage = new Messenger("MarcheGros UDP");
    }

    public void run() {

        // Création de la socket MARCHE UDP
        DatagramSocket socketServeur = null;
        try {
            socketServeur = new DatagramSocket(portMarche_UDP);
        } catch (SocketException e) {
            gestionMessage.afficheMessage("Erreur lors de la création du socket : " + e);
            System.exit(0);
        }

        // Serveur constant avec un while
        boolean infini = true;
        while (infini) {
            // Connexions des clients
            DatagramPacket invitation = null;
            try {
                byte[] tampon = new byte[1024];
                invitation = new DatagramPacket(tampon, tampon.length);
                socketServeur.receive(invitation);
            } catch (IOException e) {
                gestionMessage.afficheMessage("Erreur lors de la réception du message : " + e);
                System.exit(0);
            }

            // Lire l'invitation : "Client:Port"
            String[] invitation_str = new String(invitation.getData(), 0, invitation.getLength()).split(":");
            String client_invitation = invitation_str[0];
            int socketClient = Integer.parseInt(invitation_str[1]);

            // Création de la socket client
            DatagramSocket socket_invite = null;
            try {
                socket_invite = new DatagramSocket(socketClient);
            } catch (SocketException e) {
                gestionMessage.afficheMessage("Erreur lors de la création du socket : " + e);
                System.exit(0);
            }

            ThreadMarcheGros t = new ThreadMarcheGros(socket_invite, invitation, portAMI_TCP, gestionMessage);
            t.start();
        }

        // Fermeture de la socket
        // try {
        // socketServeur.close();
        // } catch (IOException e) {
        // System.err.println("Erreur lors de la fermeture de la socket : " + e);
        // System.exit(0);
        // }
    }

}