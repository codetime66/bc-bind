/*
 * java -cp /home/codetime/projects/bc-bind/target/bc-1.0-SNAPSHOT.jar:/home/codetime/glassfish4/glassfish/modules/javax.json.jar bc.cipher.SignGen <ORIGINAL_MSG> <PRIVATE_KEY>
 *
 */
package bc.cipher;

import bc.cipher.api.CipherFactory;
import bc.cipher.api.IKeyPairGen;
import bc.cipher.api.ISignGen;
import java.security.PrivateKey;
import java.security.Signature;

/**
 *
 * @author codetime
 */
public class SignGen implements ISignGen {

    private IKeyPairGen keyPairGen;
    
    public SignGen() throws Exception {
        keyPairGen = (IKeyPairGen) CipherFactory.getInstance("KeyPairGen");
    }
    
    @Override
    public String perform(String s_message, String privK) throws Exception {
        byte[] message = s_message.getBytes();
        PrivateKey privSavedFile = keyPairGen.loadPrivateKey(privK.toString());
        System.out.println(privSavedFile);
        //
        Signature rsa = Signature.getInstance("SHA1withRSA");
        rsa.initSign(privSavedFile);
        rsa.update(message);
        //
        byte[] realSig = rsa.sign();

        return keyPairGen.base64Encode(realSig);
    }

    @Override
    public IKeyPairGen getKeyPairGen(){
        return keyPairGen;
    }
    
    public static void main(String[] args) throws Exception {
        SignGen signGen = new SignGen();
        String privK = signGen.getKeyPairGen().readKeyFile(args[1]);                
        System.out.println( signGen.perform(args[0],privK) );
    }
    
}
