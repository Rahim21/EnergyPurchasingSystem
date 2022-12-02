package ClassServeurAMI;

import ClassEnergie.Energie;
import ClassChiffrementAES.ChiffrementAES;
import Config.Messenger;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.PrintWriter;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import java.security.NoSuchAlgorithmException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.security.InvalidKeyException;

public class ServeurAMI_TCP implements Runnable {

    public final int portAMI;
    // public final int portAMIchiffre;
    public final Messenger gestionMessage;

    public ServeurAMI_TCP(int portAMI) {
        this.portAMI = portAMI;
        // this.portAMIchiffre = portAMIchiffre;
        this.gestionMessage = new Messenger("AMI");
    }

    public void run() {
        // Serveur constant avec un while
        boolean infini = true;
        while (infini) {

            // Création de la socket serveur
            ServerSocket socketServeur = null;
            try {
                socketServeur = new ServerSocket(portAMI);
            } catch (IOException e) {
                gestionMessage.afficheMessage("Création de la socket impossible : " + e);
                System.exit(0);
            }

            // Attente d'une connexion d'un client
            Socket socketClient = null;
            try {
                socketClient = socketServeur.accept();
            } catch (IOException e) {
                gestionMessage.afficheMessage("Erreur lors de l'attente d'une connexion : " + e);
                System.exit(0);
            }

            // Association d'un flux d'entrée et de sortie
            BufferedReader input = null;
            PrintWriter output = null;
            try {
                input = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
                output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socketClient.getOutputStream())),
                        true);
            } catch (IOException e) {
                gestionMessage.afficheMessage("Association des flux impossible : " + e);
                System.exit(0);
            }

            String message = "";

            // ----- Lecture du message formaté JSON {"prix":?} -----
            try {
                message = input.readLine();
            } catch (IOException e) {
                gestionMessage.afficheMessage("Erreur lors de la lecture : " + e);
                System.exit(0);
            }

            // récupération des valeurs prix et quantité depuis le message
            String prix = message.split(":")[1];
            gestionMessage.afficheMessage("[Reçu] prix: " + prix);

            // Envoi de du Résultat plafonné à 180 euros le mégawattheure (MWh)
            // resultat "ACCEPT" ou "REFUSE"
            String resultat = (Double.parseDouble(prix) > 180) ? "REFUSE"
                    : "ACCEPT";
            gestionMessage.afficheMessage("Envoi requête au Marche : " + resultat);
            output.println(resultat);

            // ----- Lecture du message crypté -----
            // byte[] data = null;
            // try {
            //     message = input.readLine();
            //     data = java.util.Base64.getDecoder().decode(message);
            //     data = ChiffrementAES.dechiffrer("1234567890123456", data);
            //     message = new String(data);
            //     gestionMessage.afficheMessage("Message reçu : " + message);
            //     Energie obj_e = Energie.fromJSON(message);
            //     gestionMessage.afficheMessage("Energie reçue : " + obj_e);
            // } catch (IOException e) {
            //     gestionMessage.afficheMessage("Erreur lors de la lecture : " + e);
            //     System.exit(0);
            // }

            // Fermeture des flux et des sockets
            try {
                input.close();
                output.close();
                socketClient.close();
                socketServeur.close();
            } catch (IOException e) {
                gestionMessage.afficheMessage("Erreur lors de la fermeture des flux et des sockets : " +
                        e);
                System.exit(0);
            }
        }
    }
}