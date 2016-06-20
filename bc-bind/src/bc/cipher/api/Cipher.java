package bc.cipher.api;

public interface Cipher {
    public String perform(byte[] message, String fileName) throws Exception;
}