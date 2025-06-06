package inaugural.soliloquy.tools.tests.files;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static inaugural.soliloquy.tools.files.Files.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FilesTests {
    private final String LOCAL_PATH = "\\src\\test\\resources\\sampleFile.txt";
    private final String FILE_CONTENTS = "This file has text.";

    @Test
    public void testExecutionDirectory() {
        String localFolderName = "\\inaugural.soliloquy.tools";

        String executionDirectory = executionDirectory();

        assertNotNull(executionDirectory);
        assertEquals(localFolderName, executionDirectory.substring(
                executionDirectory.length() - localFolderName.length()));
    }

    @Test
    public void testGetLocalFile() throws IOException {
        File file = getLocalFile(LOCAL_PATH);
        String readFromFile = FileUtils.readFileToString(file, "UTF-8");

        assertEquals(FILE_CONTENTS, readFromFile);
    }

    @Test
    public void testReadAllLinesFromLocalFile() {
        String fileContents = readAllLinesFromLocalFile(LOCAL_PATH, Charset.UTF_8);

        assertEquals(FILE_CONTENTS, fileContents);
    }
}
