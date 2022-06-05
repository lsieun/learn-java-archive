package lsieun.archive.compress;

import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class CompressionTest {
    public static void compress() {
        Deflater compressor = new Deflater();

        // Try to read the compressed data 1024 bytes at a time
        byte[] readBuffer = new byte[1024];
        int readCount = 0;
        while (!compressor.finished()) {
            readCount = compressor.deflate(readBuffer);
            /* At this point, the readBuffer array has the compressed data from index 0 to readCount - 1. */
        }
    }

    public static void decompress() throws DataFormatException {
        Inflater decompressor = new Inflater();

        // Try to read the decompressed data 1024 bytes at a time
        byte[] readBuffer = new byte[1024];
        int readCount = 0;
        while(!decompressor.finished()) {
            readCount = decompressor.inflate(readBuffer);
            /* At this point, the readBuffer array has the decompressed data from index 0 to readCount - 1. */
        }
    }
}
