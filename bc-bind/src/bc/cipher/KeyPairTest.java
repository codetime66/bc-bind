/*

 */
package bc.cipher;

import java.io.BufferedInputStream;
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
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;

/**
 *
 * @author codetime
 */
public class KeyPairTest {

    public static PrivateKey loadPrivateKey(String key64) throws GeneralSecurityException {
        byte[] clear = base64Decode(key64);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(clear);
        KeyFactory fact = KeyFactory.getInstance("DSA","SUN");
        PrivateKey priv = fact.generatePrivate(keySpec);
        Arrays.fill(clear, (byte) 0);
        return priv;
    }

    public static PublicKey loadPublicKey(String stored) throws GeneralSecurityException {
        byte[] data = base64Decode(stored);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(data);
        KeyFactory fact = KeyFactory.getInstance("DSA","SUN");
        return fact.generatePublic(spec);
    }

    public static String savePrivateKey(PrivateKey priv) throws GeneralSecurityException {
        KeyFactory fact = KeyFactory.getInstance("DSA","SUN");
        PKCS8EncodedKeySpec spec = fact.getKeySpec(priv,
                PKCS8EncodedKeySpec.class);
        byte[] packed = spec.getEncoded();
        String key64 = base64Encode(packed);

        Arrays.fill(packed, (byte) 0);
        return key64;
    }

    public static String savePublicKey(PublicKey publ) throws GeneralSecurityException {
        KeyFactory fact = KeyFactory.getInstance("DSA","SUN");
        X509EncodedKeySpec spec = fact.getKeySpec(publ,
                X509EncodedKeySpec.class);
        return base64Encode(spec.getEncoded());
    }

    private static byte[] base64Decode(String key64) {
        return Base64.getDecoder().decode(key64);
    }

    private static String base64Encode(byte[] packed) {
        return Base64.getEncoder().encodeToString(packed);
    }

    public static void writeKeyFile(String key, String fileName) throws Exception {
        FileOutputStream keyfos = new FileOutputStream(fileName);
        keyfos.write(key.getBytes());
        keyfos.close();
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

    public static void main(String[] args) throws Exception {
        KeyPairGenerator gen = KeyPairGenerator.getInstance("DSA","SUN");
        //
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
        gen.initialize(1024, random);
        //        
        KeyPair pair = gen.generateKeyPair();

        String pubKey = savePublicKey(pair.getPublic());

        writeKeyFile(pubKey, "TSTPUBKEY");

        String pubK = readKeyFile("TSTPUBKEY");
        PublicKey pubSavedFile = loadPublicKey(pubK.toString());
        System.out.println(pair.getPublic() + "\n" + pubSavedFile);

        String privKey = savePrivateKey(pair.getPrivate());

        writeKeyFile(privKey, "TSTPRIVKEY");

        String privK = readKeyFile("TSTPRIVKEY");
        PrivateKey privSavedFile = loadPrivateKey(privK.toString());
        System.out.println(pair.getPrivate() + "\n" + privSavedFile);

        //
        Signature dsa = Signature.getInstance("SHA1withDSA", "SUN");
        dsa.initSign(privSavedFile);
        //
        FileInputStream fis = new FileInputStream(args[0]);
        BufferedInputStream bufin = new BufferedInputStream(fis);
        byte[] buffer = new byte[1024];
        int len;
        while ((len = bufin.read(buffer)) >= 0) {
            dsa.update(buffer, 0, len);
        };
        bufin.close();
        //
        byte[] realSig = dsa.sign();
        //
        /* save the signature in a file */
        FileOutputStream sigfos = new FileOutputStream("sig");
        sigfos.write( Base64.getEncoder().encode(realSig) );
        sigfos.close();
        //
        String sigFile = readKeyFile("sig");
        byte[] sigToVerify = base64Decode(sigFile);
        //
        Signature sig = Signature.getInstance("SHA1withDSA", "SUN");
        sig.initVerify(pubSavedFile);
        //
        FileInputStream datafis = new FileInputStream(args[0]);
        BufferedInputStream bufin2 = new BufferedInputStream(datafis);
        byte[] buffer2 = new byte[1024];
        int len2;
        while (bufin2.available() != 0) {
            len = bufin2.read(buffer2);
            sig.update(buffer2, 0, len);
        };
        bufin2.close();
        //
        boolean verifies = sig.verify(sigToVerify);
        System.out.println("signature verifies: " + verifies);
    }
}
