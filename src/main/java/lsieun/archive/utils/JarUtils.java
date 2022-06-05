package lsieun.archive.utils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class JarUtils {
    private static final String MAIN_CLASS = "Main-Class";

    public static String getMainClassFromJar(String jarName) {
        try (JarFile jarFile = new JarFile(jarName)) {
            Manifest manifest = jarFile.getManifest();
            if (manifest == null) {
                return null;
            }
            Attributes mainAttrs = manifest.getMainAttributes();
            if (mainAttrs == null) {
                return null;
            }
            String mainValue = mainAttrs.getValue(MAIN_CLASS);
            if (mainValue == null) {
                return null;
            }

            return mainValue.trim();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String getCurrentJarPath() {
        String filepath = null;
        try {
            filepath = new File(JarUtils.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
        }

        if (filepath == null || !filepath.endsWith(".jar")) {
            filepath = null;
        }
        return filepath;
    }

    public static String getManagementAgentJarPath() {
        String home = System.getProperty("java.home");
        return getManagementAgentJarPath(home);
    }

    public static String getManagementAgentJarPath(String javaHome) {
        return getJDKJarPath(javaHome, "management-agent.jar");
    }

    public static String getToolsJarPath() {
        String home = System.getProperty("java.home");
        return getToolsJarPath(home);
    }

    public static String getToolsJarPath(String javaHome) {
        return getJDKJarPath(javaHome, "tools.jar");
    }

    public static String getJDKJarPath(String javaHome, String jarName) {
        // (1) ${java.home}/jre/lib/xxx.jar
        String first_filepath = javaHome + File.separator + "jre" + File.separator + "lib" + File.separator + jarName;
        File firstFile = new File(first_filepath);
        if (firstFile.exists()) {
            return getCanonicalPath(firstFile);
        }

        // (2) ${java.home}/lib/xxx.jar
        String second_filepath = javaHome + File.separator + "lib" + File.separator + jarName;
        File secondFile = new File(second_filepath);
        if (secondFile.exists()) {
            return getCanonicalPath(secondFile);
        }

        // (3) ${java.home}/../lib/xxx.jar
        String third_filepath = javaHome + File.separator + ".." + File.separator + "lib" + File.separator + jarName;
        File thirdFile = new File(third_filepath);
        if (thirdFile.exists()) {
            return getCanonicalPath(thirdFile);
        }

        throw new RuntimeException(jarName + " not found");
    }

    private static String getCanonicalPath(File file) {
        try {
            return file.getCanonicalPath();
        } catch (IOException ignored) {
        }
        return null;
    }

}
