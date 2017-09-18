package dk.developer.database;

import javax.annotation.processing.*;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.HashSet;
import java.util.Set;

import static java.lang.String.format;
import static javax.lang.model.SourceVersion.RELEASE_8;
import static javax.lang.model.element.ElementKind.*;
import static javax.lang.model.element.Modifier.*;
import static javax.tools.Diagnostic.Kind.ERROR;

@SupportedSourceVersion(RELEASE_8)
public class CollectionProcessor extends AbstractProcessor {
    static final String NOT_ALLOWED_ERROR = "Annotation only allowed on concrete classes";
    static final String NOT_STORABLE_ERROR;
    static {
        String collection = Collection.class.getSimpleName();
        String storable = Storable.class.getSimpleName();
        NOT_STORABLE_ERROR = format("@%s can only be used on a @%s type", collection, storable);
    }

    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> supportedAnnotationTypes = new HashSet<>();
        supportedAnnotationTypes.add(Collection.class.getName());
        return supportedAnnotationTypes;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        annotations.stream()
                .map(roundEnv::getElementsAnnotatedWith)
                .flatMap(Set::stream)
                .forEach(element -> {
                    this.rejectIfNotAConcreteClass(element);
                    this.rejectIfNotASubtypeOfStorable(element);
                });
        return false;
    }

    private void rejectIfNotAConcreteClass(Element element) {
        if ( element.getKind() == CLASS && !element.getModifiers().contains(ABSTRACT) )
            return;

        messager.printMessage(ERROR, NOT_ALLOWED_ERROR, element);
    }

    private void rejectIfNotASubtypeOfStorable(Element element) {
        if ( element.getAnnotation(Storable.class) != null )
            return;

        messager.printMessage(ERROR, NOT_STORABLE_ERROR, element);
    }
}
