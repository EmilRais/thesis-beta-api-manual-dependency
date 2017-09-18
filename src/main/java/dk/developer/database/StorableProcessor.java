package dk.developer.database;

import javax.annotation.processing.*;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import java.util.HashSet;
import java.util.Set;

import static java.lang.String.format;
import static javax.lang.model.SourceVersion.RELEASE_8;
import static javax.lang.model.element.ElementKind.*;
import static javax.lang.model.element.Modifier.ABSTRACT;
import static javax.tools.Diagnostic.Kind.ERROR;

@SupportedSourceVersion(RELEASE_8)
public class StorableProcessor extends AbstractProcessor {
    static final String NOT_ALLOWED_ERROR = "Annotation only allowed on abstract classes";
    static final String NO_COLLECTION_ERROR;
    static {
        String storable = Storable.class.getSimpleName();
        String collection = Collection.class.getSimpleName();
        NO_COLLECTION_ERROR = format("@%s classes need to specify a @%s", storable, collection);
    }

    private Messager messager;

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> supportedAnnotationTypes = new HashSet<>();
        supportedAnnotationTypes.add(Storable.class.getName());
        return supportedAnnotationTypes;
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        annotations.stream().forEach(annotation -> {
            Name simpleName = annotation.getSimpleName();

            Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);
            annotatedElements.stream()
                    .filter(element -> isDirectlyAnnotated(element, simpleName))
                    .forEach(this::rejectAnythingButAbstractClasses);

            annotatedElements.stream()
                    .filter(element -> isIndirectlyAnnotated(element, simpleName))
                    .forEach(this::rejectIfConcreteAndNoCollection);
        });
        return false;
    }

    private void rejectAnythingButAbstractClasses(Element element) {
        if ( element.getKind() == CLASS && element.getModifiers().contains(ABSTRACT) )
            return;

        messager.printMessage(ERROR, NOT_ALLOWED_ERROR, element);
    }

    private boolean isDirectlyAnnotated(Element element, Name annotationName) {
        return element.getAnnotationMirrors().stream()
                .map(AnnotationMirror::getAnnotationType)
                .map(DeclaredType::asElement)
                .map(Element::getSimpleName)
                .anyMatch(name -> name.equals(annotationName));
    }

    private boolean isIndirectlyAnnotated(Element element, Name annotationName) {
        return !isDirectlyAnnotated(element, annotationName);
    }

    private void rejectIfConcreteAndNoCollection(Element element) {
        if ( element.getModifiers().contains(ABSTRACT) )
            return;

        if ( element.getAnnotation(Collection.class) != null )
            return;

        messager.printMessage(ERROR, NO_COLLECTION_ERROR, element);
    }
}
