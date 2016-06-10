package bc.cipher.api;

public interface Command {
    void execute(byte[] message) throws Exception;
}