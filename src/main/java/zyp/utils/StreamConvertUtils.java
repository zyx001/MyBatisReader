package zyp.utils;

import java.io.*;

public class StreamConvertUtils {

    public static String convert(InputStream inputStream) throws IOException {
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        }
        return writer.toString();
    }
}
