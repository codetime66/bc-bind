/**
 * java -cp /home/codetime/projects/bc-bind/target/bc-1.0-SNAPSHOT.jar:/home/codetime/glassfish4/glassfish/modules/javax.json.jar bc.cipher.Decrypt <PRIVKEY> <encryptedData> <encryptedkey>
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

        String encryptedData = "0Op0qzuNwX//m+lqWf7Cn3TX7dvs/20p0W5+seSHuIcU4bBT4gggNcb7UF+AkNYK8INrr6+kbHv2yBpx7H8WSthqYZC0ZWTNH1SE9lQQBsfBYadlGeIaDc7IijF3L9yq0Nt0/NFqvBchIePPRa1uT9J26vtznoWKw5cSlHss/yExJxdOs7GwADX7i6ZSKJvf+sT2TUUJJCdxpfjvv1N1djU2bey8D2AcnQOmOXQJJkchSVuEGtPjTAgCc7bk7l1jcCp1gn0oJhG3h6nMjhTS9/2T1AKJW60vOkxkhSK6Klr/1lFez1V4EYjen5YzI3PHVAg0E8L66ryWwYTFHayS+OOlBfBwzm7fI9wE9WkJmVWtTeb90Jg2cr1nSe/7/OmeNKcA5aj8wsWmjuPWBc8uzEvGS8kDBJSwc1fFHCuQOEVEUb4dt74RTzDejeU+54si";
        String encryptedkey = "Z9JqXFk/A2kmVo9ZowqmOlHZ9yCbFOoq3vHq0qDbdM3V9K9vef4dIbc+Mr8Gb9ZLKCP5yfrUApBKi8tVJKnVyVxGB4KDaZX+mhftwg57tcVaR9MLuWKjQ62P25Pd/AE1KiQ0whP/uwk9LRAPgDpZ3xsUcxCT7Qsz/yC0U/8AS8k=";

        Decrypt decrypt = new Decrypt();
        String privKeyFileName = args[0];
        System.out.println(decrypt.perform(privKeyFileName, encryptedData, encryptedkey));
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
        String decryptedData = decryptWithAESKey(encryptedData, decryptedSymmetricKey);

        return decryptedData;
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
