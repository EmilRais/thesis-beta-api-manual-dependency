package dk.developer.clause;

public interface Matching<Input, Output> {
    Output matching(Input input);

    interface Void<Input> {
        void matching(Input input);
    }
}
