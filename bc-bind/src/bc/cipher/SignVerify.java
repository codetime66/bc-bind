/*
 * java -cp /home/codetime/projects/bc-bind/target/bc-1.0-SNAPSHOT.jar:/home/codetime/glassfish4/glassfish/modules/javax.json.jar bc.cipher.SignVerify <SIGN_MSG> <ORIGINAL_MSG> <PARTICIPANT_SHORT_NAME>
 *
 */
package bc.cipher;

import bc.cipher.api.CipherFactory;
import bc.cipher.api.ISignGen;
import bc.cipher.api.ISignVerify;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 *
 * @author codetime
 */
public class SignVerify implements ISignVerify {

    public static void main(String[] args) throws Exception {

        SignVerify signVerify = new SignVerify();

        String privFileName = "PRIVKEY" + new String(args[2]);
        String pubFileName = "PUBKEY" + new String(args[2]);
        String original = signVerify.readKeyFile(args[1]);
        //
        ISignGen signGen = (ISignGen) CipherFactory.getInstance("SignGen");
        String sigFile = signGen.perform(original, privFileName);
        System.out.println("signed file: " + sigFile);
        //
        System.out.println(signVerify.perform(sigFile, original, pubFileName));
    }

    @Override
    public String perform(String sigFile, String original, String pubFileName) throws Exception {
        byte[] b_sigFile = base64Decode(sigFile);
        byte[] b_original = original.getBytes();
        String keyFileName = pubFileName;
        String pubK = readKeyFile(keyFileName);
        PublicKey pubSavedFile = loadPublicKey(pubK.toString());
        System.out.println(pubSavedFile);
        //
        Signature sig = Signature.getInstance("SHA1withRSA");
        sig.initVerify(pubSavedFile);
        System.out.println("file to verify:" + new String(b_original));
        sig.update(b_original);
        //        
        boolean verifies = sig.verify(b_sigFile);
        System.out.println("signature verifies: " + verifies);

        return "signature verifies: " + verifies;
    }

    protected PublicKey loadPublicKey(String stored) throws GeneralSecurityException {
        byte[] data = base64Decode(stored);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(data);
        KeyFactory fact = KeyFactory.getInstance("RSA");
        return fact.generatePublic(spec);
    }

    protected byte[] base64Decode(String key64) {
        return Base64.getDecoder().decode(key64);
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
}
