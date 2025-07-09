package zyp.io;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;

public class VFS {


    public static void main(String[] args) throws MalformedURLException {
        ///zyp-lock-0.0.1-SNAPSHOT.jar
        File file = new File("D:/MavenRepository/zyp/com/zyp-lock/0.0.1-SNAPSHOT/0.0.1-SNAPSHOT.jar");
        URL url = file.toURI().toURL(); // 这会自动加上 file:// 协议
        VFS vfs = new VFS();
        URL jarForResource = vfs.findJarForResource(url);
        System.out.println(jarForResource);
    }

    private static final byte[] JAR_MAGIC = { 'P', 'K', 3, 4 };

    protected URL findJarForResource(URL url) throws MalformedURLException {


        // If the file part of the URL is itself a URL, then that URL probably points to the JAR
        boolean continueLoop = true;
        while (continueLoop) {
            try {
                url = new URL(url.getFile());

            } catch (MalformedURLException e) {
                // This will happen at some point and serves as a break in the loop
                continueLoop = false;
            }
        }

        // Look for the .jar extension and chop off everything after that
        StringBuilder jarUrl = new StringBuilder(url.toExternalForm());
        int index = jarUrl.lastIndexOf(".jar");
        if (index >= 0) {
            jarUrl.setLength(index + 4);

        }
        else {

            return null;
        }

        // Try to open and test it
        try {
            URL testUrl = new URL(jarUrl.toString());
            if (isJar(testUrl)) {
                return testUrl;
            }
            /**
             * 下面这里的
             */
            else {
                // WebLogic fix: check if the URL's file exists in the filesystem.
                jarUrl.replace(0, jarUrl.length(), testUrl.getFile());
                File file = new File(jarUrl.toString());

                // File name might be URL-encoded
                if (!file.exists()) {
                    try {
                        file = new File(URLEncoder.encode(jarUrl.toString(), "UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException("Unsupported encoding?  UTF-8?  That's unpossible.");
                    }
                }

                if (file.exists()) {

                    testUrl = file.toURI().toURL();
                    if (isJar(testUrl)) {
                        return testUrl;
                    }
                }
            }
        } catch (MalformedURLException e) {
            System.out.println("Invalid JAR URL: " + jarUrl);
        }


        return null;
    }

    protected boolean isJar(URL url) {
        return isJar(url, new byte[JAR_MAGIC.length]);
    }

    protected boolean isJar(URL url, byte[] buffer) {
        InputStream is = null;
        try {
            is = url.openStream();
            is.read(buffer, 0, JAR_MAGIC.length);
            if (Arrays.equals(buffer, JAR_MAGIC)) {

                return true;
            }
        } catch (Exception e) {
            // Failure to read the stream means this is not a JAR
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                    // Ignore
                }
            }
        }

        return false;
    }

}
