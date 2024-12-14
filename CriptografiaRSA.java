import javax.crypto.Cipher;
import java.io.*;
import java.security.*;
import java.util.Base64;

public class CriptografiaRSA {

    private KeyPair keyPair;

    public CriptografiaRSA() throws NoSuchAlgorithmException {
        // Gera um par de chaves RSA
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        this.keyPair = keyGen.generateKeyPair();
    }

    public String encrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
        byte[] encryptedData = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    public String decrypt(String encryptedData) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
        byte[] decodedData = Base64.getDecoder().decode(encryptedData);
        return new String(cipher.doFinal(decodedData));
    }

    public void encryptFile(String filePath, String outputPath) throws Exception {
        File inputFile = new File(filePath);
        File outputFile = new File(outputPath);

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(encrypt(line));
                writer.newLine();
            }
        }
    }

    public void decryptFile(String filePath, String outputPath) throws Exception {
        File inputFile = new File(filePath);
        File outputFile = new File(outputPath);

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(decrypt(line));
                writer.newLine();
            }
        }
    }
}
