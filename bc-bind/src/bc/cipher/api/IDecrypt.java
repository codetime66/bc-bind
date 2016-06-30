package bc.cipher.api;

public interface IDecrypt {
    public String perform(String privKeyFileName, String encryptedData, String encryptedkey) throws Exception;
}