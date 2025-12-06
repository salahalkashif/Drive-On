package Texture;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Utility class that allows transparent reading of files from
 * the current working directory or from the classpath.
 * @author Pepijn Van Eeckhoudt
 */
public class ResourceRetriever {

    private static File findProjectRoot() {
        URL root = ResourceRetriever.class.getResource("/");
        if (root != null && "file".equals(root.getProtocol())) {
            try {
                File dir = new File(root.toURI()); // classpath root
                while (dir != null) {
                    File srcDir = new File(dir, "src");
                    File assetsDir = new File(dir, "Assets");
                    if (srcDir.exists() && srcDir.isDirectory() && assetsDir.exists() && assetsDir.isDirectory()) {
                        return dir; // This is the project root
                    }
                    dir = dir.getParentFile();
                }
            } catch (Exception e) {
                // ignore, will fallback
            }
        }
        return null;
    }

    private static final File projectRoot = findProjectRoot();

    public static URL getResource(final String filename) throws IOException {
        // Try to load resource from jar
        URL url = ClassLoader.getSystemResource(filename);
        // If not found in jar, then load from disk
        if (url == null) {
            if (projectRoot != null) {
                File file = new File(projectRoot, filename);
                return file.toURI().toURL();
            }
            // Fallback to original behavior
            return new URL("file", "localhost", filename);
        } else {
            return url;
        }
    }

    public static InputStream getResourceAsStream(final String filename) throws IOException {
        // Try to load resource from jar
        InputStream stream = ClassLoader.getSystemResourceAsStream(filename);
        // If not found in jar, then load from disk
        if (stream == null) {
            if (projectRoot != null) {
                File file = new File(projectRoot, filename);
                return new FileInputStream(file);
            }
            // Fallback to original behavior
            return new FileInputStream(filename);
        } else {
            return stream;
        }
    }
}
