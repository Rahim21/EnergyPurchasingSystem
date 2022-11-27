package ClassChiffrementAES;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.security.InvalidKeyException;

public class ChiffrementAES {

    public static byte[] chiffrer(String motDePasse, String message) {
        // Chiffrement du message
        System.out.println("Message origine   : " + message);
        SecretKeySpec specification = new SecretKeySpec(motDePasse.getBytes(), "AES");
        byte[] bytes = null;

        try {
            Cipher chiffreur = Cipher.getInstance("AES");
            chiffreur.init(Cipher.ENCRYPT_MODE, specification);
            bytes = chiffreur.doFinal(message.getBytes());
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
                | BadPaddingException e) {
            System.err.println("Erreur lors du chiffrement : " + e);
            System.exit(0);
        }
        return bytes;
    }

    public static byte[] dechiffrer(String motDePasse, byte[] messageChiffre) {
        SecretKeySpec specification = new SecretKeySpec(motDePasse.getBytes(), "AES");
        byte[] bytes = null;
        // Dechiffrement du message
        try {
            Cipher dechiffreur = Cipher.getInstance("AES");
            dechiffreur.init(Cipher.DECRYPT_MODE, specification);
            bytes = dechiffreur.doFinal(messageChiffre);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
                | BadPaddingException e) {
            System.err.println("Erreur lors du chiffrement : " + e);
            System.exit(0);
        }
        return bytes;
    }
}
