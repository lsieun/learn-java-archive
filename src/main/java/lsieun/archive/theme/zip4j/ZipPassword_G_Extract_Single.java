package lsieun.archive.theme.zip4j;

import net.lingala.zip4j.ZipFile;

import java.io.IOException;

public class ZipPassword_G_Extract_Single {
    public static void main(String[] args) throws IOException {
        ZipFile zipFile = new ZipFile("compressed.zip", "password".toCharArray());
        zipFile.extractFile("aFile.txt", "D:\\tmp");
        zipFile.close();
    }
}
