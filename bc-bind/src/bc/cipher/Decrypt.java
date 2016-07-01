/**
 * java -cp /home/codetime/projects/bc-bind/target/bc-1.0-SNAPSHOT.jar:/home/codetime/glassfish4/glassfish/modules/javax.json.jar bc.cipher.Decrypt <MESSAGE> <PUBKEY> <PRIVKEY>
 *
 */
package bc.cipher;

import bc.cipher.api.CipherFactory;
import bc.cipher.api.IDecrypt;
import bc.cipher.api.IKeyPairGen;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author codetime
 */
public class Decrypt implements IDecrypt {

    private IKeyPairGen keyPairGen;
    
    public Decrypt() throws Exception {
        keyPairGen = (IKeyPairGen) CipherFactory.getInstance("KeyPairGen");
    }

    @Override
    public String perform(String privK, String encryptedData, String encryptedkey) throws Exception {

        PrivateKey privateKey = keyPairGen.loadPrivateKey(privK.toString());

        //Send message and key to other user having private key
        //Decrypt symmetric Key by private key
        Cipher dipher = Cipher.getInstance("RSA");

        dipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedSymmetricKey = dipher.doFinal(keyPairGen.base64Decode(encryptedkey));

        //Decrypt encrypted Data by decrypted symmetric key
        String decryptedData = decryptWithAESKey(encryptedData, decryptedSymmetricKey);

        return decryptedData;
    }
    
    @Override
    public IKeyPairGen getKeyPairGen(){
        return keyPairGen;
    }
    
    protected String decryptWithAESKey(String inputData, byte[] key) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("AES");
        SecretKey secKey = new SecretKeySpec(key, "AES");

        cipher.init(Cipher.DECRYPT_MODE, secKey);

        byte[] newData = cipher.doFinal(keyPairGen.base64Decode(inputData.getBytes()));
        return new String(newData);

    }

    public static void main(String[] args) throws Exception {

        Encrypt encrypt = new Encrypt();
        String message = encrypt.getKeyPairGen().readKeyFile(args[0]);
        String keyFileName = args[1];
        String pubK = encrypt.getKeyPairGen().readKeyFile(keyFileName);                
        String[] result = encrypt.perform(message, pubK);
        System.out.println("encryptedData=" + result[0] + ", encryptedkey="+result[1]);        
        
        String encryptedData = result[0];
        String encryptedkey = result[1];
        
        Decrypt decrypt = new Decrypt();
        String privK = decrypt.getKeyPairGen().readKeyFile(args[2]);        
        System.out.println(decrypt.perform(privK, encryptedData, encryptedkey));
    }
    
}
