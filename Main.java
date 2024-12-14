import java.io.*;
import java.util.Scanner;
import java.nio.file.*;
import java.util.List;


public class Main {
    static final String CSV_PATH = "src/resources/nbaplayersdraft.csv";
    static final String DB_PATH = "src/resources/jogadores.db";

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        var allPlayers = NBAPlayerAA.readCSV(CSV_PATH);
        RandomAccessFile raf = new RandomAccessFile(DB_PATH, "rw");
        HeapFile heapFile = new HeapFile();
        heapFile.saveAll(allPlayers);

        HuffmanCompression huffman = new HuffmanCompression();
        LZWCompression lzw = new LZWCompression();
        PatternMatching patternMatcher = new PatternMatching();

        System.out.println("Menu:\n1- Ler Registro\n2- Deletar Registro\n3- Atualizar Registro" +
                "\n4- Comprimir Arquivo\n5- Descomprimir Arquivo" +
                "\n6- Buscar Padrão\n7- Finalizar\nDigite o número correspondente a sua escolha: ");

        int opcaoEscolhida = scanner.nextInt();

        while (opcaoEscolhida != 8) {
            switch (opcaoEscolhida) {
                case 1:
                    // Código existente para ler registro
                    System.out.println("\nDigite a ID: ");
                    int idBuscada = scanner.nextInt();
                    System.out.println(heapFile.findByID(idBuscada));
                    break;

                case 2:
                    // Código existente para deletar registro
                    System.out.println("\nDigite a ID: ");
                    int idDeletada = scanner.nextInt();
                    boolean deleted = heapFile.deleteByID(idDeletada);
                    System.out.println(deleted ? "Jogador excluído com sucesso." : "Jogador não encontrado.");
                    break;

                case 3:
                    System.out.println("\nDigite a ID do jogador que deseja atualizar: ");
                    int idAtualizado = scanner.nextInt();
                    NBAPlayerAA jogadorAtualizado = new NBAPlayerAA();

                    // Coleta os novos valores do jogador
                    jogadorAtualizado.setID(idAtualizado);  // mantém o ID

                    System.out.println("Digite o novo ano: ");
                    jogadorAtualizado.setYear(scanner.nextInt());

                    System.out.println("Digite o novo rank: ");
                    jogadorAtualizado.setRank(scanner.nextInt());

                    System.out.println("Digite o novo overall pick: ");
                    jogadorAtualizado.setOverallPick(scanner.nextInt());

                    System.out.println("Digite o novo time: ");
                    String team;
                    boolean validTeam = false;
                    while (!validTeam) {
                        team = scanner.next();
                        try {
                            jogadorAtualizado.setTeam(team);
                            validTeam = true;  // Se não lançar exceção, o time é válido
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage() + " Tente novamente.");
                        }
                    }

                    System.out.println("Digite o nome do jogador: ");
                    jogadorAtualizado.setPlayer(scanner.next());

                    System.out.println("Digite a nova faculdade: ");
                    jogadorAtualizado.setCollege(scanner.next());

                    System.out.println("Digite os novos anos ativos: ");
                    jogadorAtualizado.setYearsActive(scanner.nextInt());

                    System.out.println("Digite os novos jogos: ");
                    jogadorAtualizado.setGames(scanner.nextInt());

                    System.out.println("Digite os novos minutos jogados: ");
                    jogadorAtualizado.setMinutesPlayed(scanner.nextInt());

                    System.out.println("Digite os novos pontos: ");
                    jogadorAtualizado.setPoints(scanner.nextInt());

                    System.out.println("Digite os novos rebotes totais: ");
                    jogadorAtualizado.setTotalRebounds(scanner.nextInt());

                    System.out.println("Digite as novas assistências: ");
                    jogadorAtualizado.setAssists(scanner.nextInt());

                    System.out.println("Digite o novo percentual de arremessos de quadra: ");
                    jogadorAtualizado.setFieldGoalPercentage(scanner.nextDouble());

                    System.out.println("Digite o novo percentual de arremessos de 3 pontos: ");
                    jogadorAtualizado.setThreePointPercentage(scanner.nextDouble());

                    System.out.println("Digite o novo percentual de lances livres: ");
                    jogadorAtualizado.setFreeThrowPercentage(scanner.nextDouble());

                    System.out.println("Digite a nova média de minutos jogados: ");
                    jogadorAtualizado.setAverageMinutesPlayed(scanner.nextDouble());

                    System.out.println("Digite os novos pontos por jogo: ");
                    jogadorAtualizado.setPointsPerGame(scanner.nextDouble());

                    System.out.println("Digite a nova média de rebotes totais: ");
                    jogadorAtualizado.setAverageTotalRebounds(scanner.nextDouble());

                    System.out.println("Digite a nova média de assistências: ");
                    jogadorAtualizado.setAverageAssists(scanner.nextDouble());

                    System.out.println("Digite o novo win shares: ");
                    jogadorAtualizado.setWinShares(scanner.nextDouble());

                    System.out.println("Digite o novo win shares por 48 minutos: ");
                    jogadorAtualizado.setWinSharesPer48Minutes(scanner.nextDouble());

                    System.out.println("Digite o novo Box Plus Minus: ");
                    jogadorAtualizado.setBoxPlusMinus(scanner.nextDouble());

                    System.out.println("Digite o novo valor de Value Over Replacement: ");
                    jogadorAtualizado.setValueOverReplacement(scanner.nextDouble());

                    // Atualiza o jogador
                    heapFile.updatePlayer(jogadorAtualizado);
                    System.out.println("Registro atualizado com sucesso!");
                    break;

                case 4:
                    // Compression option with proper byte array handling
                    System.out.println("\nRealizando compressão do arquivo...");

                    // Read the original file as bytes
                    byte[] originalData = Files.readAllBytes(Paths.get(DB_PATH));
                    long originalSize = originalData.length;

                    // Huffman compression
                    byte[] huffmanCompressed = huffman.compress(originalData);
                    String huffmanFileName = "src/resources/jogadoresHuffmanCompressao1";
                    Files.write(Paths.get(huffmanFileName), huffmanCompressed);

                    // LZW compression
                    byte[] lzwCompressed = lzw.compress(originalData);
                    String lzwFileName = "src/resources/jogadoresLZWCompressao1";
                    Files.write(Paths.get(lzwFileName), lzwCompressed);

                    // Calculate and show statistics
                    double huffmanRatio = 100.0 * (originalSize - huffmanCompressed.length) / originalSize;
                    double lzwRatio = 100.0 * (originalSize - lzwCompressed.length) / originalSize;

                    System.out.println("\nEstatísticas de compressão:");
                    System.out.printf("Tamanho original: %d bytes%n", originalSize);
                    System.out.printf("Huffman: %d bytes (%.2f%% de redução)%n",
                            huffmanCompressed.length, huffmanRatio);
                    System.out.printf("LZW: %d bytes (%.2f%% de redução)%n",
                            lzwCompressed.length, lzwRatio);

                    System.out.println("\nMelhor algoritmo: " +
                            (huffmanRatio > lzwRatio ? "Huffman" : "LZW"));
                    break;

                case 5:
                    // Decompression option with proper byte array handling
                    System.out.println("\nEscolha o algoritmo de descompressão:");
                    System.out.println("1- Huffman\n2- LZW");
                    int algoritmo = scanner.nextInt();

                    System.out.println("Digite a versão X da compressão:");
                    int versao = scanner.nextInt();

                    String compressedFileName = "src/resources/jogadores" +
                            (algoritmo == 1 ? "Huffman" : "LZW") +
                            "Compressao" + versao;

                    try {
                        byte[] compressedData = Files.readAllBytes(Paths.get(compressedFileName));
                        byte[] decompressedData;

                        if (algoritmo == 1) {
                            decompressedData = huffman.decompress(compressedData);
                        } else {
                            decompressedData = lzw.decompress(compressedData);
                        }

                        // Write decompressed data back to original file
                        Files.write(Paths.get(DB_PATH), decompressedData);
                        System.out.println("Arquivo descomprimido com sucesso!");

                    } catch (IOException e) {
                        System.out.println("Erro ao descomprimir arquivo: " + e.getMessage());
                    }
                    break;

                case 6:
                    // Pattern matching with proper byte array to String conversion
                    scanner.nextLine(); // Clear buffer
                    System.out.println("\nDigite o padrão a ser procurado:");
                    String padrao = scanner.nextLine();

                    System.out.println("\nEscolha o algoritmo de busca:");
                    System.out.println("1- KMP\n2- Boyer-Moore");
                    int algBusca = scanner.nextInt();

                    try {
                        // Read file as bytes and convert to String for pattern matching
                        byte[] fileBytes = Files.readAllBytes(Paths.get(DB_PATH));
                        String conteudo = new String(fileBytes);
                        List<Integer> resultados;

                        if (algBusca == 1) {
                            resultados = patternMatcher.KMPSearch(padrao, conteudo);
                        } else {
                            resultados = patternMatcher.boyerMooreSearch(padrao, conteudo);
                        }

                        if (!resultados.isEmpty()) {
                            System.out.println("Padrão encontrado nas posições:");
                            for (int pos : resultados) {
                                System.out.println(pos);
                            }
                        } else {
                            System.out.println("Padrão não encontrado.");
                        }

                    } catch (IOException e) {
                        System.out.println("Erro ao ler arquivo: " + e.getMessage());
                    }
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            
                case 7:
                    CriptografiaRSA rsa = new CriptografiaRSA();

                    System.out.println("1- Criptografar o arquivo\n2- Descriptografar o arquivo");
                    int opcaoCriptografia = scanner.nextInt();

                    String encryptedPath = "src/resources/jogadores_criptografado.db";
                    String decryptedPath = "src/resources/jogadores_descriptografado.db";

                    try {
                        if (opcaoCriptografia == 1) {
                            rsa.encryptFile(DB_PATH, encryptedPath);
                            System.out.println("Arquivo criptografado com sucesso em: " + encryptedPath);
                        } else if (opcaoCriptografia == 2) {
                            rsa.decryptFile(encryptedPath, decryptedPath);
                            System.out.println("Arquivo descriptografado com sucesso em: " + decryptedPath);
                        } else {
                            System.out.println("Opção inválida.");
                        }
                    } catch (Exception e) {
                        System.out.println("Erro durante o processo de criptografia/descriptografia: " + e.getMessage());
                    }
                    break;

            System.out.println("Menu:\n1- Ler Registro\n2- Deletar Registro\n3- Atualizar Registro" +
                    "\n4- Comprimir Arquivo\n5- Descomprimir Arquivo" +
                    "\n6- Buscar Padrão\n7- Finalizar\nDigite o número correspondente a sua escolha: ");
            opcaoEscolhida = scanner.nextInt();
        

        scanner.close();
    }
}
}
}
