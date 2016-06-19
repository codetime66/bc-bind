package bc.cipher.api;

/**
 *
 * @author codetime
 */
public class CipherFactory {

    public static Cipher getInstance(String cipherImpl) throws Exception {
        Class c = Class.forName("bc.cipher." + cipherImpl);
        Cipher cipher = (Cipher) c.newInstance();
        return cipher;
    }
}
