package amerifrance.guideapi.api;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

public interface ITypeReader<T> extends JsonSerializer<T>, JsonDeserializer<T> {

    Class<? extends T> getType();

}
