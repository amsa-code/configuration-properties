package au.gov.amsa.configuration.properties;

public final class ValueNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 4874623459504005980L;
    
    private final String key;

    public ValueNotFoundException(String key) {
        super("Value not found for key=" + key);
        this.key = key;
    }
    
    public String key() {
        return key;
    }
    
}
