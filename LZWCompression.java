import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Classe para implementar a compressão e descompressão LZW
class LZWCompression {
    private static final int DICT_SIZE = 256;
    private static final int MAX_DICT_SIZE = 4096; // Tamanho máximo do dicionário

    public byte[] compress(byte[] input) {
        long startTime = System.currentTimeMillis();

        // Inicializar dicionário
        Map<String, Integer> dictionary = new HashMap<>();
        for (int i = 0; i < DICT_SIZE; i++) {
            dictionary.put(String.valueOf((char)i), i);
        }

        String current = "";
        List<Integer> result = new ArrayList<>();
        int dictSize = DICT_SIZE;

        // Processo de compressão
        for (byte b : input) {
            String next = current + (char)(b & 0xFF);

            if (dictionary.containsKey(next)) {
                current = next;
            } else {
                result.add(dictionary.get(current));

                if (dictSize < MAX_DICT_SIZE) {
                    dictionary.put(next, dictSize++);
                }
                current = String.valueOf((char)(b & 0xFF));
            }
        }

        if (!current.isEmpty()) {
            result.add(dictionary.get(current));
        }

        // Converter para bytes
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        DataOutputStream dataOut = new DataOutputStream(outputStream);

        try {
            for (int code : result) {
                dataOut.writeShort(code);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Tempo de compressão LZW: " + (endTime - startTime) + "ms");

        return outputStream.toByteArray();
    }

    public byte[] decompress(byte[] input) {
        long startTime = System.currentTimeMillis();

        // Inicializar dicionário
        Map<Integer, String> dictionary = new HashMap<>();
        for (int i = 0; i < DICT_SIZE; i++) {
            dictionary.put(i, String.valueOf((char)i));
        }

        DataInputStream dataIn = new DataInputStream(new ByteArrayInputStream(input));
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try {
            int previousCode = dataIn.readShort();
            String previous = dictionary.get(previousCode);
            output.write(previous.getBytes());

            int dictSize = DICT_SIZE;

            while (dataIn.available() > 0) {
                int currentCode = dataIn.readShort();
                String current;

                if (dictionary.containsKey(currentCode)) {
                    current = dictionary.get(currentCode);
                } else if (currentCode == dictSize) {
                    current = previous + previous.charAt(0);
                } else {
                    throw new IllegalStateException("Dados corrompidos");
                }

                output.write(current.getBytes());

                if (dictSize < MAX_DICT_SIZE) {
                    dictionary.put(dictSize++, previous + current.charAt(0));
                }

                previous = current;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Tempo de descompressão LZW: " + (endTime - startTime) + "ms");

        return output.toByteArray();
    }
}