package lsieun.archive.theme.zip4j;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.EncryptionMethod;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ZipPassword_B_Zip_Multiple_File {
    public static void main(String[] args) throws IOException {
        ZipParameters zipParameters = new ZipParameters();
        zipParameters.setEncryptFiles(true);
        zipParameters.setEncryptionMethod(EncryptionMethod.AES);

        List<File> filesToAdd = Arrays.asList(
                new File("aFile.txt"),
                new File("bFile.txt")
        );

        ZipFile zipFile = new ZipFile("compressed.zip", "password".toCharArray());
        zipFile.addFiles(filesToAdd, zipParameters);
        zipFile.close();
    }
}
