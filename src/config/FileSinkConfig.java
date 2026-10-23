package config;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
public class FileSinkConfig {
    private final long maxFileSizeByte;
    private final int maxBackupFiles;

    public FileSinkConfig(long maxFileSizeByte, int maxBackupFiles){

        if(maxFileSizeByte <= 0){
            throw new IllegalArgumentException("Max file size must be positive");
        }

        if(maxBackupFiles <= 0){
            throw new IllegalArgumentException("Max backup count must be positive");
        }

        this.maxFileSizeByte = maxFileSizeByte;
        this.maxBackupFiles = maxBackupFiles;
    }
}
