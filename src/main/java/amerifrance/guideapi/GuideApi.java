package amerifrance.guideapi;


import amerifrance.guideapi.deserialization.DeserializerRegistry;
import amerifrance.guideapi.deserialization.displays.FixedShapeDisplayDeserializer;
import amerifrance.guideapi.deserialization.displays.LineDisplayDeserializer;
import amerifrance.guideapi.deserialization.guide.CategoryDeserializer;
import amerifrance.guideapi.deserialization.guide.ElementDeserializer;
import amerifrance.guideapi.deserialization.guide.EntryDeserializer;
import amerifrance.guideapi.deserialization.guide.GuideDeserializer;
import amerifrance.guideapi.deserialization.renderers.*;
import amerifrance.guideapi.guide.Guide;
import amerifrance.guideapi.test.TestGuide;
import com.google.common.io.Resources;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static net.minecraft.util.registry.Registry.ITEM;


public class GuideApi implements ModInitializer {

    public static final String MODID = "guide-api";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public static final List<Guide> GUIDES = newArrayList();
    public static final List<Item> GUIDE_ITEMS = newArrayList();

    @Override
    public void onInitialize() {
        GUIDES.add(TestGuide.TEST_GUIDE_1);

        String json = "";
        try {
            json = Resources.toString(Resources.getResource("test-json-guide.json"), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        DeserializerRegistry.register("GUIDE", new GuideDeserializer());
        DeserializerRegistry.register("CATEGORY", new CategoryDeserializer());
        DeserializerRegistry.register("ENTRY", new EntryDeserializer());
        DeserializerRegistry.register("ELEMENT", new ElementDeserializer());
        DeserializerRegistry.register("LINE_DISPLAY", new LineDisplayDeserializer());
        DeserializerRegistry.register("FIXED_SHAPE_DISPLAY", new FixedShapeDisplayDeserializer());
        DeserializerRegistry.register("TEXT", new StringRendererDeserializer());
        DeserializerRegistry.register("ITEM", new ItemstackRendererDeserializer());
        DeserializerRegistry.register("PAGE_BREAK", new PageBreakRendererDeserializer());
        DeserializerRegistry.register("LINE_BREAK", new LineBreakRendererDeserializer());
        DeserializerRegistry.register("CRAFTING_RECIPE", new CraftingRecipeRendererDeserializer());
        DeserializerRegistry.register("COOKING_RECIPE", new CookingRecipeRendererDeserializer());
        DeserializerRegistry.register("CUTTING_RECIPE", new CuttingRecipeRendererDeserializer());

        GUIDES.add(new GuideDeserializer().deserialize(json));

        GUIDES.forEach(guide -> {
            //TODO allow Guide class to give item
            Item item = new ItemGuide(guide);
            Identifier identifier = new Identifier(MODID, guide.getId());

            Registry.register(ITEM, identifier, item);
            GUIDE_ITEMS.add(item);
        });
    }
}
