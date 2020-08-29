package amerifrance.guideapi.utils;

import java.util.List;

public class RecipePair {

    private final List<RenderStack> inputs;
    private final RenderStack output;

    public RecipePair(List<RenderStack> inputs, RenderStack output) {
        this.inputs = inputs;
        this.output = output;
    }

    public List<RenderStack> getInputs() {
        return inputs;
    }

    public RenderStack getOutput() {
        return output;
    }
}
