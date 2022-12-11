package ClassServeurAMI;

import java.security.KeyPairGenerator;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Signature;
import java.security.SignatureException;
import java.security.InvalidKeyException;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.FileInputStream;

public class RSA {

    public static void GenererClesRSA(String cle_prive, String cle_publique) {
        // Création d'un générateur RSA
        KeyPairGenerator generateurCles = null;
        try {
            generateurCles = KeyPairGenerator.getInstance("RSA");
            generateurCles.initialize(2048);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Erreur lors de l'initialisation du generateur de cles : " + e);
            System.exit(0);
        }
        // Génération de la paire de clés
        KeyPair paireCles = generateurCles.generateKeyPair();
        // Sauvegarde de la clé privée
        GestionClesRSA.sauvegardeClePrivee(paireCles.getPrivate(), cle_prive);
        // Sauvegarde de la clé publique
        GestionClesRSA.sauvegardeClePublique(paireCles.getPublic(), cle_publique);
        System.out.println("Cles sauvegardees.");
    }

    // Signer un fichier avec une clé privée RSA : fichier_signer est le fichier à
    // signer, fichier_sauvegarde est le fichier où sera sauvegardée la signature
    public static void SignerFichierRSA(String cle_prive, String fichier_signer, String fichier_sauvegarde) {

        // Reconstruction de la clé
        PrivateKey clePrivee = GestionClesRSA.lectureClePrivee(cle_prive);
        // Création de la signature
        Signature signature = null;
        try {
            signature = Signature.getInstance("SHA256withRSA");
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Erreur lors de l'initialisation de la signature : " + e);
            System.exit(0);
        }
        // Initialisation de la signature
        try {
            signature.initSign(clePrivee);
        } catch (InvalidKeyException e) {
            System.err.println("Clé privée invalide : " + e);
            System.exit(0);
        }
        // Mise-à-jour de la signature par rapport au contenu du fichier
        try {
            BufferedInputStream fichier = new BufferedInputStream(new FileInputStream(fichier_signer));
            byte[] tampon = new byte[1024];
            int n;
            while (fichier.available() != 0) {
                n = fichier.read(tampon);
                signature.update(tampon, 0, n);
            }
            fichier.close();
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier à signer : " + e);
            System.exit(0);
        } catch (SignatureException e) {
            System.err.println("Erreur lors de la mise-à-jour de la signature : " + e);
            System.exit(0);
        }
        // Sauvegarde de la signature du fichier
        try {
            FileOutputStream fichier = new FileOutputStream(fichier_sauvegarde);
            fichier.write(signature.sign());
            fichier.close();
        } catch (SignatureException e) {
            System.err.println("Erreur lors de la récupération de la signature : " + e);
            System.exit(0);
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde de la signature : " + e);
            System.exit(0);
        }
    }

    // Signer un fichier avec une clé privée RSA : fichier_signer est un JSON
    public static void SignerFichierRSA_JSON(String cle_prive, String fichier_signer, String fichier_sauvegarde) {
        // Reconstruction de la clé
        PrivateKey clePrivee = GestionClesRSA.lectureClePrivee(cle_prive);
        // Création de la signature
        Signature signature = null;
        try {
            signature = Signature.getInstance("SHA256withRSA");
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Erreur lors de l'initialisation de la signature : " + e);
            System.exit(0);
        }
        // Initialisation de la signature
        try {
            signature.initSign(clePrivee);
        } catch (InvalidKeyException e) {
            System.err.println("Clé privée invalide : " + e);
            System.exit(0);
        }
        // Mise-à-jour de la signature par rapport au contenu du JSON
        try {
            signature.update(fichier_signer.getBytes());
        } catch (SignatureException e) {
            System.err.println("Erreur lors de la mise-à-jour de la signature : " + e);
            System.exit(0);
        }
        // Sauvegarde de la signature du fichier
        try {
            FileOutputStream fichier = new FileOutputStream(fichier_sauvegarde);
            fichier.write(signature.sign());
            fichier.close();
        } catch (SignatureException e) {
            System.err.println("Erreur lors de la récupération de la signature : " + e);
            System.exit(0);
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde de la signature : " + e);
            System.exit(0);
        }
    }

