/*
 * java -cp /home/codetime/projects/bc-bind/target/bc-1.0-SNAPSHOT.jar:/home/codetime/glassfish4/glassfish/modules/javax.json.jar bc.cipher.SignGen <ORIGINAL_MSG> <PRIVATE_KEY>
 *
 */
package bc.cipher;

import bc.cipher.api.ISignGen;
import java.io.BufferedReader;
import java.io.FileInputStream;
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
public class SignGen implements ISignGen {

    public static void main(String[] args) throws Exception {
        SignGen signGen = new SignGen();
        System.out.println( signGen.perform(args[0],args[1]) );
    }
    
    @Override
    public String perform(String s_message, String keyFileName) throws Exception {
        byte[] message = s_message.getBytes();
        String privK = readKeyFile(keyFileName);
        PrivateKey privSavedFile = loadPrivateKey(privK.toString());
        System.out.println(privSavedFile);
        //
        Signature rsa = Signature.getInstance("SHA1withRSA");
        rsa.initSign(privSavedFile);
        rsa.update(message);
        //
        byte[] realSig = rsa.sign();

        return Base64.getEncoder().encodeToString(realSig);
    }

    protected String readKeyFile(String fileName) throws Exception {
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

    protected PrivateKey loadPrivateKey(String key64) throws GeneralSecurityException {
        byte[] clear = base64Decode(key64);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(clear);
        KeyFactory fact = KeyFactory.getInstance("RSA");
        PrivateKey priv = fact.generatePrivate(keySpec);
        Arrays.fill(clear, (byte) 0);
        return priv;
    }

    protected byte[] base64Decode(String key64) {
        return Base64.getDecoder().decode(key64);
    }
}
