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

/**
 * Classe correspondant au thread associe à chaque connexion d'un client.
 */
public class ThreadAMI extends Thread {

    private Socket socketClient;
    public final Messenger gestionMessage;
    private BufferedReader input;
    private PrintWriter output;

    public ThreadAMI(Socket socketClient) {
        this.socketClient = socketClient;
        this.gestionMessage = new Messenger("AMI");

        // Association d'un flux d'entrée et de sortie
        try {
            input = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
            output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socketClient.getOutputStream())), true);
        } catch (IOException e) {
            gestionMessage.afficheMessage("Association des flux impossible : " + e);
            System.exit(0);
        }
    }

    @Override
    public void run() {

        String message = "";

        // ----- Lecture du message formaté JSON -----
        try {
            message = input.readLine();
        } catch (IOException e) {
            gestionMessage.afficheMessage("Erreur lors de la lecture : " + e);
            System.exit(0);
        }

        // récupération des valeurs prix et quantité depuis le message
        Energie obj_e = Energie.fromJSON(message);

        // ----- Certification CRADO -----
        boolean certif = false;
        String resultatCertif = "AUCUN CERTIFICAT";
        // chemin du dossier courant
        String ami_folder = System.getProperty("user.dir") + "/Serveurs/src/ClassServeurAMI/";
        String crado_folder = ami_folder+"CRADO/CRADO-"
                + obj_e.getCodeDeSuivie()
                + ".bin";
        String privee = ami_folder+"privee.bin";
        String publique = ami_folder+"publique.bin";

        if (obj_e.getCertificat() != null) { // ----- VERIFICATION CERTIFICAT D'UNE ENERGIE DU CLIENT -----
            // ----- VERIFICATION CERTIFICAT -----
            resultatCertif = RSA.VerifierSignatureRSA_JSON(obj_e.toJSON().toString(), crado_folder, publique);
            // ----- ENVOI DE L'ENERGIE -----
            gestionMessage.afficheMessage(resultatCertif+" -> ClientID: "+obj_e.getIdProprietaire()+", Energie: "+obj_e.getCodeDeSuivie());
            output.println(resultatCertif);
            
        } else { // ----- ACHAT D'ENERGIE + AJOUT CERTIFICAT -----

            double prix = obj_e.getPrix();
            gestionMessage.afficheMessage("[Reçu] prix: " + prix);

            // Envoi de du Résultat plafonné à 180 euros le mégawattheure (MWh)
            // resultat "ACCEPT" ou "REFUSE"
            String resultat = (prix > 180) ? "REFUSE"
                    : "ACCEPT";
            gestionMessage.afficheMessage("Envoi requête au Marche : " + resultat);
            output.println(resultat);

            if (resultat.equals("ACCEPT")) {
                // ----- GENERATION CERTIFICAT -----
                RSA.GenererClesRSA(privee, publique);
                String obj_json = obj_e.toJSON().toString();
                RSA.SignerFichierRSA_JSON(privee, obj_json, crado_folder);
                resultatCertif = RSA.VerifierSignatureRSA_JSON(obj_json, crado_folder, publique);

                if (resultatCertif.equals("CERTIFICATION VALIDE")) {
                    certif = true;
                }
                gestionMessage.afficheMessage(
                        "Certification " + (certif ? "REUSSI, Signature ajoutée" : "ECHOUEE") + " pour : " + obj_e.getCodeDeSuivie());
            }

        }


        

        // ----- Lecture du message crypté -----
        // byte[] data = null;
        // try {
        // message = input.readLine();
        // data = java.util.Base64.getDecoder().decode(message);
        // data = ChiffrementAES.dechiffrer("1234567890123456", data);
        // message = new String(data);
        // gestionMessage.afficheMessage("Message reçu : " + message);
        // Energie obj_e = Energie.fromJSON(message);
        // gestionMessage.afficheMessage("Energie reçue : " + obj_e);
        // } catch (IOException e) {
        // gestionMessage.afficheMessage("Erreur lors de la lecture : " + e);
        // System.exit(0);
        // }

        // Fermeture des flux et des sockets
        try {
            input.close();
            output.close();
            socketClient.close();
        } catch (IOException e) {
            gestionMessage.afficheMessage("Erreur lors de la fermeture des flux et des sockets : " + e);
            System.exit(0);
        }
    }

}