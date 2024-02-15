package lsieun.archive.zip4j;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.EncryptionMethod;

import java.io.File;
import java.io.IOException;

public class ZipPassword_E_Split_Zip_Directory {
    public static void main(String[] args) throws IOException {
        ZipParameters zipParameters = new ZipParameters();
        zipParameters.setEncryptFiles(true);
        zipParameters.setEncryptionMethod(EncryptionMethod.AES);

        ZipFile zipFile = new ZipFile("compressed.zip", "password".toCharArray());
        int splitLength = 1024 * 1024 * 10; //10MB
        zipFile.createSplitZipFileFromFolder(new File("E:\\Software\\Scala"), zipParameters, true, splitLength);
        zipFile.close();
    }
}
