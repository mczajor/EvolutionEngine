package Evo.map;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FileReaderTest {

    @Test
    void byStreamTest() {
        Path path = Path.of("src/main/resources/1.txt");
        Map<String, Integer> testMap = FileReader.byStream(path);
        assertAll(
                () -> assertEquals(10, testMap.get("x")),
                () -> assertEquals(10, testMap.get("y")),
                () -> assertEquals(14, testMap.get("z"))
                );
    }
}