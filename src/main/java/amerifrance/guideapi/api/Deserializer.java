package amerifrance.guideapi.api;

public interface Deserializer {
    Object deserialize(String value, Object... initParameters);
}
