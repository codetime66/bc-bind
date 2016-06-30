package bc.cipher.api;

/**
 *
 * @author codetime
 */
public class CipherFactory {

    public static Object getInstance(String cipherImpl) throws Exception {
        Class c = Class.forName("bc.cipher." + cipherImpl);
        Object cipher = (Object) c.newInstance();
        return cipher;
    }
}
