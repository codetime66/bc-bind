/**
 *  java -cp /home/codetime/projects/bc-bind/target/bc-1.0-SNAPSHOT.jar:/home/codetime/glassfish4/glassfish/modules/javax.json.jar bc.cipher.Decrypt <PRIVKEY> <encryptedData> <encryptedkey>
 *
 */
package bc.cipher;

import bc.cipher.api.IDecrypt;
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
public class Decrypt implements IDecrypt {

    public static void main(String[] args) throws Exception {
        
        String encryptedData = "c2ApEqvITmTVeeDdxGmjXIuK7wDFpeibQhNVinnyPVJ1IfWhxu5YYvGsrGdyjn3kd3G21HOwce0ozSvkBfH3mqobinqJSIyz2SzSF5YBzbwS4rO8fy2KIYcxeXoGRXSObFcDhRtzhIuKjcZc5a96ZLMEH9W7FvBg0s6YAlGRlZPTXvIkunb+66cwzqYWelYdHmnr1urA+64uReesHqiJvrhZmzB6/B1kAbshQVYtj3M1jbPIojPmamMGvseFdnCP5e9eO32GOQ+yX9JBAxmGAA3KX7YGcCx/IAphf3FcM/4Z1fV3vq0Ij5+KEtNuPwb5WzvQwMty+nLc4o8eCi5GNAcwGEYY81I4tI4I6I1Tk7TeSgrKOaP2QrBlQ81eDXytnguUOZ1AxwSUbGUtaPjrCG2+m+j4NOaUJdA2lUIUhA7OADYaN0jCoQFneg9wEkKx";
        String encryptedkey = "WXprFHCHCpqXJfCOr2aLsosqlhRjYClzNHmUdsrKgNPqNGitKF4JCvxfwyhRXRwupjAdwj+35EOy6BSu1yx1TW0DDfAxhjZF0e6cqMuFJ+ysV6DSFR6xDDLBi/ziHF/2spkRWDhQQdl6pawURfU7ZYnO0F8wAUDflBxpSdfP62o=";
        
        Decrypt decrypt = new Decrypt();
        String privKeyFileName = args[0];
        decrypt.perform(privKeyFileName, encryptedData, encryptedkey);
    }

    @Override
    public String perform(String privKeyFileName, String encryptedData, String encryptedkey) throws Exception {

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
