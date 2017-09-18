package dk.developer.clause;

public interface In<Input, Output> {
    Output in(Input input);
    
    interface Void<Input> {
        void in(Input input);
    }

    interface Bool<Input> {
        boolean in(Input input);
    }
}
