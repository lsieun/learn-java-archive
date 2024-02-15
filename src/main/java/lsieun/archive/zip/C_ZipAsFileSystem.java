package lsieun.archive.zip;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class C_ZipAsFileSystem {
    public static void main(String[] args) throws IOException {
        // Construct the URI pointing to the ZIP archive
        // Windows
        URI zipURI = URI.create("jar:file:///D:/tmp/MyArchive.zip");
        // Linux
        //URI zipURI = URI.create("jar:file:/Users/pat/tmp/MyArchive.zip");

        // Open or create it and write a file
        Map<String, String> env = new HashMap<>();
        env.put("create", "true");

        // Move the file
        try (FileSystem zipfs = FileSystems.newFileSystem(zipURI, env)) {
            Path path = zipfs.getPath("/README.txt");
            Path toPath = zipfs.getPath("/README2.txt");
            Files.move(path, toPath);
        }
    }
}
