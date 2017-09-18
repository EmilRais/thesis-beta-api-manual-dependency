package dk.developer.testing;

import java.net.URI;
import java.net.URL;
import java.nio.file.Paths;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.readAllBytes;

public class JsonTool {
    private final Class<?> context;

    public JsonTool(Class<?> context) {
        this.context = context;
    }

    public String readJsonFile(String relativePath) {
        URL resource = context.getResource(relativePath);
        byte[] bytes = readBytes(resource);
        return new String(bytes, UTF_8);
    }

    private byte[] readBytes(URL path) {
        try {
            URI uri = path.toURI();
            return readAllBytes(Paths.get(uri));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String filterWhitespace(String string) {
        String removeAllWhitespaceOutsideQuotes = "\\s+(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
        return string.replaceAll(removeAllWhitespaceOutsideQuotes, "");
    }

    public String readFilteredJsonFile(String relativePath) {
        String json = readJsonFile(relativePath);
        return filterWhitespace(json);
    }
}
