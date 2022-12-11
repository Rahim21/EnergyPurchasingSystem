import Config.Configuration;
import Config.GenerationCles;
import ClassServeurTARE.ServeurTare_HTTP;
import ClassServeurMarcheGros.ServeurMulti_MarcheGros_UDP;
import ClassClientPONE.ClientPONE_UDP;
import ClassServeurAMI.ServeurMulti_AMI_TCP;

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

        int portTARE = config.getInt("portTARE");
        int portPONE_UDP_1 = config.getInt("portPONE_UDP_1");
        int portPONE_UDP_2 = config.getInt("portPONE_UDP_2");
        int portMarche_UDP = config.getInt("portMarche_UDP");
        int portAMI_TCP = config.getInt("portAMI_TCP");

        java.util.ArrayList<Thread> mesServices = new java.util.ArrayList<Thread>();

        // On doit donner une référence d'objet implémentant l'interface Runnable pour
        // créer un Thread
        String[] nom = { "Souhail", "Rahim", "Sami", "Walid", "Fayssal", "Leo", "Corentin", "Dilara", "Mikael",
                "Quentin" }; // 10 noms => TARE, PONE

        // boucle pour créer des tarés, leur donné un id, pour l'entête de la requête
        // HTTP /tare{id}
        mesServices.add(new Thread(new GenerationCles())); // Génération des clés
        mesServices.add(new Thread(new ServeurTare_HTTP(portTARE, portMarche_UDP, nom[(int) (Math.random() * 10)]))); // TARE
        mesServices.add(new Thread(new ServeurMulti_MarcheGros_UDP(portMarche_UDP, portAMI_TCP))); // MARCHE GROS
        mesServices.add(new Thread(new ServeurMulti_AMI_TCP(portAMI_TCP))); // AMI
        mesServices
                .add(new Thread(new ClientPONE_UDP(portPONE_UDP_1, portMarche_UDP, nom[(int) (Math.random() * 10)]))); // PONE
                                                                                                                       // 1
        mesServices
                .add(new Thread(new ClientPONE_UDP(portPONE_UDP_2, portMarche_UDP, nom[(int) (Math.random() * 10)]))); // PONE
                                                                                                                       // 2

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