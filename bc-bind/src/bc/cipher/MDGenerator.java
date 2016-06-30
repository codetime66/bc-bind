/*
 * java -cp /home/codetime/projects/bc-bind/target/bc-1.0-SNAPSHOT.jar:/home/codetime/glassfish4/glassfish/modules/javax.json.jar bc.cipher.MDGenerator <ORIGINAL_MSG>
 *
 */
package bc.cipher;

import bc.cipher.api.IMDGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author codetime
 */
public class MDGenerator implements IMDGenerator {

    public static void main(String[] args) throws Exception {
        MDGenerator md = new MDGenerator();
        System.out.println( md.perform(args[0]) );
    }
    
    @Override
    public String perform(String s_message) throws Exception {
        byte[] message = s_message.getBytes();
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
}