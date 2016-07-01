/**
 * java -cp /home/codetime/projects/bc-bind/target/bc-1.0-SNAPSHOT.jar:/home/codetime/glassfish4/glassfish/modules/javax.json.jar bc.cipher.Encrypt <MESSAGE> <PUBKEY>
 * 
 */
package bc.cipher;

import bc.cipher.api.CipherFactory;
import bc.cipher.api.IEncrypt;
import bc.cipher.api.IKeyPairGen;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author codetime
 */
public class Encrypt implements IEncrypt {

    private IKeyPairGen keyPairGen;
    
    public Encrypt() throws Exception {
        keyPairGen = (IKeyPairGen) CipherFactory.getInstance("KeyPairGen");
    }
    
    @Override
    public String[] perform(String s_message, String pubK) throws Exception {
        byte[] message = s_message.getBytes();
        //Generate Symmetric key
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(128);
        SecretKey key = generator.generateKey();
        byte[] symmetricKey = key.getEncoded();

        //Generate private key public key pair
        PublicKey publicKey = keyPairGen.loadPublicKey(pubK.toString());

        //Encrypt Data by symmetric key
        String encryptedData = encryptWithAESKey(message, symmetricKey);
        
        //Encrypt symmetric key by public key
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        String encryptedkey = keyPairGen.base64Encode(cipher.doFinal(symmetricKey));
        
        return new String[]{encryptedData, encryptedkey};
    }

    protected String encryptWithAESKey(byte[] data, byte[] key) throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        SecretKey secKey = new SecretKeySpec(key, "AES");

        Cipher cipher = Cipher.getInstance("AES");

        cipher.init(Cipher.ENCRYPT_MODE, secKey);
        byte[] newData = cipher.doFinal(data);

        return keyPairGen.base64Encode(newData);
    }

    @Override
    public IKeyPairGen getKeyPairGen(){
        return keyPairGen;
    }
    
    public static void main(String[] args) throws Exception {
        Encrypt encrypt = new Encrypt();
        String message = encrypt.getKeyPairGen().readKeyFile(args[0]);
        String keyFileName = args[1];
        String pubK = encrypt.getKeyPairGen().readKeyFile(keyFileName);                
        String[] result = encrypt.perform(message, pubK);
        System.out.println("encryptedData=" + result[0] + ", encryptedkey="+result[1]);
    }
    
}
