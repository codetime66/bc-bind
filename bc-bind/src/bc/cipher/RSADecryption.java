package bc.cipher;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;

/**
 *
 * @author codetime
 */
public class RSADecryption implements bc.cipher.api.Cipher {
    
    @Override
    public String perform(byte[] message, String keyFileName) throws Exception {
        String privK = readKeyFile(keyFileName);
        PrivateKey privSavedFile = loadPrivateKey(privK.toString());
        System.out.println(privSavedFile);

        // implements RSA
        javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("RSA","SUN");

        // decrypting the message using the private key
        // initialize the cryptography algorithm        
        cipher.init(javax.crypto.Cipher.DECRYPT_MODE, privSavedFile);
        
        // decrypting the message        
        byte[] originalMessage = cipher.doFinal(message);
        
        System.out.println("the original message was:");
        System.out.println(new String(originalMessage));
        
        return new String(originalMessage);
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

    protected PrivateKey loadPrivateKey(String key64) throws GeneralSecurityException {
        byte[] clear = base64Decode(key64);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(clear);
        KeyFactory fact = KeyFactory.getInstance("DSA", "SUN");
        PrivateKey priv = fact.generatePrivate(keySpec);
        Arrays.fill(clear, (byte) 0);
        return priv;
    }

    protected byte[] base64Decode(String key64) {
        return Base64.getDecoder().decode(key64);
    }
    
    @Override
    public String perform(byte[] message) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String perform(byte[] message, byte[] hashFile, String keyFileName) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}