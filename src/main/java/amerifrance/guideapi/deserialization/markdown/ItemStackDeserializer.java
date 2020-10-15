package amerifrance.guideapi.deserialization.markdown;

import amerifrance.guideapi.api.Deserializer;

import java.util.regex.Pattern;

public interface ItemStackDeserializer extends Deserializer {
    Pattern PATTERN = Pattern.compile("^\\[.*]\\((.*)\\)$");
}
