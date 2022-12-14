package Evo.map;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class FileReader {
    public static Map<String, Integer> byStream(Path filePath) throws IOException, NumberFormatException {
        Map<String, Integer> map = new HashMap<>();
        try (Stream<String> lines = Files.lines(filePath)) {
            lines.filter(line -> line.contains(":"))
                    .forEach(line -> {
                        String[] keyValuePair = line.split(":", 2);
                        String key = keyValuePair[0];
                        Integer value = Integer.parseInt(keyValuePair[1]);
                        map.putIfAbsent(key, value);
                    });
        } catch (IOException e) {
            throw new IOException("File with given path does not exist or is not readable");
        } catch(NumberFormatException e){
            throw new NumberFormatException("File is formatted incorrectly");
        }
        return map;
    }
}
