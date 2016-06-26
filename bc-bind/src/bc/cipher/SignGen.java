/*
 *
 */
package bc.cipher;

import bc.cipher.api.Cipher;
import bc.cipher.api.CipherFactory;
import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 *
 * @author codetime
 */
public class SignGen implements Cipher {

    public String perform(byte[] message, String keyFileName) throws Exception {
        // 
        FileInputStream keyfis = new FileInputStream(keyFileName);
        byte[] encKey = new byte[keyfis.available()];
        keyfis.read(encKey);
        keyfis.close();
        //
        PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(encKey);
        KeyFactory keyFactory = KeyFactory.getInstance("DSA", "SUN");
        PrivateKey privKey
                = keyFactory.generatePrivate(privKeySpec);
        //generating a hash from the message
        Cipher md = CipherFactory.getInstance("MDGenerator");
        String messageMD = md.perform(message);
        //signing the hash
        Signature dsa = Signature.getInstance("SHA1withDSA", "SUN");
        dsa.initSign(privKey);
        dsa.update(messageMD.getBytes());
        //
        byte[] realSig = dsa.sign();
        //
        return new String(realSig);
    }

    @Override
    public String perform(byte[] message, byte[] hashFile, String keyFileName) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public String perform(byte[] message) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
}
