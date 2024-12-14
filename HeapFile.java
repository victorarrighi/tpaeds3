import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class HeapFile {
    static final String TEMP_DIR = "src/resources/temp";
    static final String DB_PATH = "src/resources/jogadores.db";
    private RandomAccessFile raf;

    public HeapFile() throws IOException {
        File tempDirectory = new File(TEMP_DIR);
        if (!tempDirectory.exists()) {
            boolean dirCreated = tempDirectory.mkdirs(); // Cria o diretório
            if (!dirCreated) {
                throw new IOException("Não foi possível criar o diretório temporário: " + TEMP_DIR);
            }
        }

        raf = new RandomAccessFile(DB_PATH, "rw");
    }

    public void save(NBAPlayerAA playerAA) {
        try {
            raf.seek(raf.length());
            int tamanho = playerAA.getBytes();
            boolean lapide = false;
            raf.writeShort(tamanho);
            raf.writeBoolean(lapide);
            raf.write(playerAA.serialize());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveAll(Collection<NBAPlayerAA> players) {
        players.forEach(this::save);
    }

    public NBAPlayerAA findByID(int id) {
        try {
            raf.seek(0);
            while (true) {
                try {
                    int tamanho = raf.readShort();
                    boolean lapide = raf.readBoolean();

                    if(lapide)
                        raf.skipBytes(tamanho);
                    else {
                        long recordInitialOffset = raf.getFilePointer();
                        int currentID = raf.readInt();
                        raf.seek(recordInitialOffset);
                        if(currentID == id)
                            return readRecord(tamanho);
                        raf.skipBytes(tamanho);
                    }
                } catch (EOFException e) {
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<NBAPlayerAA> findAll() {
        List<NBAPlayerAA> found = new ArrayList<>();
        try {
            raf.seek(0);
            while (true) {
                try {
                    int tamanho = raf.readShort();
                    boolean lapide = raf.readBoolean();

                    if(lapide)
                        raf.skipBytes(tamanho);
                    else {
                        var player = readRecord(tamanho);
                        found.add(player);
                    }
                } catch (EOFException e) {
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return found;
    }

    public boolean deleteByID(int id) {
        try {
            raf.seek(0);
            while (true) {
                try {
                    int tamanho = raf.readShort();
                    boolean lapide = raf.readBoolean();

                    if (lapide) {
                        raf.skipBytes(tamanho);
                    } else {
                        long recordInitialOffset = raf.getFilePointer();
                        int currentID = raf.readInt();

                        if (currentID == id) {
                            raf.seek(recordInitialOffset - 1);
                            raf.writeBoolean(true);
                            return true;
                        }

                        raf.skipBytes(tamanho - 4);
                    }
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;  // Retorna falso se o ID não for encontrado
    }

    public void updatePlayer(NBAPlayerAA newPlayer){
        try {
            raf.seek(0);
            while (true) {
                try {
                    int tamanho = raf.readShort();
                    boolean lapide = raf.readBoolean();

                    if (lapide) {
                        raf.skipBytes(tamanho - 5); // Corrige a posição após "tamanho" e "booleano"
                    } else {
                        long recordInitialOffset = raf.getFilePointer();
                        int currentID = raf.readInt();

                        if (currentID == newPlayer.getID()) {
                            int currentRecordSize = tamanho;
                            int newRecordSize = newPlayer.getBytes();

                            if (newRecordSize > currentRecordSize) {
                                // Marca o registro atual como "lápide"
                                raf.seek(recordInitialOffset - 5); // Ajuste o deslocamento para incluir "tamanho" e "booleano"
                                raf.writeBoolean(true);
                                System.out.println("Registro marcado como 'lápide'.");

                                // Move o ponteiro para o final do arquivo e salva o novo registro
                                raf.seek(raf.length());
                                save(newPlayer);
                                System.out.println("Novo registro salvo ao final do arquivo.");
                            } else {
                                // Atualiza o registro existente
                                raf.seek(recordInitialOffset);
                                raf.write(newPlayer.serialize());

                                // Preenche o espaço restante se o novo registro for menor
                                if (newRecordSize < currentRecordSize) {
                                    raf.write(new byte[currentRecordSize - newRecordSize]);
                                    System.out.println("Espaço restante preenchido.");
                                }
                            }
                            return;
                        }
                        raf.skipBytes(tamanho - 4); // Pular bytes até o próximo registro
                    }
                } catch (Exception e) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private NBAPlayerAA readRecord(int byteLength) throws IOException {
        byte[] buffer = new byte[byteLength];
        raf.read(buffer);
        NBAPlayerAA player = new NBAPlayerAA();
        player.deserialize(buffer);
        return player;
    }


    private class RunFileReader {
        RandomAccessFile runFile;
        NBAPlayerAA currentPlayer;

        RunFileReader(RandomAccessFile runFile) throws IOException {
            this.runFile = runFile;
            next();
        }

        boolean hasNext() {
            return currentPlayer != null;
        }

        void next() throws IOException {
            try {
                int tamanho = runFile.readShort();
                boolean lapide = runFile.readBoolean();
                if (!lapide) {
                    byte[] buffer = new byte[tamanho];
                    runFile.readFully(buffer); // Garante que todos os bytes sejam lidos
                    currentPlayer = new NBAPlayerAA();
                    currentPlayer.deserialize(buffer);
                } else {
                    currentPlayer = null;
                }
            } catch (EOFException e) {
                currentPlayer = null;
            }
        }

        void close() throws IOException {
            runFile.close();
        }
    }


}