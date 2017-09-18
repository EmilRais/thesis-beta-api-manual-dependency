package dk.developer.clause;

public interface From<Input, Output> {
    Output from(Input input);

    interface Void<Input> {
        void from(Input input);
    }

    interface Bool<Input> {
        boolean from(Input input);
    }
}
