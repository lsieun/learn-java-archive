package lsieun.archive.theme.zip;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class B_Unzip {
    public static void main(String[] args) {
        String zipFileName = "ziptest.zip";
        String unzipdirectory = "extracted";
        unzip(zipFileName, unzipdirectory);
    }

    public static void unzip(String zipFileName, String unzipDir) {
        try (
                ZipInputStream zis = new ZipInputStream(
                        new BufferedInputStream(new FileInputStream(zipFileName))
                )
        ) {
            // Read each entry from the ZIP file
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                // Extract the entry's contents
                extractEntryContent(zis, entry, unzipDir);
            }
            System.out.println("ZIP file's contents have been extracted to " + (new File(unzipDir)).getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void extractEntryContent(ZipInputStream zis, ZipEntry entry, String unzipDir) throws IOException {
        String entryFileName = entry.getName();
        String entryPath = unzipDir + File.separator + entryFileName;

        // Create the entry file by creating necessary directories
        createFile(entryPath);

        // Create an output stream to extract the contents of the
        // zip entry and write to the new file
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(entryPath))) {
            byte[] buffer = new byte[1024];
            int count;
            while ((count = zis.read(buffer)) != -1) {
                bos.write(buffer, 0, count);
            }
        }
    }

    public static void createFile(String filePath) throws IOException {
        File file = new File(filePath);
        File parent = file.getParentFile();
        // Create all parent directories if they do not exist
        if (!parent.exists()) {
            parent.mkdirs();
        }

        file.createNewFile();
    }
}
