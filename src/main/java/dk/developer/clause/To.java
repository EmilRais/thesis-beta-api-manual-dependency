package dk.developer.clause;

public interface To<Input, Output> {
    Output to(Input input);

    interface Void<Input> {
        void to(Input input);
    }
}
