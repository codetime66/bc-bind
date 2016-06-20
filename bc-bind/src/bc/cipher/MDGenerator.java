/*

 */
package bc.cipher;

import bc.cipher.api.Cipher;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author codetime
 */
public class MDGenerator implements Cipher {

    public String perform(byte[] message, String fileName) throws Exception {
        return hash256(message);
    }
    
    public String hash256(byte[] data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(data);
        return bytesToHex(md.digest());
    }

    public String bytesToHex(byte[] bytes) {
        StringBuffer result = new StringBuffer();
        for (byte byt : bytes) {
            result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
        }
        return result.toString();
    }
}