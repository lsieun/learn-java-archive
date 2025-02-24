package lsieun.archive.theme.zip4j;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.EncryptionMethod;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class ZipPassword_D_Split_Zip_File {
    public static void main(String[] args) throws IOException {
        ZipParameters zipParameters = new ZipParameters();
        zipParameters.setEncryptFiles(true);
        zipParameters.setEncryptionMethod(EncryptionMethod.AES);

        ZipFile zipFile = new ZipFile("compressed.zip", "password".toCharArray());
        int splitLength = 1024 * 1024 * 10; //10MB
        zipFile.createSplitZipFile(Arrays.asList(new File("E:\\Software\\MySQL\\mysql-8.2.0-winx64.7z")), zipParameters, true, splitLength);
        zipFile.close();
    }
}
