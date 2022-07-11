package files;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.openjdk.jmh.runner.RunnerException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Files: ReadWriteTest")
public class ReadWriteTest {

  @Test
  @DisplayName("Should to append a small file")
  public void shouldToAppendASmallFile(@TempDir Path tempDir) throws IOException, RunnerException {
    Path pathSmallFile = tempDir.resolve("small_file.txt");
    Path pathSmallFileCopy = tempDir.resolve("small_file_copy.txt");

    List<String> contentSource = Arrays.asList(
      "a",
      "b",
      "c"
    );

    List<String> contentTarget = Arrays.asList(
      "f",
      "g",
      "h"
    );

    writeFile(pathSmallFile, contentSource);
    writeFile(pathSmallFile, contentTarget);

    new ReadWrite().appendFile(pathSmallFile.toString(), pathSmallFileCopy.toString());

    List<String> allContent = new ArrayList<>();

    allContent.addAll(contentSource);
    allContent.addAll(contentTarget);

    assertEquals(allContent, Files.readAllLines(pathSmallFileCopy));
  }

  private final void writeFile(Path path, List<String> content) throws IOException {
    try (
      FileWriter fileWriter = new FileWriter(path.toFile(), true);
      PrintWriter printWriter = new PrintWriter(new BufferedWriter(fileWriter));
    ) {
      content
        .forEach(printWriter::println);
    } catch (Exception e) {
      throw e;
    }
  }
}
