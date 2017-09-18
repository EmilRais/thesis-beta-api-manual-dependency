package dk.developer.database;

import com.google.testing.compile.CompileTester;
import com.google.testing.compile.JavaFileObjects;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.tools.JavaFileObject;

import static com.google.common.truth.Truth.ASSERT;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;
import static dk.developer.database.CollectionProcessor.*;
import static dk.developer.database.CollectionProcessorTest.Helper.*;

public class CollectionProcessorTest {
    private CollectionProcessor processor;

    @BeforeMethod
    public void setUp() throws Exception {
        processor = new CollectionProcessor();
    }

    @Test
    public void shouldRejectAbstractClasses() throws Exception {
        expectThat(anAbstractClass()).failsToCompile().withErrorContaining(NOT_ALLOWED_ERROR);
    }

    @Test
    public void shouldRejectCollectionWhenTypeIsNotStorable() throws Exception {
        expectThat(nonStorableClass()).failsToCompile().withErrorContaining(NOT_STORABLE_ERROR);
    }

    @Test
    public void shouldAcceptCollectionOnStorableType() throws Exception {
        expectThat(storableClass()).compilesWithoutError();
    }

    private CompileTester expectThat(JavaFileObject target) {
        return ASSERT.about(javaSource()).that(target).processedWith(processor);
    }

    static class Helper {
        private Helper() {
        }

        static JavaFileObject anAbstractClass() {
            return JavaFileObjects.forSourceLines(
                    "dk.developer.database.SomeClass",
                    "package dk.developer.database;",
                    "@Collection(\"Items\")",
                    "abstract class SomeClass {}");
        }

        static JavaFileObject nonStorableClass() {
            return JavaFileObjects.forSourceLines(
                    "dk.developer.database.SomeParent",
                    "package dk.developer.database;",
                    "abstract class SomeParent {",
                    "@Collection(\"Items\")",
                    "class SomeChild extends SomeParent {}",
                    "}");
        }

        static JavaFileObject storableClass() {
            return JavaFileObjects.forSourceLines(
                    "dk.developer.database.SomeParent",
                    "package dk.developer.database;",
                    "@Storable",
                    "abstract class SomeParent {",
                    "@Collection(\"Items\")",
                    "class SomeChild extends SomeParent {}",
                    "}");
        }
    }
}