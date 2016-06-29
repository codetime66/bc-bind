/**
 *  java -cp /home/codetime/projects/bc-bind/target/bc-1.0-SNAPSHOT.jar:/home/codetime/glassfish4/glassfish/modules/javax.json.jar bc.cipher.Decrypt <PRIVKEY> <encryptedData> <encryptedkey>
 *
 */
package bc.cipher;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import java.util.Base64;

/**
 *
 * @author codetime
 */
public class Decrypt implements bc.cipher.api.Cipher {

    public static void main(String[] args) throws Exception {

        String encryptedData = "uRNxQ5somvLHrR5KGjK47XklfCKxdut0aPc8lgGwGNBpBct56XtIp35wXqu6YAdMstwNq1zxcefHCdSKFcFQaXs8zdVqhOptIgf7hvlDFYv3GQ8Whi3mtgEzPE3zVUTHJDj3b0C1PP2k7Pq71Ot5Jkzt5VBhcpPOzvvtnbYH1FpSL1ddz6YOr/5CKCM2K+gNFGuk37NTiA9yofL/gcazO70/j/DZ3n4/FJLbvnPFdJZWAz/KoVz0HldeCa98LUerWMVJCu815+MgQW34Ww4nTAP2Npiv0eRxpb+qLHwdaMsEMoQ29Jdodtbw18eRQSjHi8nJIcESvbTVmeeTWejNfA+r6MumzB/wr1h20x/SXxyyq2LgHuujKAaaXXSzoXW3tuGmKipxABshmuWWFS7b27CBo6FDr6JMlUvBgsi677c3Hyoz8rdc5H2K2C9yxuX1";
        String encryptedkey = "cf4HOfTlWykmj58k+6SarbhxUI4m9lDURqukgkA5Ka17h24lO2ipK73d8KtAXVBcXSjSfiC4mm3Vds0w96PUS2h1OM73972SPlNu6oaJ+VxLmOpomE49W+OS2yt5E/1gfS4MKYxUnzoS2Xmn8DU/n+KtcZUK0V1kERWh8Zlj+34=";
        
        Decrypt decrypt = new Decrypt();
        String privKeyFileName = args[0];
        decrypt.perform( new String[]{privKeyFileName, encryptedData, encryptedkey} );
    }

    @Override
    public String perform(String[] keys) throws Exception {

        String privKeyFileName = keys[0];        
        
        String encryptedData = keys[1];
        String encryptedkey = keys[2];

        String privK = readKeyFile(privKeyFileName);
        PrivateKey privateKey = loadPrivateKey(privK.toString());
        System.out.println(privateKey);

        //Send message and key to other user having private key
        //Decrypt symmetric Key by private key
        Cipher dipher = Cipher.getInstance("RSA");

        dipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedSymmetricKey = dipher.doFinal(base64Decode(encryptedkey));

        //Decrypt encrypted Data by decrypted symmetric key
        System.out.println("Decrypted Data : " + decryptWithAESKey(encryptedData, decryptedSymmetricKey));

        return null;
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

    protected byte[] base64Encode(byte[] key64) {
        return Base64.getDecoder().decode(key64);
    }
    
    protected String decryptWithAESKey(String inputData, byte[] key) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("AES");
        SecretKey secKey = new SecretKeySpec(key, "AES");

        cipher.init(Cipher.DECRYPT_MODE, secKey);

        byte[] newData = cipher.doFinal(base64Encode(inputData.getBytes()));
        return new String(newData);

    }
}
