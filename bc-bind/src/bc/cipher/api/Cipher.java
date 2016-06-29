package bc.cipher.api;

public interface Cipher {
    public default String perform(byte[] message) throws Exception{
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public default String perform(byte[] message, String fileName) throws Exception{
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public default String perform(byte[] message, byte[] hashFile, String keyFileName) throws Exception{
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public default String perform(String[] args) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }    
}