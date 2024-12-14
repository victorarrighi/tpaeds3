
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;

    // Classe para implementar a compressão e descompressão Huffman
    class HuffmanCompression {
        // Classe interna para representar os nós da árvore Huffman
        private class HuffmanNode implements Comparable<HuffmanNode> {
            int frequency;
            byte data;
            HuffmanNode left, right;

            public HuffmanNode(byte data, int frequency) {
                this.data = data;
                this.frequency = frequency;
                left = right = null;
            }

            @Override
            public int compareTo(HuffmanNode node) {
                return this.frequency - node.frequency;
            }
        }

        // Método principal para comprimir dados
        public byte[] compress(byte[] input) {
            long startTime = System.currentTimeMillis();

            // Calcular frequências
            int[] frequencies = new int[256];
            for (byte b : input) {
                frequencies[b & 0xFF]++;
            }

            // Construir árvore Huffman
            HuffmanNode root = buildHuffmanTree(frequencies);

            // Gerar códigos Huffman
            String[] huffmanCodes = new String[256];
            generateCodes(root, "", huffmanCodes);

            // Comprimir dados
            StringBuilder compressed = new StringBuilder();
            for (byte b : input) {
                compressed.append(huffmanCodes[b & 0xFF]);
            }

            // Converter string binária para bytes
            byte[] compressedData = convertBinaryStringToBytes(compressed.toString());

            // Adicionar cabeçalho com frequências para descompressão
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            try {
                // Salvar frequências no cabeçalho
                for (int freq : frequencies) {
                    outputStream.write((freq >> 24) & 0xFF);
                    outputStream.write((freq >> 16) & 0xFF);
                    outputStream.write((freq >> 8) & 0xFF);
                    outputStream.write(freq & 0xFF);
                }
                outputStream.write(compressedData);
            } catch (IOException e) {
                e.printStackTrace();
            }

            long endTime = System.currentTimeMillis();
            System.out.println("Tempo de compressão Huffman: " + (endTime - startTime) + "ms");

            return outputStream.toByteArray();
        }

        // Método para descomprimir dados
        public byte[] decompress(byte[] input) {
            long startTime = System.currentTimeMillis();

            // Extrair frequências do cabeçalho
            int[] frequencies = new int[256];
            int headerSize = 256 * 4; // 4 bytes por frequência

            for (int i = 0; i < 256; i++) {
                frequencies[i] = ((input[i*4] & 0xFF) << 24) |
                        ((input[i*4+1] & 0xFF) << 16) |
                        ((input[i*4+2] & 0xFF) << 8) |
                        (input[i*4+3] & 0xFF);
            }

            // Reconstruir árvore Huffman
            HuffmanNode root = buildHuffmanTree(frequencies);

            // Converter bytes comprimidos para string binária
            StringBuilder binary = new StringBuilder();
            for (int i = headerSize; i < input.length; i++) {
                String bits = String.format("%8s", Integer.toBinaryString(input[i] & 0xFF))
                        .replace(' ', '0');
                binary.append(bits);
            }

            // Descomprimir dados usando a árvore
            ArrayList<Byte> decompressed = new ArrayList<>();
            HuffmanNode current = root;
            for (char bit : binary.toString().toCharArray()) {
                if (bit == '0') {
                    current = current.left;
                } else {
                    current = current.right;
                }

                if (current.left == null && current.right == null) {
                    decompressed.add(current.data);
                    current = root;
                }
            }

            // Converter ArrayList para array de bytes
            byte[] result = new byte[decompressed.size()];
            for (int i = 0; i < decompressed.size(); i++) {
                result[i] = decompressed.get(i);
            }

            long endTime = System.currentTimeMillis();
            System.out.println("Tempo de descompressão Huffman: " + (endTime - startTime) + "ms");

            return result;
        }

        // Métodos auxiliares
        private HuffmanNode buildHuffmanTree(int[] frequencies) {
            PriorityQueue<HuffmanNode> queue = new PriorityQueue<>();

            // Criar nós folha para cada caractere
            for (int i = 0; i < frequencies.length; i++) {
                if (frequencies[i] > 0) {
                    queue.offer(new HuffmanNode((byte)i, frequencies[i]));
                }
            }

            // Construir árvore
            while (queue.size() > 1) {
                HuffmanNode left = queue.poll();
                HuffmanNode right = queue.poll();

                HuffmanNode parent = new HuffmanNode((byte)0, left.frequency + right.frequency);
                parent.left = left;
                parent.right = right;

                queue.offer(parent);
            }

            return queue.poll();
        }

        private void generateCodes(HuffmanNode root, String code, String[] codes) {
            if (root == null) return;

            if (root.left == null && root.right == null) {
                codes[root.data & 0xFF] = code;
            }

            generateCodes(root.left, code + "0", codes);
            generateCodes(root.right, code + "1", codes);
        }

        private byte[] convertBinaryStringToBytes(String binary) {
            // Adicionar padding se necessário
            int padding = 8 - (binary.length() % 8);
            if (padding < 8) {
                binary = binary + "0".repeat(padding);
            }

            // Converter para bytes
            byte[] bytes = new byte[binary.length() / 8];
            for (int i = 0; i < bytes.length; i++) {
                String byteStr = binary.substring(i * 8, (i + 1) * 8);
                bytes[i] = (byte) Integer.parseInt(byteStr, 2);
            }

            return bytes;
        }
    }
