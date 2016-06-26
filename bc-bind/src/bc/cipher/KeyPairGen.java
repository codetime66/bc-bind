/*

 */
package bc.cipher;

import bc.cipher.api.Cipher;
import java.io.FileOutputStream;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;

/**
 *
 * @author codetime
 */
public class KeyPairGen implements Cipher {

    @Override
    public String perform(byte[] fileName) throws Exception {
        KeyPairGenerator gen = KeyPairGenerator.getInstance("DSA","SUN");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
        gen.initialize(1024, random);
        KeyPair pair = gen.generateKeyPair();

        String pubKey = savePublicKey(pair.getPublic());
        String pubKeyFileName="PUBKEY"+new String(fileName);
        writeKeyFile(pubKey, pubKeyFileName);

        String privKey = savePrivateKey(pair.getPrivate());
        String privKeyFileName="PRIVKEY"+new String(fileName);        
        writeKeyFile(privKey, privKeyFileName);

        return null;
    }

    public String savePrivateKey(PrivateKey priv) throws GeneralSecurityException {
        KeyFactory fact = KeyFactory.getInstance("DSA","SUN");
        PKCS8EncodedKeySpec spec = fact.getKeySpec(priv,
                PKCS8EncodedKeySpec.class);
        byte[] packed = spec.getEncoded();
        String key64 = base64Encode(packed);

        Arrays.fill(packed, (byte) 0);
        return key64;
    }

    public String savePublicKey(PublicKey publ) throws GeneralSecurityException {
        KeyFactory fact = KeyFactory.getInstance("DSA","SUN");
        X509EncodedKeySpec spec = fact.getKeySpec(publ,
                X509EncodedKeySpec.class);
        return base64Encode(spec.getEncoded());
    }

    private String base64Encode(byte[] packed) {
        return Base64.getEncoder().encodeToString(packed);
    }

    private static void writeKeyFile(String key, String fileName) throws Exception {
        FileOutputStream keyfos = new FileOutputStream(fileName);
        keyfos.write(key.getBytes());
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
