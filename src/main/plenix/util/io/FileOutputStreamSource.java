package plenix.util.io;

import java.io.FileOutputStream;
import java.io.OutputStream;


public class FileOutputStreamSource implements OutputStreamSource {
    private String filename;
    
    public FileOutputStreamSource() {}
    
    public FileOutputStreamSource(String filename) {
        this.filename = filename;
    }
    
    @Override
    public OutputStream getOutputStream() {
        try {
            return new FileOutputStream(filename);
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
