package Config;

public class GenerationCles implements Runnable {

    public final Messenger gestionMessage;

    public GenerationCles() {
        this.gestionMessage = new Messenger("GenerationCles");
    }

    public void run() {

        String dossierCourant = System.getProperty("user.dir");
        // gestionMessage.afficheMessage("chemin : " + dossierCourant);

        String dossierTARE = dossierCourant + "/Serveurs/src/ClassServeurTARE/";
        RSA.GenererClesRSA(dossierTARE + "TARE_PrivateKey.bin", dossierTARE + "TARE_PublicKey.bin");

        String dossierMARCHE = dossierCourant + "/Serveurs/src/ClassServeurMarcheGros/";
        RSA.GenererClesRSA(dossierMARCHE+ "MARCHE_PrivateKey.bin", dossierMARCHE+ "MARCHE_PublicKey.bin");

        String dossierPONE = dossierCourant + "/Serveurs/src/ClassClientPONE/";
        RSA.GenererClesRSA(dossierPONE+ "PONE_PrivateKey.bin", dossierPONE+ "PONE_PublicKey.bin");

        String dossierAMI = dossierCourant + "/Serveurs/src/ClassServeurAMI/";
        RSA.GenererClesRSA(dossierAMI+ "AMI_PrivateKey.bin", dossierAMI+ "AMI_PublicKey.bin");
    }
}