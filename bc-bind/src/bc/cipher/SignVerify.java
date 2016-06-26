/*

 */
package bc.cipher;

import bc.cipher.api.Cipher;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 *
 * @author codetime
 */
public class SignVerify implements Cipher {

    @Override
    public String perform(byte[] message, byte[] hashFile, String keyFileName) throws Exception {
        String pubK = readKeyFile(keyFileName);
        PublicKey pubSavedFile = loadPublicKey(pubK.toString());
        System.out.println(pubSavedFile);
        //
        Signature sig = Signature.getInstance("SHA1withDSA", "SUN");
        sig.initVerify(pubSavedFile);
        System.out.println("file to verify:"+new String(hashFile));
        sig.update(hashFile);        
        //        
        boolean verifies = sig.verify(message);
        System.out.println("signature verifies: " + verifies);

        return "signature verifies: " + verifies;
    }

    public static PublicKey loadPublicKey(String stored) throws GeneralSecurityException {
        byte[] data = base64Decode(stored);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(data);
        KeyFactory fact = KeyFactory.getInstance("DSA","SUN");
        return fact.generatePublic(spec);
    }
    
    private static byte[] base64Decode(String key64) {
        return Base64.getDecoder().decode(key64);
    }
    
    public static String readKeyFile(String fileName) throws Exception {
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
    public String perform(byte[] message, String fileName) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public String perform(byte[] message) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

}
