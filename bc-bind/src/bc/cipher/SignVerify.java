/*
 * java -cp /home/codetime/projects/bc-bind/target/bc-1.0-SNAPSHOT.jar:/home/codetime/glassfish4/glassfish/modules/javax.json.jar bc.cipher.SignVerify <SIGN_MSG> <ORIGINAL_MSG> <PARTICIPANT_SHORT_NAME>
 *
 */
package bc.cipher;

import bc.cipher.api.CipherFactory;
import bc.cipher.api.IKeyPairGen;
import bc.cipher.api.ISignGen;
import bc.cipher.api.ISignVerify;
import java.security.PublicKey;
import java.security.Signature;

/**
 *
 * @author codetime
 */
public class SignVerify implements ISignVerify {

    private IKeyPairGen keyPairGen;
    
    public SignVerify() throws Exception {
        keyPairGen = (IKeyPairGen) CipherFactory.getInstance("KeyPairGen");
    }
    
    @Override
    public boolean perform(String sigFile, String original, String pubK) throws Exception {
        byte[] b_sigFile = keyPairGen.base64Decode(sigFile);
        byte[] b_original = original.getBytes();
        PublicKey pubSavedFile = keyPairGen.loadPublicKey(pubK.toString());
        //
        Signature sig = Signature.getInstance("SHA1withRSA");
        sig.initVerify(pubSavedFile);
        sig.update(b_original);
        //        
        return sig.verify(b_sigFile);
    }

    @Override
    public IKeyPairGen getKeyPairGen(){
        return keyPairGen;
    }
    
    public static void main(String[] args) throws Exception {

        SignVerify signVerify = new SignVerify();

        String privFileName = "PRIVKEY" + new String(args[2]);
        String pubFileName = "PUBKEY" + new String(args[2]);
        String pubK = signVerify.getKeyPairGen().readKeyFile(pubFileName);        
        String original = signVerify.getKeyPairGen().readKeyFile(args[1]);
        //
        ISignGen signGen = (ISignGen) CipherFactory.getInstance("SignGen");
        String privK = signGen.getKeyPairGen().readKeyFile(privFileName);        
        String sigFile = signGen.perform(original, privK);
        System.out.println("signed file: " + sigFile);
        //
        System.out.println(signVerify.perform(sigFile, original, pubK));
    }
    
}
