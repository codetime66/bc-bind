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

    @Override
    public String perform(byte[] message) throws Exception {
        return hash256(message);
    }
    
    protected String hash256(byte[] data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(data);
        return bytesToHex(md.digest());
    }

    protected String bytesToHex(byte[] bytes) {
        StringBuffer result = new StringBuffer();
        for (byte byt : bytes) {
            result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
        }
        return result.toString();
    }

    @Override
    public String perform(byte[] message, byte[] hashFile, String keyFileName) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public String perform(byte[] message, String fileName) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
}