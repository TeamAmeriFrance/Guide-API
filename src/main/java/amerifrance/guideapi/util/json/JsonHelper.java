package amerifrance.guideapi.util.json;

import com.google.gson.JsonElement;

import java.awt.*;

public class JsonHelper {

    // Getters

    public static boolean getBoolean(JsonElement jsonElement, String memberName, boolean def) {
        if (!jsonElement.getAsJsonObject().has(memberName) || jsonElement.getAsJsonObject().get(memberName).isJsonNull())
            return def;

        return jsonElement.getAsJsonObject().get(memberName).getAsBoolean();
    }

    public static String getString(JsonElement jsonElement, String memberName, String def) {
        if (!jsonElement.getAsJsonObject().has(memberName) || jsonElement.getAsJsonObject().get(memberName).isJsonNull())
            return def;

        return jsonElement.getAsJsonObject().get(memberName).getAsString();
    }

    public static char getCharacter(JsonElement jsonElement, String memberName, char def) {
        if (!jsonElement.getAsJsonObject().has(memberName) || jsonElement.getAsJsonObject().get(memberName).isJsonNull())
            return def;

        return jsonElement.getAsJsonObject().get(memberName).getAsCharacter();
    }

    public static int getInteger(JsonElement jsonElement, String memberName, int def) {
        if (!jsonElement.getAsJsonObject().has(memberName) || jsonElement.getAsJsonObject().get(memberName).isJsonNull())
            return def;

        return jsonElement.getAsJsonObject().get(memberName).getAsInt();
    }

    public static short getShort(JsonElement jsonElement, String memberName, short def) {
        if (!jsonElement.getAsJsonObject().has(memberName) || jsonElement.getAsJsonObject().get(memberName).isJsonNull())
            return def;

        return jsonElement.getAsJsonObject().get(memberName).getAsShort();
    }

    public static long getLong(JsonElement jsonElement, String memberName, long def) {
        if (!jsonElement.getAsJsonObject().has(memberName) || jsonElement.getAsJsonObject().get(memberName).isJsonNull())
            return def;

        return jsonElement.getAsJsonObject().get(memberName).getAsLong();
    }

    public static double getDouble(JsonElement jsonElement, String memberName, double def) {
        if (!jsonElement.getAsJsonObject().has(memberName) || jsonElement.getAsJsonObject().get(memberName).isJsonNull())
            return def;

        return jsonElement.getAsJsonObject().get(memberName).getAsDouble();
    }

    public static float getFloat(JsonElement jsonElement, String memberName, float def) {
        if (!jsonElement.getAsJsonObject().has(memberName) || jsonElement.getAsJsonObject().get(memberName).isJsonNull())
            return def;

        return jsonElement.getAsJsonObject().get(memberName).getAsFloat();
    }

    public static Color getColor(JsonElement jsonElement, String memberName, Color def) {
        return getColor(jsonElement, memberName, "#" + def.getRGB());
    }

    public static Color getColor(JsonElement jsonElement, String memberName, String def) {
        if (!def.startsWith("#"))
            def = "#" + def;

        if (!jsonElement.getAsJsonObject().has(memberName) || jsonElement.getAsJsonObject().get(memberName).isJsonNull())
            return Color.decode(def);

        String ret = jsonElement.getAsJsonObject().get(memberName).getAsString();

        if (!ret.startsWith("#"))
            ret = "#" + ret;

        return Color.decode(ret);
    }
}
