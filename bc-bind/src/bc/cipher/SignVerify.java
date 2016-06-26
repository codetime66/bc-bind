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
        FileInputStream sigfis = new FileInputStream(new String(message));
        byte[] sigToVerify = new byte[sigfis.available()];
        sigfis.read(sigToVerify);
        sigfis.close();
        //
        Signature sig = Signature.getInstance("SHA1withDSA", "SUN");
        sig.initVerify(pubKey);
        //
        FileInputStream datafis = new FileInputStream(new String(hashFile));
        BufferedInputStream bufin = new BufferedInputStream(datafis);
        byte[] buffer = new byte[1024];
        int len;
        while (bufin.available() != 0) {
            len = bufin.read(buffer);
            sig.update(buffer, 0, len);
        };
        bufin.close();
        //
        boolean verifies = sig.verify(sigToVerify);
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
