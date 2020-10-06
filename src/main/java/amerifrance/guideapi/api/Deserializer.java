package amerifrance.guideapi.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public interface Deserializer {
    Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    Object deserialize(String value, Object... initParameters);
}
