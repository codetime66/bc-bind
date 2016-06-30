/*
 * java -cp /home/codetime/projects/bc-bind/target/bc-1.0-SNAPSHOT.jar:/home/codetime/glassfish4/glassfish/modules/javax.json.jar bc.cipher.KeyPairGen <PARTICIPANT_SHORT_NAME>
 *
 */
package bc.cipher;

import bc.cipher.api.IKeyPairGen;
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
public class KeyPairGen implements IKeyPairGen {

    public static void main(String[] args) throws Exception {
       KeyPairGen keyPairGen = new KeyPairGen();
       System.out.println( keyPairGen.perform(args[0]));
    }
    
    @Override
    public String perform(String fileName) throws Exception {
        KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
        gen.initialize(1024, random);
        KeyPair pair = gen.generateKeyPair();

        String pubKey = savePublicKey(pair.getPublic());
        String pubKeyFileName="PUBKEY"+new String(fileName.getBytes());
        writeKeyFile(pubKey, pubKeyFileName);

        String privKey = savePrivateKey(pair.getPrivate());
        String privKeyFileName="PRIVKEY"+new String(fileName.getBytes());        
        writeKeyFile(privKey, privKeyFileName);

        return null;
    }

    protected String savePrivateKey(PrivateKey priv) throws GeneralSecurityException {
        KeyFactory fact = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec spec = fact.getKeySpec(priv,
                PKCS8EncodedKeySpec.class);
        byte[] packed = spec.getEncoded();
        String key64 = base64Encode(packed);

        Arrays.fill(packed, (byte) 0);
        return key64;
    }

    protected String savePublicKey(PublicKey publ) throws GeneralSecurityException {
        KeyFactory fact = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec spec = fact.getKeySpec(publ,
                X509EncodedKeySpec.class);
        return base64Encode(spec.getEncoded());
    }

    protected String base64Encode(byte[] packed) {
        return Base64.getEncoder().encodeToString(packed);
    }

    protected void writeKeyFile(String key, String fileName) throws Exception {
        FileOutputStream keyfos = new FileOutputStream(fileName);
        keyfos.write(key.getBytes());
        keyfos.close();
    }
}
