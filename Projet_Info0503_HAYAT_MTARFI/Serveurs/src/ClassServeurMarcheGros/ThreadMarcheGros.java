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
 * Classe correspondant au thread associe à chaque connexion d'un client.
 */
public class ThreadMarcheGros extends Thread {

    private final DatagramSocket socket_invite;
    private int socketClient;
    private int portAMI_TCP;
    private Messenger gestionMessage;
    private DatagramPacket invitation;

    public ThreadMarcheGros(DatagramSocket socket_invite, DatagramPacket invitation, int portAMI_TCP, Messenger gestionMessage) {
        this.socket_invite = socket_invite;
        this.invitation = invitation;
        this.portAMI_TCP = portAMI_TCP;
        this.gestionMessage = new Messenger("MarcheGros UDP");
    }

    @Override
    public void run() {
        // Lire l'invitation : "Client:Port"
        String[] invitation_str = new String(invitation.getData(), 0, invitation.getLength()).split(":");
        String client_invitation = invitation_str[0];
        socketClient = Integer.parseInt(invitation_str[1]);

        gestionMessage.afficheMessage(
                "Client " + client_invitation + " demande une connexion sur le port " + socketClient);
                
        int compteur = 1; // ou 0 ? à tester nbEnergie reçu : va problement poser problème à cause du multithread

        // données energie.json
        String dossierCourant = System.getProperty("user.dir");
        String cheminFichier = dossierCourant + "/Serveurs/src/classServeurMarcheGros/energie.json";
        File file = new File(cheminFichier);

        if(client_invitation.equals("PONE")){
            try {
                DatagramPacket msgRecu = null;
                try {
                    byte[] tampon = new byte[1024];
                    msgRecu = new DatagramPacket(tampon, tampon.length, invitation.getAddress(), socketClient);
                    socket_invite.receive(msgRecu);
                } catch (IOException e) {
                    gestionMessage.afficheMessage("Erreur lors de la réception du message : " + e);
                    System.exit(0);
                }
                ByteArrayInputStream bais = new ByteArrayInputStream(msgRecu.getData());
                ObjectInputStream ois = new ObjectInputStream(bais);
                Energie obj_e = (Energie) ois.readObject();

                // Affichage de l'objet reçu
                gestionMessage.afficheMessage("Recu energie [" + (compteur) + "]: " + obj_e);
                socket_invite.close();

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
                String reponse = "\"prix\":" + obj_e.getPrix(); // Prix
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
                        gestionMessage
                                .afficheMessage("Erreur: impossible d'écrire dans le fichier '" + cheminFichier + "'");
                    }
                } else {
                    msgPone = "L'énergie n'est pas conforme, la demande à été rejeté par l'AMI.";
                }
                gestionMessage.afficheMessage(msgPone);

                // pour quoi faire ?
                // envoyer le message au Pone + faire quelque chose pour le Tare après
                // byte[] tampon = msgPone.getBytes(); // le tampon contient le resultat à envoyer au Pone
                // DatagramPacket msg = new DatagramPacket(tampon, tampon.length, msgRecu.getAddress(),
                //         msgRecu.getPort()); // port récupérer via le message reçu
                // socket.send(msg);
            } catch (ClassNotFoundException e) {
                gestionMessage.afficheMessage("Objet reçu non reconnu : " + e);
                System.exit(0);
            } catch (IOException e) {
                gestionMessage.afficheMessage("Erreur lors de la récupération de l'objet : " + e);
                System.exit(0);
            }
        } else if(client_invitation.equals("TARE")){
            // TODO
        }
    }

        // Fermeture de la socket
        // socket.close();
        // gestionMessage.afficheMessage("Fermeture de la socket serveur.");

}