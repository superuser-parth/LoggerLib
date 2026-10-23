package sink;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.zip.GZIPOutputStream;

import config.FileSinkConfig;
import model.LogMessage;
import utils.LogFormatter;

public class FileSink implements Sink{
    private final FileSinkConfig config;
    private final LogFormatter formatter;
    private final Path filePath;

    public FileSink(FileSinkConfig config, LogFormatter formatter) throws IOException{
        this.config = config;
        this.formatter = formatter;

        this.filePath = Path.of("logs", "application.log");

        Files.createDirectories(filePath.getParent());

        if(!Files.exists(filePath)){
            Files.createFile(filePath);
        }
    }
    @Override 
    public synchronized void writeMessage(LogMessage message){
        String formattedMessage = formatter.format(message) + System.lineSeparator();

        byte[] bytes = formattedMessage.getBytes(
            StandardCharsets.UTF_8
        );

        try{
            long currentSize = Files.size(filePath);

            if(currentSize > 0 && currentSize + bytes.length > config.getMaxFileSizeByte()){
                rotate();
            }

            Files.write(
                filePath,
                bytes,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
            );
        } catch (IOException e){
            throw new RuntimeException("Failed to write logs to " + filePath, e);
        }
    }

    public void rotate() throws IOException{
        int maxBackups = config.getMaxBackupFiles();

        if(maxBackups == 0){
            Files.newOutputStream(filePath, StandardOpenOption.TRUNCATE_EXISTING).close();
            return;
        }

        Path tempFile = filePath.resolveSibling("application.log.tmp.gz");
        Path newestBackup = getBackupPath(1);

        try(
            InputStream input = Files.newInputStream(filePath);
            OutputStream output = new GZIPOutputStream(
                Files.newOutputStream(tempFile, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)
            )
        ){
            input.transferTo(output);
        }catch(IOException e){
            Files.deleteIfExists(tempFile);
            throw e;
        }

        Files.deleteIfExists(getBackupPath(maxBackups));    

        for(int i = maxBackups -1 ; i>=1 ; i--){
            Path source = getBackupPath(i);
            Path target = getBackupPath(i+1);

            if(Files.exists(source)){
                Files.move(
                    source,
                    target,
                    StandardCopyOption.REPLACE_EXISTING
                );
            }
        }

        Files.move(
            tempFile,
            newestBackup,
            StandardCopyOption.REPLACE_EXISTING
        );

        Files.newOutputStream(
            filePath,
            StandardOpenOption.TRUNCATE_EXISTING
        ).close();
    }

    private Path getBackupPath(int index){
        return filePath.resolveSibling("application.log." + index + ".gz");
    }
}
