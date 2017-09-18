package dk.developer.testing;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.google.common.truth.Truth.ASSERT;

public class JsonToolTest {
    private JsonTool tool;

    @BeforeMethod
    public void setUp() throws Exception {
        tool = new JsonTool(JsonToolTest.class);
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void noFile() throws Exception {
        tool.readJsonFile("File.json");
    }

    @Test
    public void singleLineNoLineBreak() throws Exception {
        String json = tool.readJsonFile("json/SingleLineNoLineBreak.json");
        ASSERT.that(json).isEqualTo("{\"name\": \"Anders\"}");
    }

    @Test
    public void singleLineWithLineBreak() throws Exception {
        String json = tool.readJsonFile("json/SingleLineWithLineBreak.json");
        ASSERT.that(json).isEqualTo("{\"name\": \"Anders\"}\n");
    }

    @Test
    public void multipleLinesNoLineBreak() throws Exception {
        String json = tool.readJsonFile("json/MultipleLinesNoLineBreak.json");
        ASSERT.that(json).isEqualTo("{\n" + "  \"name\": \"Anders\"\n" + "}");
    }

    @Test
    public void filterEmptyString() throws Exception {
        String string = tool.filterWhitespace("");
        ASSERT.that(string).isEmpty();
    }

    @Test
    public void filterStringWithNoWhitespace() throws Exception {
        String string = tool.filterWhitespace("Anders");
        ASSERT.that(string).isEqualTo("Anders");
    }

    @Test
    public void filterAllSpaces() throws Exception {
        String string = tool.filterWhitespace(" And ers ");
        ASSERT.that(string).isEqualTo("Anders");
    }

    @Test
    public void filterExcludesSpacesInQuotes() throws Exception {
        String string = tool.filterWhitespace(" \" And ers \" ");
        ASSERT.that(string).isEqualTo("\" And ers \"");
    }

    @Test
    public void filterAllLineBreaks() throws Exception {
        String string = tool.filterWhitespace("\nAnders\n");
        ASSERT.that(string).isEqualTo("Anders");
    }

    @Test
    public void filterExcludesLineBreaksInQuotes() throws Exception {
        String string = tool.filterWhitespace("\n\"\nAnders\n\"\n");
        ASSERT.that(string).isEqualTo("\"\nAnders\n\"");
    }

    @Test
    public void allFilesAreEqualWhenFiltered() throws Exception {
        String singleNoBreak = tool.readFilteredJsonFile("json/SingleLineNoLineBreak.json");
        String singleWithBreak = tool.readFilteredJsonFile("json/SingleLineWithLineBreak.json");
        String multipleNoBreak = tool.readFilteredJsonFile("json/MultipleLinesNoLineBreak.json");

        ASSERT.that(singleNoBreak).isEqualTo(singleWithBreak);
        ASSERT.that(singleWithBreak).isEqualTo(multipleNoBreak);
    }
}