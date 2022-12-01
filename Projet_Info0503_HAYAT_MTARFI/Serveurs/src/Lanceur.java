import Config.Configuration;
import ClassServeurTARE.ServeurTare_HTTP;
import ClassServeurMarcheGros.ServeurMarcheGros_UDP;
import ClassClientPONE.ClientPONE_UDP;
import ClassServeurAMI.ServeurAMI_TCP;

/**
 * Lanceur de test pour démarrer une communication TCP (basé sur la fiche 1 du
 * TP sur la communication TCP).
 */
public class Lanceur {

    /**
     * Constructeur par défaut (pour éviter un warning lors de la génération de la
     * documentation)
     */
    Lanceur() {
    }

    /**
     * Code de test : on prépare les Thread et on les lance (serveur puis client).
     * 
     * @param args Les arguments de la ligne de commande notamment pour fournir le
     *             chemin vers le fichier de configuration json.
     */
    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Merci de donner un fichier de configuration json");
            System.exit(0);
        }

        Configuration config = new Configuration(args[0]);

        // String adresseServeurTCP = config.getString("adresseServeurTCP");
        // int portServeurTCP = config.getInt("portServeurTCP");
        int portEcouteTare = config.getInt("portEcouteTare");
        int portEnvoiTare = config.getInt("portEnvoiTare");

        int portTARE_UDP = config.getInt("portTARE_UDP");
        int portPONE_UDP = config.getInt("portPONE_UDP");
        int portAMI_TCP = config.getInt("portAMI_TCP");
        // int portAMI_TCPchiffre = config.getInt("portAMI_TCPchiffre");

        java.util.ArrayList<Thread> mesServices = new java.util.ArrayList<Thread>();

        // On doit donner une référence d'objet implémentant l'interface Runnable pour
        // créer un Thread
        String[] nom = { "Souhail", "Rahim", "Sami", "Walid", "Fayssal", "Leo", "Corentin", "Dilara", "Mikael",
                "Quentin" }; // 10 noms => TARE, PONE
        mesServices
                .add(new Thread(new ServeurTare_HTTP(portEcouteTare, portEnvoiTare, nom[(int) (Math.random() * 10)]))); // TARE
        mesServices.add(new Thread(new ServeurMarcheGros_UDP(portEnvoiTare, portPONE_UDP, portAMI_TCP))); // MARCHE GROS
        // supprimer port tare 1) envoie "jesuisTARE" au PONE ensuite 2) envoie la
        // demande...
        mesServices.add(new Thread(new ClientPONE_UDP(portPONE_UDP, nom[(int) (Math.random() * 10)]))); // PONE 1
        mesServices.add(new Thread(new ClientPONE_UDP(portPONE_UDP, nom[(int) (Math.random() * 10)]))); // PONE 2
        mesServices.add(new Thread(new ServeurAMI_TCP(portAMI_TCP))); // AMI

        java.util.Iterator<Thread> it = mesServices.iterator();
        // Cela fonctionne ici car le serveur est démarré avant le client
        // Il faudrait mieux appeler spécifiquement chaque Thread (notamment dans le
        // projet)
        while (it.hasNext()) {
            Thread thread = it.next();
            thread.start();
        }
    }
}
