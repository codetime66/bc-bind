package bc.cipher.api;

public interface Cipher {
    public String perform(byte[] message) throws Exception;
}