    // fichier_verif : fichier à vérifier
    public static boolean VerifierSignatureRSA(String fichier_verif, String fichier_signature,
            String fichier_publique) {

        // Reconstruction de la clé
        PublicKey clePublique = GestionClesRSA.lectureClePublique(fichier_publique);
        boolean res = false;
        // Lecture de la signature
        byte[] signatureFournie = null;
        try {
            FileInputStream fichier = new FileInputStream(fichier_signature);
            signatureFournie = new byte[fichier.available()];
            fichier.read(signatureFournie);
            fichier.close();
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture de la signature : " + e);
            System.exit(0);
        }

        // Création de la signature
        Signature signature = null;
        try {
            signature = Signature.getInstance("SHA256withRSA");
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Erreur lors de l'initialisation de la signature : " + e);
            System.exit(0);
        }

        // Initialisation de la signature
        try {
            signature.initVerify(clePublique);
        } catch (InvalidKeyException e) {
            System.err.println("Cle publique invalide : " + e);
            System.exit(0);
        }

        // Mise-à-jour de la signature par rapport au contenu du fichier
        try {
            BufferedInputStream fichier = new BufferedInputStream(new FileInputStream(fichier_verif));
            byte[] tampon = new byte[1024];
            int n;
            while (fichier.available() != 0) {
                n = fichier.read(tampon);
                signature.update(tampon, 0, n);
            }
            fichier.close();
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier à vérifier : " + e);
            System.exit(0);
        } catch (SignatureException e) {
            System.err.println("Erreur lors de la mise-à-jour de la signature : " + e);
            System.exit(0);
        }

        // Comparaison des deux signatures
        try {
            if (signature.verify(signatureFournie))

                res = true;
            else
                res = false;
        } catch (SignatureException e) {
            System.err.println("Erreur lors de la vérification des signatures : " + e);
            System.exit(0);
        }
        return res;
    }

    // fichier_verif : json de l'énergie à vérifier
    public static String VerifierSignatureRSA_JSON(String fichier_verif, String fichier_signature,
            String fichier_publique) {
        // Reconstruction de la clé
        PublicKey clePublique = GestionClesRSA.lectureClePublique(fichier_publique);
        // Lecture de la signature
        byte[] signatureFournie = null;
        try {
            FileInputStream fichier = new FileInputStream(fichier_signature);
            signatureFournie = new byte[fichier.available()];
            fichier.read(signatureFournie);
            fichier.close();
        } catch (IOException e) {
            return "Aucune signature trouvée";
            // System.err.println("Erreur lors de la lecture de la signature : " + e);
            // System.exit(0);
        }

        // Création de la signature
        Signature signature = null;
        try {
            signature = Signature.getInstance("SHA256withRSA");
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Erreur lors de l'initialisation de la signature : " + e);
            System.exit(0);
        }

        // Initialisation de la signature
        try {
            signature.initVerify(clePublique);
        } catch (InvalidKeyException e) {
            System.err.println("Cle publique invalide : " + e);
            System.exit(0);
        }

        // Mise-à-jour de la signature par rapport au contenu du JSON
        try {
            signature.update(fichier_verif.getBytes());
        } catch (SignatureException e) {
            System.err.println("Erreur lors de la mise-à-jour de la signature : " + e);
            System.exit(0);
        }

        // Comparaison des deux signatures
        try {
            if (signature.verify(signatureFournie))

                return "CERTIFICATION VALIDE";
            else
                return "CERTIFICATION INVALIDE";
        } catch (SignatureException e) {
            System.err.println("Erreur lors de la vérification des signatures : " + e);
            System.exit(0);
        }
        return "Erreur inconnue";
    }

}