package amerifrance.guideapi.utils.deserialization;

import amerifrance.guideapi.deserialization.DeserializerRegistry;

public class MarkdownHelper {

    public static Object deserializeFromMarkdownString(String string) {
        return DeserializerRegistry.get("MARKDOWN").deserialize(string);
    }
}
