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

import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Serveur UDP permettant de tester l'envoi d'objets dans un segment UDP.
 * Le client crée un message contenant énergie et l'envoie au serveur.
 * Le numéro de port est spécifié dans la classe ServeurMarcheGros.
 * 
 * @author HAYAT Rahim
 */
public class ServeurMarcheGros_UDP implements Runnable {
    public final int portTARE_UDP;
    public final int portPONE_UDP;
    public final int portAMI_TCP;
    private final Messenger gestionMessage;

    public ServeurMarcheGros_UDP(int portTARE_UDP, int portPONE_UDP, int portAMI_TCP) {
        this.portTARE_UDP = portTARE_UDP;
        this.portPONE_UDP = portPONE_UDP;
        this.portAMI_TCP = portAMI_TCP;
        this.gestionMessage = new Messenger("MarcheGros UDP");
    }

    public void run() {

        int compteur = 1; // ou 0 ? à tester

        // Création de la socket Pone
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(portPONE_UDP);
        } catch (SocketException e) {
            gestionMessage.afficheMessage("Erreur lors de la création du socket : " + e);
            System.exit(0);
        }

        // données energie.json
        String dossierCourant = System.getProperty("user.dir");
        String cheminFichier = dossierCourant + "/src/energie.json";
        File file = new File(cheminFichier);

        // Afficher la liste d'energie
        gestionMessage.afficheMessage("Liste des énergies :");
        if (file.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String ligne = br.readLine();
                JSONObject obj = new JSONObject(ligne);
                if (ligne.equals("")) {
                    gestionMessage.afficheMessage("Le fichier est vide");
                } else {
                    for (int i = 1; i <= obj.length(); i++) {
                        gestionMessage
                                .afficheMessage(Energie.fromJSON(obj.get(Integer.toString(i)).toString()).toString());
                    }
                    compteur = obj.length();
                }
                br.close();
            } catch (Exception e) {
                gestionMessage.afficheMessage("Erreur lors de la lecture du fichier : '"
                        + cheminFichier + "'");
            }
        } else {
            gestionMessage.afficheMessage("Le fichier n'existe pas.");
        }

        // Serveur constant avec un while
        boolean infini = true;
        while (infini) {

            // Lecture du message du client
            DatagramPacket msgRecu = null;
            try {
                byte[] tampon = new byte[1024];
                msgRecu = new DatagramPacket(tampon, tampon.length);
                socket.receive(msgRecu);
            } catch (IOException e) {
                gestionMessage.afficheMessage("Erreur lors de la réception du message : " + e);
                System.exit(0);
            }

            // Récupération de l'objet
            try {
                ByteArrayInputStream bais = new ByteArrayInputStream(msgRecu.getData());
                ObjectInputStream ois = new ObjectInputStream(bais);
                Energie obj_e = (Energie) ois.readObject();

                // Affichage de l'objet reçu
                gestionMessage.afficheMessage("Recu energie [" + (compteur) + "]: " + obj_e);

                // ServeurAMI_TCP
                // Création de la socket pour l'AMI
                Socket socket_ami = null;
                try {
                    socket_ami = new Socket("localhost", portAMI_TCP);
                } catch (UnknownHostException e) {
                    gestionMessage.afficheMessage("Erreur sur l'hôte : " + e);
                    System.exit(0);
                } catch (IOException e) {
                    gestionMessage.afficheMessage("Création de la socket AMI impossible : " + e);
                    System.exit(0);
                }

                // Association d'un flux d'entrée et de sortie AMI
                BufferedReader input = null;
                PrintWriter output = null;
                try {
                    input = new BufferedReader(new InputStreamReader(socket_ami.getInputStream()));
                    output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket_ami.getOutputStream())),
                            true);
                } catch (IOException e) {
                    gestionMessage.afficheMessage("Association des flux impossible : " + e);
                    System.exit(0);
                }

                // envoyer une réponse au client AMI format JSON
                // String reponse = "\"prix\":" + obj_e.getPrix();
                String reponse = "\"budget\":" + obj_e.getBudget();
                output.println(reponse);
                gestionMessage.afficheMessage("Réponse envoyée au client AMI : " + reponse);

                // reception de la réponse du client AMI
                String reponseClient = null;
                try {
                    reponseClient = input.readLine();
                } catch (IOException e) {
                    gestionMessage.afficheMessage("Erreur lors de la réception de la réponse : " + e);
                    System.exit(0);
                }

                // Affichage de la réponse du client AMI
                gestionMessage.afficheMessage("Reception réponse AMI : " + reponseClient); // ACCEPT ou REFUSE
                String msgPone = "";
                if (reponseClient.equals("ACCEPT")) {
                    msgPone = "L'énergie à été valider par l'AMI et à été enregistré au Marché.";
                    // Ajouter son format JSON dans le fichier energie.json
                    try {
                        if (!file.exists() || file.length() == 0) {
                            file.createNewFile();
                            FileWriter fileWriter = new FileWriter(file);
                            fileWriter.write("{}");
                            fileWriter.close();
                        }
                        FileReader fileReader = new FileReader(file);
                        BufferedReader bufferedReader = new BufferedReader(fileReader);
                        String ligne = bufferedReader.readLine();
                        bufferedReader.close();
                        fileReader.close();
                        JSONObject obj_energie = new JSONObject(ligne);
                        obj_energie.put(String.valueOf(compteur++), obj_e.toJSON());
                        FileWriter fileWriter = new FileWriter(file);
                        fileWriter.write(obj_energie.toString());
                        fileWriter.close();
                    } catch (IOException e) {
                        gestionMessage.afficheMessage("Erreur: impossible d'écrire dans le fichier '"
                                + cheminFichier + "'");
                    }
                } else {
                    msgPone = "L'énergie n'est pas conforme, la demande à été rejeté par l'AMI.";
                }
                gestionMessage.afficheMessage(msgPone);
                // envoyer le message au Pone + faire quelque chose pour le Tare après
                byte[] tampon = msgPone.getBytes(); // le tampon contient le resultat à envoyer au Pone
                DatagramPacket msg = new DatagramPacket(tampon, tampon.length, msgRecu.getAddress(),
                        msgRecu.getPort()); // port récupérer via le message reçu
                socket.send(msg);
            } catch (ClassNotFoundException e) {
                gestionMessage.afficheMessage("Objet reçu non reconnu : " + e);
                System.exit(0);
            } catch (IOException e) {
                gestionMessage.afficheMessage("Erreur lors de la récupération de l'objet : " + e);
                System.exit(0);
            }

        }
        // Fermeture de la socket
        socket.close();
        gestionMessage.afficheMessage("Fermeture de la socket serveur.");
    }
}
