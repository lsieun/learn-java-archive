package run;


import java.nio.charset.StandardCharsets;
import java.util.HexFormat;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class DeflaterInflaterRun {
    public static void main(String[] arg) throws Exception {
        HexFormat hexFormat = HexFormat.of();

        String inStr = "Hello World!";
        byte[] data = inStr.getBytes(StandardCharsets.UTF_8);
        System.out.println(hexFormat.formatHex(data));
        byte[] output = new byte[100];
        //Compresses the data
        Deflater compresser = new Deflater();
        compresser.setInput(data);
        compresser.finish();
        int bytesAfterdeflate = compresser.deflate(output);
        System.out.println("Compressed byte number:" + bytesAfterdeflate);
        System.out.println(hexFormat.formatHex(output));

        //Decompresses the data
        Inflater decompresser = new Inflater();
        decompresser.setInput(output, 0, bytesAfterdeflate);
        byte[] result = new byte[100];
        int resultLength = decompresser.inflate(result);
        decompresser.end();
        String outStr = new String(result, 0, resultLength, StandardCharsets.UTF_8);
        System.out.println("Decompressed data: " + outStr);
        System.out.println(hexFormat.formatHex(result));
    }
}
