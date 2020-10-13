package amerifrance.guideapi.utils;

import amerifrance.guideapi.deserialization.DeserializerRegistry;

public class HtmlHelper {

    public static Object deserializeFromHtmlString(String string) {
        return DeserializerRegistry.get("HTML").deserialize(string);
    }
}
