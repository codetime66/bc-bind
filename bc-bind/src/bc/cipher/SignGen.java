/*
 *
 */
package bc.cipher;

import bc.cipher.api.Cipher;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;

/**
 *
 * @author codetime
 */
public class SignGen implements Cipher {

    @Override
    public String perform(byte[] message, String keyFileName) throws Exception {
        String privK = readKeyFile(keyFileName);
        PrivateKey privSavedFile = loadPrivateKey(privK.toString());
        System.out.println(privSavedFile);
        //
        Signature dsa = Signature.getInstance("SHA1withDSA", "SUN");
        dsa.initSign(privSavedFile);
        dsa.update(message);
        //
        byte[] realSig = dsa.sign();
        /* save the signature in a file */
        FileOutputStream sigfos = new FileOutputStream("sig");
        sigfos.write( Base64.getEncoder().encode(realSig) );
        sigfos.close();
        //
        return Base64.getEncoder().encodeToString(realSig);
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

    public static PrivateKey loadPrivateKey(String key64) throws GeneralSecurityException {
        byte[] clear = base64Decode(key64);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(clear);
        KeyFactory fact = KeyFactory.getInstance("DSA", "SUN");
        PrivateKey priv = fact.generatePrivate(keySpec);
        Arrays.fill(clear, (byte) 0);
        return priv;
    }

    private static byte[] base64Decode(String key64) {
        return Base64.getDecoder().decode(key64);
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
