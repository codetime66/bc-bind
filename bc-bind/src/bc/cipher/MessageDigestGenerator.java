package bc.cipher;

import bc.cipher.api.Command;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author codetime
 */
public class MessageDigestGenerator implements Command {

    public void execute(byte[] message) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("SHA");

        md.update(message);

        byte[] hash = md.digest();

        System.out.println("Hash:");
        System.out.println(new String(hash));

    }

}