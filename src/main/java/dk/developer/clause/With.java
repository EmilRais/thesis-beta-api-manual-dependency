package dk.developer.clause;

public interface With<Input, Output> {
    Output with(Input input);

    interface Void<Input> {
        void with(Input input);
    }

    interface Bool<Input> {
        boolean with(Input input);
    }

    interface Var<Input, Output> {
        Output with(Input... input);

        interface Void<Input> {
            void with(Input... input);
        }
    }
}
