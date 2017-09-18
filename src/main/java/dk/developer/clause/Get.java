package dk.developer.clause;

public interface Get<Input, Output> {
    Output get(Input input);

    interface Void<Input> {
        void get(Input input);
    }
}
