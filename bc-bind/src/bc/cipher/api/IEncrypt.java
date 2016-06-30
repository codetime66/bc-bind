package bc.cipher.api;

public interface IEncrypt {
    public String[] perform(String s_message, String keyFileName) throws Exception;
}