package ClassServeurMarcheGros;

import ClassEnergie.CodeDeSuivi;
import ClassEnergie.Energie;
import Config.Messenger;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.DatagramPacket;
import java.net.SocketException;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;

import org.json.JSONArray;
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
    // private int IdEnergie;

    public ThreadMarcheGros(DatagramSocket socket_invite, DatagramPacket invitation, int portAMI_TCP,
            Messenger gestionMessage) {
        this.socket_invite = socket_invite;
        this.invitation = invitation;
        this.portAMI_TCP = portAMI_TCP;
        this.gestionMessage = new Messenger("MarcheGros UDP");
        // this.IdEnergie = IdEnergie;
    }

    public String verificationAMI(Energie obj_e) {
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
        return reponseClient;
    }

    @Override
    public void run() {
        // Lire l'invitation : "Client:Port"
        String[] invitation_str = new String(invitation.getData(), 0, invitation.getLength()).split(":");
        String client_invitation = invitation_str[0];
        socketClient = Integer.parseInt(invitation_str[1]);

        gestionMessage.afficheMessage(
                "Client " + client_invitation + " demande une connexion sur le port " + socketClient);

        // ou 0 ? à tester nbEnergie reçu : va problement poser
        // problème à cause du multithread

        // données energie.json
        String dossierCourant = System.getProperty("user.dir");
        String cheminFichier = dossierCourant + "/Serveurs/src/classServeurMarcheGros/energie.json";
        File file = new File(cheminFichier);

        if (client_invitation.equals("PONE")) {
            try {
                // reception energie
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
                gestionMessage.afficheMessage("Recu energie : " + obj_e);
                gestionMessage.afficheMessage("Code de suivi : " + obj_e.getCodeDeSuivie());
                socket_invite.close();
                String reponseClient = verificationAMI(obj_e);
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
                        int nbEnergie = obj_energie.length();
                        obj_energie.put(String.valueOf(nbEnergie + 1), obj_e.toJSON());
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
                // byte[] tampon = msgPone.getBytes(); // le tampon contient le resultat à
                // envoyer au Pone
                // DatagramPacket msg = new DatagramPacket(tampon, tampon.length,
                // msgRecu.getAddress(),
                // msgRecu.getPort()); // port récupérer via le message reçu
                // socket.send(msg);
            } catch (ClassNotFoundException e) {
                gestionMessage.afficheMessage("Objet reçu non reconnu : " + e);
                System.exit(0);
            } catch (IOException e) {
                gestionMessage.afficheMessage("Erreur lors de la récupération de l'objet : " + e);
                System.exit(0);
            }
        } else if (client_invitation.equals("TARE")) {
            // "TARE:port:COMMANDE"
            // "TARE:port:VERIF_CERTIFICAT"

            try {
                DatagramPacket msgRecu = null;
                try {
                    byte[] tampon = new byte[1024];
                    msgRecu = new DatagramPacket(tampon, tampon.length, invitation.getAddress(),
                            socketClient);
                    socket_invite.receive(msgRecu);
                } catch (IOException e) {
                    gestionMessage.afficheMessage("Erreur lors de la réception du message : " +
                            e);
                    System.exit(0);
                }
                // Récupération de l'objet
                ByteArrayInputStream bais = new ByteArrayInputStream(msgRecu.getData());
                ObjectInputStream ois = new ObjectInputStream(bais);
                Energie obj_e = (Energie) ois.readObject();
                gestionMessage.afficheMessage("Recu energie : " + CodeDeSuivi.encoder(obj_e));
                // Affichage de l'objet reçu
                gestionMessage.afficheMessage("Recu de la demande : " + obj_e.toString());
                socket_invite.close();
                // verifier si l'energie est dans le fichier json
                boolean achatValide = false;
                try {
                    if (!file.exists() || file.length() == 0) {
                        file.createNewFile();
                        FileWriter fileWriter = new FileWriter(file);
                        fileWriter.write("{}");
                        fileWriter.close();
                    }
                    FileReader fileReader = new FileReader(file);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    // FileWriter fileWriter = new FileWriter(file);
                    String ligne = bufferedReader.readLine();
                    bufferedReader.close();
                    fileReader.close();
                    JSONObject obj_energie = new JSONObject(ligne);
                    int nbEnergie = obj_energie.length();
                    for (int i = 1; i <= nbEnergie; i++)
                    {
                        JSONObject obj = obj_energie.getJSONObject(String.valueOf(i));
                        Energie energie = Energie.fromJSON(obj.toString());
                        /*
                         * on verifie si le type d'energie est le meme et l'origine est le meme et la
                         * quantite est inferieur ou egale et si le prix * quantite est inferieur ou
                         * egale au budget de client
                         *
                         * 1 - type,origine,quantite,prix de energie et obj_e sont les memes : normal
                         * 2- type = SANS_CONTRAINTE le reste est normal
                         * 3 - origine = SANS_CONTRAINTE le reste est normal
                         * 4 - type et origine = SANS_CONTRAINTE
                         * 5 - le marche enregistre une commande souhaité du client non disponible, pour que les PONE la produisent
                         */

                        if (energie.getQuantite() >= obj_e.getQuantite()
                                && energie.getPrix() * obj_e.getQuantite() <= obj_e.getBudget()) {

                            if (energie.getType().equals(obj_e.getType())
                                    && energie.getOrigine().equals(obj_e.getOrigine())) {
                                achatValide = true;
                                // on met a jour le fichier json
                                energie.setQuantite(energie.getQuantite() - obj_e.getQuantite());
                                obj_energie.put(String.valueOf(i), energie.toJSON());
                            } else if (obj_e.getType().equals("SANS_CONTRAINTE")
                                    && energie.getOrigine().equals(obj_e.getOrigine())) {
                                achatValide = true;
                                // on met a jour le fichier json
                                energie.setQuantite(energie.getQuantite() - obj_e.getQuantite());
                                obj_energie.put(String.valueOf(i), energie.toJSON());
                            } else if (energie.getType().equals(obj_e.getType())
                                    && obj_e.getOrigine().equals("SANS_CONTRAINTE")) {
                                achatValide = true;
                                // on met a jour le fichier json
                                energie.setQuantite(energie.getQuantite() - obj_e.getQuantite());
                                obj_energie.put(String.valueOf(i), energie.toJSON());
                            } else if (obj_e.getType().equals("SANS_CONTRAINTE")
                                    && obj_e.getOrigine().equals("SANS_CONTRAINTE")) {
                                achatValide = true;
                                // on met a jour le fichier json
                                energie.setQuantite(energie.getQuantite() - obj_e.getQuantite());
                                obj_energie.put(String.valueOf(i), energie.toJSON());
                            }
                        }else{
                            achatValide = false;
                        }
                    }
                    FileWriter fileWriter = new FileWriter(file);
                    fileWriter.write(obj_energie.toString());
                    fileWriter.close();

                    if (achatValide) {
                        // on envoie le message au Tare
                        try {
                            gestionMessage.afficheMessage("Envoi de l'energie achetée au Tare");
                            // Création de la socket pour le Tare
                            DatagramSocket socket = new DatagramSocket();
                            // Création du message à envoyer
                            JSONObject energie = obj_e.toJSON();
                            String msg = energie.toString();
                            byte[] tampon = msg.getBytes();
                            DatagramPacket msgEnvoi = new DatagramPacket(tampon, tampon.length,
                                    msgRecu.getAddress(), msgRecu.getPort());
                            // Envoi du message
                            socket.send(msgEnvoi);
                            socket.close();
                        } catch (IOException e) {
                            gestionMessage.afficheMessage("Erreur lors de l'envoi du message : " +
                                    e);
                            System.exit(0);
                        }
                    } else {
                        try {
                        // Création de la socket pour le Tare
                        DatagramSocket socket = new DatagramSocket();
                        // Création du message à envoyer
                        JSONObject energie = obj_e.toJSON();
                        String msg = "ACHAT EN ATTENTE";
                        byte[] tampon = msg.getBytes();
                        DatagramPacket msgEnvoi = new DatagramPacket(tampon, tampon.length,
                                msgRecu.getAddress(), msgRecu.getPort());
                        // Envoi du message
                        socket.send(msgEnvoi);
                        socket.close();
                        } catch (IOException e) {
                            gestionMessage.afficheMessage("Erreur lors de l'envoi du message : " +
                                    e);
                            System.exit(0);
                        }
                        //enregistrer la commande souhaité du client non disponible dans un fichhier json, pour que les PONE la produisent
                        String dossierCourant1 = System.getProperty("user.dir");
                        String cheminFichier1 = dossierCourant1 + "/Serveurs/src/classServeurMarcheGros/attente_energie.json";
                        gestionMessage.afficheMessage("Chemin du fichier : " + cheminFichier1);
                        File file1 = new File(cheminFichier1);
                        try {
                            if (!file1.exists() || file1.length() == 0) {
                                file1.createNewFile();
                                FileWriter fileWriter2 = new FileWriter(file1);
                                fileWriter2.write("{}");
                                fileWriter2.close();
                            }
                            FileReader fileReader1 = new FileReader(file1);
                            BufferedReader bufferedReader1 = new BufferedReader(fileReader1);
                            String ligne1 = bufferedReader1.readLine();
                            bufferedReader1.close();
                            fileReader1.close();
                            JSONObject obj_energie1 = new JSONObject(ligne1);
                            int nbEnergie1 = obj_energie1.length();
                            obj_energie1.put(String.valueOf(nbEnergie1 + 1), obj_e.toJSON());
                            FileWriter fileWriter1 = new FileWriter(file1);
                            fileWriter1.write(obj_energie1.toString());
                            fileWriter1.close();
                        } catch (IOException e) {
                            gestionMessage
                                    .afficheMessage("Erreur: impossible d'écrire dans le fichier '" + cheminFichier + "'");
                        }
                    }
                } catch (IOException e) {
                    gestionMessage
                            .afficheMessage("Erreur: impossible d'écrire dans le fichier '" + cheminFichier + "'");
                }
            } catch (ClassNotFoundException e) {
                gestionMessage.afficheMessage("Objet reçu non reconnu : " + e);
                System.exit(0);
            } catch (IOException e) {
                gestionMessage.afficheMessage("Erreur lors de la récupération de l'objet : " + e);
                System.exit(0);
            }
        }
    }

    // Fermeture de la socket
    // socket.close();
    // gestionMessage.afficheMessage("Fermeture de la socket serveur.");

}