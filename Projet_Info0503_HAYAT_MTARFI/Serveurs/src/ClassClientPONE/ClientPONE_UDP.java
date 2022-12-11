package ClassClientPONE;

import ClassEnergie.Energie;
import ClassEnergie.Enum.Pays;
import ClassEnergie.Enum.TypeEnergie;
import Config.Messenger;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.json.JSONObject;

import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

/**
 * Client UDP permettant de tester l'envoi d'objets dans un segment UDP.
 * Le client envoie un objet Energie au serveur.
 * Le numéro de port est spécifie dans la classe ServeurMarcheGros.
 * 
 * @author HAYAT Rahim
 */
public class ClientPONE_UDP implements Runnable {

    public final int portPONE_UDP;
    public final int portMarche_UDP;
    private final Messenger gestionMessage;
    private final String nomPONE;
    // private Pone pone;

    public ClientPONE_UDP(int portPONE_UDP, int portMarche_UDP, String nomPONE) {

        this.portPONE_UDP = portPONE_UDP;
        this.portMarche_UDP = portMarche_UDP;
        this.nomPONE = nomPONE;
        this.gestionMessage = new Messenger("PONE | " + nomPONE);
    }

    // 1.Créer une socket pour envoyer une invitation au MarcheGros
    // "PONE,portPONE_UDP"
    // 1b. Fermeture de la socket MarcheGros
    // 2.Créer une socket PONE pour communiquer avec le MarcheGros de manière
    // sécurisé
    // 3.Envoie et reception avec la socket PONE
    // 4.Fermer la socket PONE
    public void run() {
        DatagramSocket socket = null;
        boolean infini = true;
        while (infini) {

            // this.pone = new Pone(0, nomPONE);
            Pone pone = new Pone(0, nomPONE);
            // Création de la socket
            try {
                socket = new DatagramSocket();
            } catch (SocketException e) {
                gestionMessage.afficheMessage("Erreur lors de la création du socket : " + e);
                System.exit(0);
            }

            String invitation = "PONE:" + portPONE_UDP;
            // Envoi de l'invitation au MarcheGros
            try {
                // Transformation en tableau d'octets
                byte[] donnees = invitation.getBytes();
                InetAddress adresse = InetAddress.getByName("localhost");
                DatagramPacket msg = new DatagramPacket(donnees, donnees.length,
                        adresse, portMarche_UDP);
                socket.send(msg);
                gestionMessage.afficheMessage("Envoi de l'invitation de communication au Marche.");
            } catch (UnknownHostException e) {
                gestionMessage.afficheMessage("Erreur lors de la création de l'adresse : " + e);
                System.exit(0);
            } catch (IOException e) {
                gestionMessage.afficheMessage("Erreur lors de l'envoi du message : " + e);
                System.exit(0);
            }

            // SI un client souhaite une énergie particulière (depuis le fichier
            // attenteCommande.json)
            // si le fichier attente_energie.json existe et contient une énergie il faut
            // créer l'energie et l'envoyer au serveur
            String dossierCourant1 = System.getProperty("user.dir");
            String cheminFichier1 = dossierCourant1 + "/Serveurs/src/classServeurMarcheGros/attente_energie.json";
            // gestionMessage.afficheMessage("Chemin du fichier : " + cheminFichier1);
            File file1 = new File(cheminFichier1);
            // si le fichier existe
            if (file1.length() != 0) {
                // on lit le fichier
                try {
                    FileReader fr = new FileReader(file1);
                    BufferedReader br = new BufferedReader(fr);
                    String ligne = br.readLine();
                    gestionMessage.afficheMessage("Ligne lue : " + ligne);
                    // on crée l'energie
                    JSONObject obj_energie = new JSONObject(ligne);
                    int nbEnergie = obj_energie.length();
                    JSONObject obj = obj_energie.getJSONObject(String.valueOf(nbEnergie));

                    int idClient = obj.getInt("idProprietaire");
                    // obj.put("idProprietaire", this.pone.getIdPone());
                    obj.put("idProprietaire", (int) (Math.random() * 1000));
                    obj.put("idClient", idClient);
                    Energie energie = Energie.fromJSON(obj.toString());
                    gestionMessage.afficheMessage("Energie demandée : " + energie);
                    // on l'envoie au serveur
                    // Transformation en tableau d'octets
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    try {
                        ObjectOutputStream oos = new ObjectOutputStream(baos);
                        oos.writeObject(energie);
                    } catch (IOException e) {
                        gestionMessage.afficheMessage("Erreur lors de la sérialisation : " + e);
                        System.exit(0);
                    }
                    // on vide le fichier
                    FileWriter fw = new FileWriter(file1);
                    fw.write("");
                    fw.close();
                    // Création et envoi du segment UDP
                    try {
                        byte[] donnees = baos.toByteArray();
                        InetAddress adresse = InetAddress.getByName("localhost");
                        DatagramPacket msg = new DatagramPacket(donnees, donnees.length,
                                adresse, portPONE_UDP);
                        socket.send(msg);
                        gestionMessage.afficheMessage("Envoi de l'objet Energie au serveur.");
                    } catch (UnknownHostException e) {
                        gestionMessage.afficheMessage("Erreur lors de la création de l'adresse : " + e);
                        System.exit(0);
                    }
                } catch (IOException e) {
                    gestionMessage.afficheMessage("Erreur lors de la lecture du fichier : " + e);
                    System.exit(0);
                }
            } else {

                try {
                    // temps 2-20 secondes entre chaque envoi d'energie
                    // gestionMessage.afficheMessage("Temps d'attente : " + temps + "ms");
                    int temps = (int) (Math.random() * 20000 + 2000);
                    Thread.sleep(temps);
                } catch (InterruptedException e) {
                    gestionMessage.afficheMessage("Erreur lors du sleep : " + e);
                    System.exit(0);
                }

                // Créer une énergie avec les paramètres du fichier attenteCommande.json
                // Energie energie_demandee = Energie.fromJSON(fichier_attenteCommande.json);
                // et l'envoyer au serveur
                // SINON
                // Création de l'energie
                Energie obj_e = genererEnergie();
                gestionMessage.afficheMessage("Energie générée : " + obj_e);

                // Transformation en tableau d'octets
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(baos);
                    oos.writeObject(obj_e);
                } catch (IOException e) {
                    gestionMessage.afficheMessage("Erreur lors de la sérialisation : " + e);
                    System.exit(0);
                }

                // Création et envoi du segment UDP
                try {
                    byte[] donnees = baos.toByteArray();
                    InetAddress adresse = InetAddress.getByName("localhost");
                    DatagramPacket msg = new DatagramPacket(donnees, donnees.length,
                            adresse, portPONE_UDP);
                    socket.send(msg);
                    gestionMessage.afficheMessage("Envoi de l'objet Energie au serveur.");
                } catch (UnknownHostException e) {
                    gestionMessage.afficheMessage("Erreur lors de la création de l'adresse : " + e);
                    System.exit(0);
                } catch (IOException e) {
                    gestionMessage.afficheMessage("Erreur lors de l'envoi du message : " + e);
                    System.exit(0);
                }
            }

            try {
                // temps 30-120 secondes entre chaque envoi d'energie
                // gestionMessage.afficheMessage("Temps d'attente : " + temps + "ms");
                int temps = (int) (Math.random() * 90000 + 30000);
                Thread.sleep(temps);
            } catch (InterruptedException e) {
                gestionMessage.afficheMessage("Erreur lors du sleep : " + e);
                System.exit(0);
            }
        }
        // Fermeture de la socket
        socket.close();
        gestionMessage.afficheMessage("Fermeture de la socket.");
    }

    public Energie genererEnergie() {
        // String[] liste_producteur =
        // {"EDF","Total","Engie","Uniper","EDP","Enel","RWE","Vattenfall","Tepco","GDF","Iberdrola"};

        // int producteur = this.pone.getIdPone();
        int producteur = (int) (Math.random() * 1000); // aléatoire pour le test
        Pays pays = Pays.values()[(int) (Math.random() * Pays.values().length - 1)]; // depuis le fichier Enum/Pays.java
        TypeEnergie type = TypeEnergie.values()[(int) (Math.random() * TypeEnergie.values().length - 1)]; // depuis le
                                                                                                          // fichier
                                                                                                          // Enum/TypeEnergie.java
        int quantite = (int) (Math.random() * 900) + 100;

        double prix = (int) (Math.random() * 9000) / 100.0 + 100;

        return new Energie(producteur, type.toString(), pays.toString(), quantite, prix, false);
    }
}