package inaugural.soliloquy.tools.files;

import org.apache.commons.io.FileUtils;

import java.io.File;

import static inaugural.soliloquy.tools.exception.CheckedExceptionWrapper.callWrapped;

public class Files {
    public static String executionDirectory() {
        return System.getProperty("user.dir");
    }

    public static File getLocalFile(String localPath) {
        return new File(executionDirectory() + localPath);
    }

    public static String readAllLinesFromLocalFile(String localPath, Charset charset) {
        return callWrapped(
                () -> FileUtils.readFileToString(getLocalFile(localPath), charset.value));
    }

    private static final String UTF_8_VALUE = "UTF-8";
    private static final String UTF_16_VALUE = "UTF-16";

    public enum Charset {

        UTF_8(UTF_8_VALUE),
        UTF_16(UTF_16_VALUE);

        private final String value;

        Charset(String value) {
            this.value = value;
        }

        @SuppressWarnings("unused")
        public String getValue() {
            return value;
        }

        @SuppressWarnings("unused")
        public static Charset fromValue(String value) {
            switch (value) {
                case UTF_8_VALUE:
                    return UTF_8;
                case UTF_16_VALUE:
                    return UTF_16;
                default:
                    throw new IllegalArgumentException("Files.Charset: value (" + value +
                            ") does not correspond to valid enum type");
            }
        }
    }
}
