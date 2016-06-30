/**
 * java -cp /home/codetime/projects/bc-bind/target/bc-1.0-SNAPSHOT.jar:/home/codetime/glassfish4/glassfish/modules/javax.json.jar bc.cipher.Encrypt <MESSAGE> <PUBKEY>
 * 
 */
package bc.cipher;

import bc.cipher.api.IEncrypt;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 *
 * @author codetime
 */
public class Encrypt implements IEncrypt {

    public static void main(String[] args) throws Exception {
        Encrypt encrypt = new Encrypt();
        String message = encrypt.readKeyFile(args[0]);
        String keyFileName = args[1];
        String[] result = encrypt.perform(message, keyFileName);
        System.out.println("encryptedData=" + result[0] + ", encryptedkey="+result[1]);
    }

    @Override
    public String[] perform(String s_message, String keyFileName) throws Exception {
        byte[] message = s_message.getBytes();
        //Generate Symmetric key
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(128);
        SecretKey key = generator.generateKey();
        byte[] symmetricKey = key.getEncoded();

        //Generate private key public key pair
        String pubK = readKeyFile(keyFileName);
        PublicKey publicKey = loadPublicKey(pubK.toString());

        //Encrypt Data by symmetric key
        //String encryptedData = encryptWithAESKey("My Secured Message", symmetricKey);
        String encryptedData = encryptWithAESKey(message, symmetricKey);

        //Encrypt symmetric key by public key
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        String encryptedkey = base64Encode(cipher.doFinal(symmetricKey));
        
        return new String[]{encryptedData, encryptedkey};
    }

    protected String encryptWithAESKey(byte[] data, byte[] key) throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        SecretKey secKey = new SecretKeySpec(key, "AES");

        Cipher cipher = Cipher.getInstance("AES");

        cipher.init(Cipher.ENCRYPT_MODE, secKey);
        byte[] newData = cipher.doFinal(data);

        return base64Encode(newData);
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

    protected String base64Encode(byte[] key64) {
        return Base64.getEncoder().encodeToString(key64);
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
