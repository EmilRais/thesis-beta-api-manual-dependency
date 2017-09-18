package dk.developer.clause;

public interface Post<Input, Output> {
    Output post(Input input);

    interface Void<Input> {
        void post(Input input);
    }
}
