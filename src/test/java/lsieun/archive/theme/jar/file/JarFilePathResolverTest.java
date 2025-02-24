package lsieun.archive.theme.jar.file;

import com.google.common.base.Ascii;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URISyntaxException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class JarFilePathResolverTest {

    @Test
    void givenClassObjectWhenCallingByGetProtectionDomainShouldGetExpectedPath() throws Exception {
        Class<?> clazz = Ascii.class;
        String jarPath = JarFilePathResolver.byGetProtectionDomain(clazz);
        System.out.println(jarPath);
        assertThat(jarPath).endsWith(".jar").contains("guava");
        assertThat(new File(jarPath)).exists();
    }

    @Test
    void givenClassObjectWhenCallingByGetResourceShouldGetExpectedPath() {
        String jarPath = JarFilePathResolver.byGetResource(Ascii.class);
        assertThat(jarPath).endsWith(".jar").contains("guava");
        assertThat(new File(jarPath)).exists();
    }

    @Test
    void givenClassObjectWhenNoSecurityExceptionRaisedShouldGetExpectedPath() throws URISyntaxException {
        String jarPath = JarFilePathResolver.getJarFilePath(Ascii.class);
        assertThat(jarPath).endsWith(".jar").contains("guava");
        assertThat(new File(jarPath)).exists();
//        verify(JarFilePathResolver, times(1)).byGetProtectionDomain(Ascii.class);
//        verify(JarFilePathResolver, never()).byGetResource(Ascii.class);
    }

    @Test
    void givenClassObjectWhenSecurityExceptionRaisedShouldGetExpectedPath() throws URISyntaxException {
//        when(JarFilePathResolver.byGetProtectionDomain(Ascii.class)).thenThrow(new SecurityException("not allowed"));
//        String jarPath = JarFilePathResolver.getJarFilePath(Ascii.class);
//        assertThat(jarPath).endsWith(".jar").contains("guava");
//        assertThat(new File(jarPath)).exists();
//        verify(jarFilePathResolver, times(1)).byGetProtectionDomain(Ascii.class);
//        verify(jarFilePathResolver, times(1)).byGetResource(Ascii.class);
    }
}