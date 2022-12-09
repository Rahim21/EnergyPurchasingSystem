package ClassClientPONE;

import ClassEnergie.Energie;
import ClassEnergie.Enum.Pays;
import ClassEnergie.Enum.TypeEnergie;
import Config.Messenger;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
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
        Pone pone = new Pone(0, nomPONE);
        // Création de la socket
        DatagramSocket socket = null;
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

        // // la reception de l'objet Energie envoyé par le serveur
        // Energie energie_manquant = null;
        // DatagramPacket msg = null;
        // try {
        // byte[] donnees = new byte[1024];
        // msg = new DatagramPacket(donnees, donnees.length);
        // socket.receive(msg);
        // energie_manquant = Energie.fromJSON(msg.toString());
        // gestionMessage.afficheMessage("Réception de l'objet Energie du serveur.");
        // } catch (IOException e) {
        // gestionMessage.afficheMessage("Erreur lors de la réception du message : " +
        // e);
        // System.exit(0);
        // }

        // // rebond de l'objet Energie
        // try {
        // byte[] donnees = energie_manquant.toJSON().toString().getBytes();
        // InetAddress adresse = InetAddress.getByName("localhost");
        // msg = new DatagramPacket(donnees, donnees.length,
        // adresse, portPONE_UDP);
        // socket.send(msg);
        // gestionMessage.afficheMessage("Envoi de l'objet Energie au serveur.");
        // } catch (UnknownHostException e) {
        // gestionMessage.afficheMessage("Erreur lors de la création de l'adresse : " +
        // e);
        // System.exit(0);
        // } catch (IOException e) {
        // gestionMessage.afficheMessage("Erreur lors de l'envoi du message : " + e);
        // System.exit(0);
        // }

        // Fermeture de la socket
        socket.close();
        gestionMessage.afficheMessage("Fermeture de la socket.");
    }

    public static Energie genererEnergie() {
        // String[] liste_producteur = { "EDF", "Total", "Engie", "Uniper", "EDP",
        // "Enel", "RWE", "Vattenfall", "Tepco",
        // "GDF",
        // "Iberdrola" };

        int producteur = (int) (Math.random() * 1000); // aléatoire pour le test
        Pays pays = Pays.values()[(int) (Math.random() * Pays.values().length - 1)]; // depuis le fichier Enum/Pays.java
        TypeEnergie type = TypeEnergie.values()[(int) (Math.random() * TypeEnergie.values().length - 1)]; // depuis le
                                                                                                          // fichier
                                                                                                          // Enum/TypeEnergie.java
        int quantite = (int) (Math.random() * 900) + 100;

        // prix entre 10 et 100
        int prixEntier = (int) ((Math.random() * 900) + 100) * 100;
        double prix = prixEntier / 100.0;

        return new Energie(producteur, type.toString(), pays.toString(), quantite, prix, false);
    }
}