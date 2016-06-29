/*

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
        Decrypt decrypt = new Decrypt();
        byte[] message = decrypt.readKeyFile(args[0]).getBytes();
        String keyFileName = args[1];
        decrypt.perform(message, keyFileName);
    }

    @Override
    public String perform(byte[] message, String keyFileName) throws Exception {

        String encryptedData = "r6zoBtMgXXpPPhVVKTR6eu0hq434cNlrx+1Z7qn8xRSTcfZKPHjELO0z+uQoYf8X6h6VNWQKIxT8GxiYtIMFyyPnaULZpmZBM5BS4qCloHVy22TLnYFYP7ME/9Lff8NsnDC3hnvEovW/NHIjJOh8DCS9FL29Zn8qk4O4t+nZk25Qxpkg5skH6nwUI0UozKu7MAYydEyw8Zqk/x60qUUGmtlbg1tIAOTgeuVLnz+TKtl/GTiBVlGSKpc8/j/xXjqNoPiHGrYBrKgj4iSKAUFn+MRFhlbYx0Qacmxo7MHaqp5Abq80eCSitHrKPW9MgHGdvL4gxNXz0+zRy2pyagGJfmKIn6BPakhiOYe5U8pXmAa1mbLMccEXN23Dh5zx0SpZMJz0q4PbcppQs45cl+1/x+4Y+HNlyFuJgYuEdPDPT8H0gyqZQGS2mEeiz0akTc09";
        String encryptedkey = "c5af+BztqaelqnK52+0szYq6qRsOhJjLC1ekFWcs9lGLWH8cUz+btRjtEoxe1+4w1RNsdX2GEJZ2ouqVE47QZd5uYgcg9Ed2ToMFmOWf+Fb1/g5eLB20403uWDxvgVPFCEsWx2oI4a1W2kbOr8tJqB4OD+Tam2KWRnDw4yLAdJI=";

        String privK = readKeyFile(keyFileName);
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

    @Override
    public String perform(byte[] message) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String perform(byte[] message, byte[] hashFile, String keyFileName) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
