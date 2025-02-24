package lsieun.archive.theme.jar.agent;

import java.io.*;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

public class JarAgentUtils {
    public static void createEmptyAgentJar(String jarPath, Class<?> AgentClass) {
        String className = AgentClass.getName();

        Manifest manifest = new Manifest();
        Attributes attrs = manifest.getMainAttributes();
        attrs.put(Attributes.Name.MANIFEST_VERSION, "1.0");
        attrs.put(new Attributes.Name("Premain-Class"), className);
        attrs.put(new Attributes.Name("Agent-Class"), className);
        attrs.put(new Attributes.Name("Can-Retransform-Classes"), "true");
        attrs.put(new Attributes.Name("Can-Redefine-Classes"), "true");

        try (JarOutputStream jos = new JarOutputStream(new FileOutputStream(jarPath), manifest)) {
            jos.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void createAgentJar(String filepath, String className, byte[] bytes) {
        Manifest manifest = new Manifest();
        Attributes attrs = manifest.getMainAttributes();
        attrs.put(Attributes.Name.MANIFEST_VERSION, "1.0");
        attrs.put(new Attributes.Name("Premain-Class"), className);
        attrs.put(new Attributes.Name("Agent-Class"), className);
        attrs.put(new Attributes.Name("Can-Retransform-Classes"), "true");
        attrs.put(new Attributes.Name("Can-Redefine-Classes"), "true");
        attrs.put(new Attributes.Name("Can-Set-Native-Method-Prefix"), "true");

        try (JarOutputStream jos = new JarOutputStream(new FileOutputStream(filepath), manifest)) {
            String item = className.replace('.', '/') + ".class";
            JarEntry entry = new JarEntry(item);
            jos.putNextEntry(entry);
            jos.write(bytes);
            jos.closeEntry();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String createTempAgentJar(Class<?> agentClass, Class<?>... array) {
        try {
            String className = agentClass.getName();
            File tmpFile = File.createTempFile(className, ".jar");
            System.out.println("Temp File:" + tmpFile.getAbsolutePath());
            tmpFile.deleteOnExit();

            Manifest manifest = new Manifest();
            Attributes attrs = manifest.getMainAttributes();
            attrs.put(Attributes.Name.MANIFEST_VERSION, "1.0");
            attrs.put(new Attributes.Name("Premain-Class"), className);
            attrs.put(new Attributes.Name("Agent-Class"), className);
            attrs.put(new Attributes.Name("Can-Retransform-Classes"), "true");
            attrs.put(new Attributes.Name("Can-Redefine-Classes"), "true");
            attrs.put(new Attributes.Name("Can-Set-Native-Method-Prefix"), "true");

            try (JarOutputStream jos = new JarOutputStream(new FileOutputStream(tmpFile), manifest)) {
                addClassToJar(jos, agentClass);

                if (array != null) {
                    for (Class<?> clazz : array) {
                        addClassToJar(jos, clazz);
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            return tmpFile.getAbsolutePath();
        } catch (Exception e) {
            throw new RuntimeException("Failed to write Agent installer Jar", e);
        }
    }

    private static void addClassToJar(JarOutputStream jos, Class<?> clazz) throws IOException {
        String item = getItemName(clazz);
        jos.putNextEntry(new JarEntry(item));
        jos.write(getClassBytes(clazz));
        jos.flush();
        jos.closeEntry();
    }

    private static byte[] getClassBytes(Class<?> clazz) {
        String item = getItemName(clazz);
        try (InputStream in = clazz.getClassLoader().getResourceAsStream(item)) {
            assert in != null;
            int length = in.available();

            ByteArrayOutputStream bao = new ByteArrayOutputStream(length);
            byte[] buffer = new byte[8092];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                bao.write(buffer, 0, bytesRead);
            }
            bao.flush();
            return bao.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to read class bytes for [" + clazz.getName() + "]", e);
        }
    }

    private static String getItemName(Class<?> clazz) {
        return clazz.getName().replace('.', '/') + ".class";
    }
}
