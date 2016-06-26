package bc.cipher;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 *
 * @author codetime
 */
public class RSAEncryption implements bc.cipher.api.Cipher {
    
    @Override
    public String perform(byte[] message, String keyFileName) throws Exception {
        String pubK = readKeyFile(keyFileName);
        PublicKey pubSavedFile = loadPublicKey(pubK.toString());
        System.out.println(pubSavedFile);

        // implements RSA
        javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("RSA","SUN");

        // encrypting the message using the public key
        // initialize the cryptography algorithm
        cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, pubSavedFile);

        // encrypting the message
        byte[] encryptedMensage = cipher.doFinal(message);

        System.out.println("the encrypted mensage:");
        System.out.println(new String(encryptedMensage));

        return new String(encryptedMensage);
    }

    protected PublicKey loadPublicKey(String stored) throws GeneralSecurityException {
        byte[] data = base64Decode(stored);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(data);
        KeyFactory fact = KeyFactory.getInstance("DSA","SUN");
        return fact.generatePublic(spec);
    }
    
    protected byte[] base64Decode(String key64) {
        return Base64.getDecoder().decode(key64);
    }
    
    protected String readKeyFile(String fileName) throws Exception {
        Reader in = new BufferedReader(
                new InputStreamReader(new FileInputStream(fileName), "UTF8"));
        StringBuffer buf = new StringBuffer();
        int ch;
        while ((ch = in.read()) > -1) {
            buf.append((char) ch);
        }
        in.close();
        return buf.toString();
    }

    @Override
    public String perform(byte[] message) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public String perform(byte[] message, byte[] hashFile, String keyFileName) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    

}