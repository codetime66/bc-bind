package bc.cipher.api;

public interface Cipher {
    public String perform(byte[] message) throws Exception;
    public String perform(byte[] message, String fileName) throws Exception;
    public String perform(byte[] message, byte[] hashFile, String keyFileName) throws Exception;
}