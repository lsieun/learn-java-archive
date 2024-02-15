package lsieun.archive.zip4j;

import net.lingala.zip4j.ZipFile;

import java.io.IOException;

public class ZipPassword_F_Extract_All {
    public static void main(String[] args) throws IOException {
        ZipFile zipFile = new ZipFile("compressed.zip", "password".toCharArray());
        zipFile.extractAll("D:\\tmp");
        zipFile.close();
    }
}
