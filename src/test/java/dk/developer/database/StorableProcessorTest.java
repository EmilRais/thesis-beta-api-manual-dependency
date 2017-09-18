package dk.developer.database;

import com.google.testing.compile.CompileTester;
import com.google.testing.compile.JavaFileObjects;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.tools.JavaFileObject;

import static com.google.common.truth.Truth.ASSERT;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;
import static dk.developer.database.StorableProcessor.NOT_ALLOWED_ERROR;
import static dk.developer.database.StorableProcessor.NO_COLLECTION_ERROR;
import static dk.developer.database.StorableProcessorTest.Helper.*;

public class StorableProcessorTest {
    private StorableProcessor processor;

    @BeforeMethod
    public void setUp() throws Exception {
        processor = new StorableProcessor();
    }

    private CompileTester expectThat(JavaFileObject target) {
        return ASSERT.about(javaSource()).that(target).processedWith(processor);
    }

    @Test
    public void nonAbstractClassShouldNotBeAnnotatable() throws Exception {
        expectThat(aNonAbstractClass()).failsToCompile().withErrorContaining(NOT_ALLOWED_ERROR);
    }

    @Test
    public void abstractClassShouldBeAnnotatable() throws Exception {
        expectThat(anAbstractClass()).compilesWithoutError();
    }

    @Test
    public void interfaceShouldNotBeAnnotatable() throws Exception {
        expectThat(anInterface()).failsToCompile().withErrorContaining(NOT_ALLOWED_ERROR);
    }

    @Test
    public void enumShouldNotBeAnnotatable() throws Exception {
        expectThat(anEnum()).failsToCompile().withErrorContaining(NOT_ALLOWED_ERROR);
    }

    @Test
    public void annotationShouldNotBeAnnotatable() throws Exception {
        expectThat(anAnnotation()).failsToCompile().withErrorContaining(NOT_ALLOWED_ERROR);
    }

    @Test
    public void shouldSucceedOnSubclassesAnnotatedCollection() throws Exception {
        expectThat(childWithCollection()).compilesWithoutError();
    }

    @Test
    public void shouldSucceedOnAbstractSubClassesNotAnnotatedCollection() throws Exception {
        expectThat(abstractChildWithoutCollection()).compilesWithoutError();
    }

    @Test
    public void shouldFailOnNonAbstractSubclassesNotAnnotatedCollection() throws Exception {
        expectThat(childWithoutCollection()).failsToCompile().withErrorContaining(NO_COLLECTION_ERROR);
    }

    static class Helper {
        private Helper() {
        }

        static JavaFileObject aNonAbstractClass() {
            return JavaFileObjects.forSourceLines(
                    "dk.developer.database.SomeClass",
                    "package dk.developer.database;",
                    "@Storable",
                    "class SomeClass {}");
        }

        static JavaFileObject anAbstractClass() {
            return JavaFileObjects.forSourceLines(
                    "dk.developer.database.SomeClass",
                    "package dk.developer.database;",
                    "@Storable",
                    "abstract class SomeClass {}");
        }

        static JavaFileObject anInterface() {
            return JavaFileObjects.forSourceLines(
                    "dk.developer.database.SomeInterface",
                    "package dk.developer.database;",
                    "@Storable",
                    "interface SomeInterface {}");
        }

        static JavaFileObject anEnum() {
            return JavaFileObjects.forSourceLines(
                    "dk.developer.database.SomeEnum",
                    "package dk.developer.database;",
                    "@Storable",
                    "enum SomeEnum {}");
        }

        static JavaFileObject anAnnotation() {
            return JavaFileObjects.forSourceLines(
                    "dk.developer.database.SomeAnnotation",
                    "package dk.developer.database;",
                    "@Storable",
                    "@interface SomeAnnotation {}");
        }

        static JavaFileObject childWithCollection() {
            return JavaFileObjects.forSourceLines(
                    "dk.developer.database.SomeParent",
                    "package dk.developer.database;",
                    "@Storable",
                    "abstract class SomeParent {",
                    "@Collection(\"Items\")",
                    "class SomeChild extends SomeParent {}",
                    "}");
        }

        static JavaFileObject abstractChildWithoutCollection() {
            return JavaFileObjects.forSourceLines(
                    "dk.developer.database.SomeParent",
                    "package dk.developer.database;",
                    "@Storable",
                    "abstract class SomeParent {",
                    "abstract class SomeChild extends SomeParent {}",
                    "}");
        }

        static JavaFileObject childWithoutCollection() {
            return JavaFileObjects.forSourceLines(
                    "dk.developer.database.SomeParent",
                    "package dk.developer.database;",
                    "@Storable",
                    "abstract class SomeParent {",
                    "class SomeChild extends SomeParent {}",
                    "}");
        }
    }
}