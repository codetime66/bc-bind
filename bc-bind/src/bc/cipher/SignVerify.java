/*

 */
package bc.cipher;

import bc.cipher.api.Cipher;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;

/**
 *
 * @author codetime
 */
public class SignVerify implements Cipher {

    @Override
    public String perform(byte[] message, byte[] hashFile, String keyFileName) throws Exception {
        // 
        FileInputStream keyfis = new FileInputStream(keyFileName);
        byte[] encKey = new byte[keyfis.available()];
        keyfis.read(encKey);
        keyfis.close();
        //
        X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encKey);
        KeyFactory keyFactory = KeyFactory.getInstance("DSA", "SUN");
        PublicKey pubKey
                = keyFactory.generatePublic(pubKeySpec);
        //
        Signature sig = Signature.getInstance("SHA1withDSA", "SUN");
        sig.initVerify(pubKey);
        sig.update(hashFile);
        boolean verifies = sig.verify(message);
        System.out.println("signature verifies: " + verifies);

        return null;
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
