package plenix.util.io;

import java.io.FileInputStream;
import java.io.InputStream;


public class FileInputStreamSource implements InputStreamSource {
    private String filename;
    
    public FileInputStreamSource() {}
    
    public FileInputStreamSource(String filename) {
        this.filename = filename;
    }
    
    @Override
    public InputStream getInputStream() {
        try {
            return new FileInputStream(filename);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
