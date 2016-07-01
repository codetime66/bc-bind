/*
 * java -cp /home/codetime/projects/bc-bind/target/bc-1.0-SNAPSHOT.jar:/home/codetime/glassfish4/glassfish/modules/javax.json.jar bc.cipher.KeyPairGen <PARTICIPANT_SHORT_NAME>
 *
 */
package bc.cipher;

import bc.cipher.api.IKeyPairGen;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.Reader;
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
        String[] result = keyPairGen.perform(args[0]);
        System.out.println("publicKey=" + result[0] + ", privatekey=" + result[1]);
    }

    @Override
    public String[] perform(String fileName) throws Exception {
        KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
        gen.initialize(1024, random);
        KeyPair pair = gen.generateKeyPair();

        String pubKey = savePublicKey(pair.getPublic());
        String pubKeyFileName = "PUBKEY" + new String(fileName.getBytes());
        writeKeyFile(pubKey, pubKeyFileName);

        String privKey = savePrivateKey(pair.getPrivate());
        String privKeyFileName = "PRIVKEY" + new String(fileName.getBytes());
        writeKeyFile(privKey, privKeyFileName);

        return new String[]{pubKey, privKey};
    }

    public String savePrivateKey(PrivateKey priv) throws GeneralSecurityException {
        KeyFactory fact = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec spec = fact.getKeySpec(priv,
                PKCS8EncodedKeySpec.class);
        byte[] packed = spec.getEncoded();
        String key64 = base64Encode(packed);

        Arrays.fill(packed, (byte) 0);
        return key64;
    }

    public String savePublicKey(PublicKey publ) throws GeneralSecurityException {
        KeyFactory fact = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec spec = fact.getKeySpec(publ,
                X509EncodedKeySpec.class);
        return base64Encode(spec.getEncoded());
    }

    public PublicKey loadPublicKey(String stored) throws GeneralSecurityException {
        byte[] data = base64Decode(stored);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(data);
        KeyFactory fact = KeyFactory.getInstance("RSA");
        return fact.generatePublic(spec);
    }

    public PrivateKey loadPrivateKey(String key64) throws GeneralSecurityException {
        byte[] clear = base64Decode(key64);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(clear);
        KeyFactory fact = KeyFactory.getInstance("RSA");
        PrivateKey priv = fact.generatePrivate(keySpec);
        Arrays.fill(clear, (byte) 0);
        return priv;
    }

    public byte[] base64Decode(String key64) {
        return Base64.getDecoder().decode(key64);
    }

    public String base64Encode(byte[] packed) {
        return Base64.getEncoder().encodeToString(packed);
    }

    public byte[] base64Decode(byte[] key64) {
        return Base64.getDecoder().decode(key64);
    }    
    
    public void writeKeyFile(String key, String fileName) throws Exception {
        FileOutputStream keyfos = new FileOutputStream(fileName);
        keyfos.write(key.getBytes());
        keyfos.close();
    }

    public String readKeyFile(String fileName) throws Exception {
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

}
