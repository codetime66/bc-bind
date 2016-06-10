package bc.cipher;

import bc.cipher.api.Command;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author codetime
 */
public class RSADecryption implements Command {
    
    public void execute(byte[] message)
            throws
            NoSuchAlgorithmException,
            NoSuchPaddingException,
            InvalidKeyException,
            BadPaddingException,
            IllegalBlockSizeException {

        // generating a 1024 bit RSA pair keys
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(1024);
        KeyPair keys = generator.generateKeyPair();

        // implements RSA
        Cipher cipher = Cipher.getInstance("RSA");

        // encrypting the message using the public key
        // initialize the cryptography algorithm
        cipher.init(Cipher.ENCRYPT_MODE, keys.getPublic());

        // encrypting the message
        byte[] encryptedMensage = cipher.doFinal(message);

        System.out.println("the encrypted mensage:");
        System.out.println(new String(encryptedMensage));

        // decrypting the message using the private key
        cipher.init(Cipher.DECRYPT_MODE, keys.getPrivate());
        byte[] originalMessage = cipher.doFinal(encryptedMensage);
        System.out.println("the original message was:");
        System.out.println(new String(originalMessage));

    }
}