package au.gov.amsa.configuration.properties;

import com.github.davidmoten.security.PPK;

public final class Decrypter {

    private final PrivateKeyProvider privateKey;

    public Decrypter(PrivateKeyProvider privateKey) {
        this.privateKey = privateKey;
    }

    public String decrypt(String string) {
        return PPK.privateKey(privateKey.getInputStream()).decryptBase64(string);
    }

}
