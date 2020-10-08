package amerifrance.guideapi.deserialization.renderers;

import amerifrance.guideapi.deserialization.JsonDeserializer;
import amerifrance.guideapi.deserialization.RegisterDeserializer;
import amerifrance.guideapi.gui.RenderElement;
import amerifrance.guideapi.renderers.TextureRenderer;
import com.google.gson.JsonObject;
import net.minecraft.util.Identifier;

@RegisterDeserializer("TEXTURE")
public class TextureRendererDeserializer implements JsonDeserializer {

    private static final String IMAGE = "image";
    private static final String U = "u";
    private static final String V = "v";
    private static final String WIDTH = "width";
    private static final String HEIGHT = "height";

    @Override
    public Object deserialize(String value, Object... initParameters) {
        JsonObject rendererJson = GSON.fromJson(value, JsonObject.class);
        JsonObject parameters = rendererJson.getAsJsonObject(PARAMETERS);

        Identifier image = new Identifier(parameters.get(IMAGE).getAsString());
        int u = parameters.get(U).getAsInt();
        int v = parameters.get(V).getAsInt();
        int width = parameters.get(WIDTH).getAsInt();
        int height = parameters.get(HEIGHT).getAsInt();

        return new TextureRenderer(new RenderElement(image, u, v, width, height));
    }
}
