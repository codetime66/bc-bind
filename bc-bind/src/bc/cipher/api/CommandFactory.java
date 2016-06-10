package bc.cipher.api;

/**
 *
 * @author codetime
 */
public class CommandFactory {

    public static Command getInstance(String cipherImpl) throws Exception {
        Class c = Class.forName("bc.cipher." + cipherImpl);
        Command cmd = (Command) c.newInstance();
        return cmd;
    }
}
