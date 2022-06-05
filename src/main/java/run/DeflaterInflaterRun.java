package run;


import lsieun.utils.number.HexUtils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class DeflaterInflaterRun {
    public static void main(String[] arg) throws DataFormatException, UnsupportedEncodingException {
        String inStr = "Hello World!";
        byte[] data = inStr.getBytes(StandardCharsets.UTF_8);
        System.out.println(HexUtils.toHex(data));
        byte[] output = new byte[100];
        //Compresses the data
        Deflater compresser = new Deflater();
        compresser.setInput(data);
        compresser.finish();
        int bytesAfterdeflate = compresser.deflate(output);
        System.out.println("Compressed byte number:" + bytesAfterdeflate);
        System.out.println(HexUtils.toHex(output));

        //Decompresses the data
        Inflater decompresser = new Inflater();
        decompresser.setInput(output, 0, bytesAfterdeflate);
        byte[] result = new byte[100];
        int resultLength = decompresser.inflate(result);
        decompresser.end();
        String outStr = new String(result, 0, resultLength, StandardCharsets.UTF_8);
        System.out.println("Decompressed data: " + outStr);
        System.out.println(HexUtils.toHex(result));
    }
}
