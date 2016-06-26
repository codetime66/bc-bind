/*
*
 */
package bc.cipher;

import bc.cipher.api.Cipher;
import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

/**
 *
 * @author codetime
 */
public class KeyPairGen implements Cipher {

    @Override
    public String perform(byte[] fileName) throws Exception {
        //
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA", "SUN");
        //
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
        keyGen.initialize(1024, random);
        //
        KeyPair pair = keyGen.generateKeyPair();
        PrivateKey priv = pair.getPrivate();
        PublicKey pub = pair.getPublic();
        //
        /* save the private key in a file */
        String privFileName = "PRIV"+new String(fileName);
        byte[] privKey = priv.getEncoded();
        writeKeyFile(privKey, privFileName);
        //        
        /* save the public key in a file */
        String pubFileName = "PUB"+new String(fileName);
        byte[] pubKey = pub.getEncoded();
        writeKeyFile(pubKey, pubFileName);
        //
        return null;
    }

    protected void writeKeyFile(byte[] key, String fileName) throws Exception {
        FileOutputStream keyfos = new FileOutputStream(fileName);
        keyfos.write(key);
        keyfos.close();
    }

    @Override
    public String perform(byte[] message, byte[] hashFile, String keyFileName) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public String perform(byte[] message, String fileName) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
}
