package dk.developer.clause;

public interface At<Input, Output> {
    Output at(Input input);

    interface Void<Input> {
        void at(Input input);
    }
}
