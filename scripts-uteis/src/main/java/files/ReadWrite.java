package files;

import org.openjdk.jmh.annotations.Benchmark;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.stream.Stream;

public class ReadWrite {

  public void appendFile(String sourcePathFile, String targetPathFile) {
    try (
      InputStream inputStream = new FileInputStream(sourcePathFile);
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
      FileWriter fileWriter = new FileWriter(targetPathFile, true);
      PrintWriter printWriter = new PrintWriter(new BufferedWriter(fileWriter));
      Stream<String> linesStream = bufferedReader.lines()
    ) {
      linesStream
        .forEach(printWriter::println);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
