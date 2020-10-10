package amerifrance.guideapi.deserialization;

import amerifrance.guideapi.api.Deserializer;
import amerifrance.guideapi.api.RegisterDeserializer;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.util.List;
import java.util.Map;

public class DeserializerRegistry {
    public static final Map<String, Deserializer> registry = Maps.newLinkedHashMap();

    public static void register() {
        List<ClassLoader> classLoadersList = Lists.newArrayList(ClasspathHelper.contextClassLoader(), ClasspathHelper.staticClassLoader());

        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setScanners(new SubTypesScanner(false),
                        new TypeAnnotationsScanner(),
                        new ResourcesScanner())
                .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0]))));

        reflections.getTypesAnnotatedWith(RegisterDeserializer.class).forEach(clazz -> {
            try {
                register(clazz.getAnnotation(RegisterDeserializer.class).value(), (Deserializer) clazz.getDeclaredConstructor().newInstance());
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    public static Deserializer get(String key) {
        return registry.get(key.toLowerCase());
    }

    private static void register(String key, Deserializer deserializer) {
        registry.put(key.toLowerCase(), deserializer);
    }
}
