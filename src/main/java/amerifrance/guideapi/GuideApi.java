package amerifrance.guideapi;


import amerifrance.guideapi.deserialization.DeserializerRegistry;
import amerifrance.guideapi.guide.Guide;
import amerifrance.guideapi.test.TestGuide;
import amerifrance.guideapi.utils.JsonHelper;
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
        DeserializerRegistry.register();

        registerTestGuides();

        GUIDES.forEach(guide -> {
            //TODO allow Guide class to give item
            Item item = new ItemGuide(guide);
            Identifier identifier = new Identifier(MODID, guide.getId());

            Registry.register(ITEM, identifier, item);
            GUIDE_ITEMS.add(item);
        });
    }

    private void registerTestGuides() {
        GUIDES.add(TestGuide.TEST_GUIDE_1);

        try {
            String json = Resources.toString(Resources.getResource("test-guide.json"), StandardCharsets.UTF_8);
            GUIDES.add((Guide) JsonHelper.deserializeFromJsonString(json));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
