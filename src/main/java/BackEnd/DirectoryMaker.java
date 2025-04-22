package BackEnd;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DirectoryMaker {
    private String path = "C://Programming//Lucene//Directory";

    public  DirectoryMaker(){
        super();
    }

    public void createDirectory() throws IOException {Files.createDirectories(Paths.get(this.path));}

    public String getPath() {
        return path;
    }
}
