package amerifrance.guideapi.deserialization;

import amerifrance.guideapi.api.Deserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public interface JsonDeserializer extends Deserializer {
    Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    String ID = "id";
    String NAME = "name";
    String RENDERER = "renderer";
    String DISPLAY = "display";
    String PARAMETERS = "parameters";
}
