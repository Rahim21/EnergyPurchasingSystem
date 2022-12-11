package ClassServeurTARE;

import ClassEnergie.Energie;
import Config.Messenger;
import Config.RSA;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.Headers;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.URLDecoder;
import java.net.MalformedURLException;

import org.json.JSONArray;
import org.json.JSONObject;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

class TareHandler implements HttpHandler {

    private Tare tare;
    public final int portMarche_UDP;
    public final int portTARE;
    private final Messenger gestionMessage;
    private static String reponseHTTP;

    public TareHandler(Tare tare, int portTARE, int portMarche_UDP) {
        this.tare = tare;
        this.portMarche_UDP = portMarche_UDP;
        this.portTARE = portTARE;
        this.gestionMessage = new Messenger("TARE | " + tare.getNom());
        this.reponseHTTP = "";
    }

    public void handle(HttpExchange t) {
        this.reponseHTTP = "";
        URI requestedUri = t.getRequestURI();
        String query = requestedUri.getRawQuery();

        // Utilisation d'un flux pour lire les données du message Http
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(t.getRequestBody(), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            gestionMessage.afficheMessage("Erreur lors de la récupération du flux " + e);
            System.exit(0);
        }

        // Récupération des données en POST
        try {
            query = br.readLine();
        } catch (IOException e) {
            gestionMessage.afficheMessage("Erreur lors de la lecture d'une ligne " + e);
            System.exit(0);
        }

        // Affichage des données
        if (query == null)
            this.reponseHTTP += "Aucune";
        else {
            try {
                query = URLDecoder.decode(query, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                query = "";
            }
            this.reponseHTTP += query;
        }

        // // Envoi de l'en-tête Http
        // try {
        // Headers h = t.getResponseHeaders();
        // // Content-type: application/x-www-form-urlencoded
        // h.set("Content-Type", "text/html; charset=utf-8");
        // t.sendResponseHeaders(200, reponseHTTP.getBytes().length);
        // } catch (IOException e) {
        // gestionMessage.afficheMessage("Erreur lors de l'envoi de l'en-tête : " + e);
        // System.exit(0);
        // }

        // // Envoi du corps (données HTML)
        // try {
        // OutputStream os = t.getResponseBody();
        // os.write(reponseHTTP.getBytes());
        // os.close();
        // } catch (IOException e) {
        // gestionMessage.afficheMessage("Erreur lors de l'envoi du corps : " + e);
        // }

        gestionMessage.afficheMessage("Lu  " + this.reponseHTTP);
        tare_UDP(t);
    }

    public void tare_UDP(HttpExchange t) {
        // TARE UDP

         // RSA pour la communication
         String dossierCourant = System.getProperty("user.dir");
         String dossierMARCHE = dossierCourant + "/Serveurs/src/ClassServeurMarcheGros/";


        // Création de la socket
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            gestionMessage.afficheMessage("Erreur lors de la création du socket : " + e);
            System.exit(0);
        }

        String invitation = "TARE:" + portTARE;
        // Envoi de l'invitation au MarcheGros [communication MarcheGros - TARE]
        try {
            // String messageChiffre = RSA.chiffrerRSA(dossierMARCHE+"MARCHE_PublicKey.bin", invitation);

            // InetAddress adresse = InetAddress.getByName("localhost");    
            // DatagramPacket msg = new DatagramPacket(messageChiffre.getBytes(), messageChiffre.length(), adresse, portMarche_UDP);

            // Transformation en tableau d'octets
            byte[] donnees = invitation.getBytes();
            InetAddress adresse = InetAddress.getByName("localhost");
            DatagramPacket msg = new DatagramPacket(donnees, donnees.length, adresse, portMarche_UDP);

            socket.send(msg);
            gestionMessage.afficheMessage("Envoi de l'invitation de communication au Marche.");
        } catch (UnknownHostException e) {
            gestionMessage.afficheMessage("Erreur lors de la création de l'adresse : " + e);
            System.exit(0);
        } catch (IOException e) {
            gestionMessage.afficheMessage("Erreur lors de l'envoi du message : " + e);
            System.exit(0);
        }

        // si reponseHTTP n'est pas null [si le revendeur a répondu]
        if (this.reponseHTTP != null) {
            // Création de l'Energie

            String[] messages = this.reponseHTTP.split(":::");
            String type_requete = messages[0]; // COMMANDE ou VERIFICATION
            String requete = messages[1];   // JSON
        
            JSONObject obj = new JSONObject(requete);
            Energie obj_e = null;
            if(type_requete.equals("COMMANDE")) {
            JSONObject commande = obj.getJSONObject("commande");
            obj_e = Energie.fromJSON(commande.toString());
            } else if(type_requete.equals("VERIFICATION")) {
                obj.put("Certificat", "ATTENTE DE VERIFICATION");
                obj_e = Energie.fromJSON(obj.toString());
            }
            gestionMessage.afficheMessage("Energie récupéré du Revendeur : " + obj_e);

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
                        adresse, portTARE);
                socket.send(msg);
                gestionMessage.afficheMessage("Envoi de l'objet Energie au Marche.");
            } catch (UnknownHostException e) {
                gestionMessage.afficheMessage("Erreur lors de la création de l'adresse : " + e);
                System.exit(0);
            } catch (IOException e) {
                gestionMessage.afficheMessage("Erreur lors de l'envoi du message : " + e);
                System.exit(0);
            }

            // recuperation de la reponse de la part du Marche de gros
            byte[] donnees = new byte[1024];
            DatagramPacket msg = new DatagramPacket(donnees, donnees.length);
            try {
                socket.receive(msg);
                gestionMessage.afficheMessage(
                        "Réception de la réponse du Marche de gros: " + new String(msg.getData()));
            } catch (IOException e) {
                gestionMessage.afficheMessage("Erreur lors de la réception du message : " + e);
                System.exit(0);
            }

            // Envoi de l'en-tête Http
            try {
                Headers h = t.getResponseHeaders();
                h.set("Content-Type", "application/json ; charset=utf-8");
                t.sendResponseHeaders(200, msg.getData().length);
            } catch (IOException e) {
                System.err.println("Erreur lors de l'envoi de l'en-tête : " + e);
                System.exit(0);
            }

            try {
                OutputStream os = t.getResponseBody();
                String reponse = new String(msg.getData());
                os.write(reponse.getBytes());
                os.close();
            } catch (IOException e) {
                System.err.println("Erreur lors de l'envoi du corps : " + e);
            }

            gestionMessage.afficheMessage("Envoie de réponse au Revendeur  " + donnees);
            this.reponseHTTP = "";

            // Fermeture de la socket
            socket.close();
            gestionMessage.afficheMessage("Fermeture de la socket.");
        }
        gestionMessage.afficheMessage("Fin de la communication.");
    }
}
