package amerifrance.guideapi.deserialization;

import amerifrance.guideapi.api.Deserializer;
import amerifrance.guideapi.api.RegisterDeserializer;
import amerifrance.guideapi.displays.LineDisplay;
import amerifrance.guideapi.guide.Category;
import amerifrance.guideapi.guide.Element;
import amerifrance.guideapi.guide.Entry;
import amerifrance.guideapi.guide.Guide;
import amerifrance.guideapi.renderers.CraftingRecipeRenderer;
import amerifrance.guideapi.renderers.ItemstackRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

@RegisterDeserializer("HTML")
public class HtmlDeserializer implements Deserializer {

    @Override
    public Object deserialize(String value, Object... initParameters) {
        Document document = Jsoup.parse(value);

        Guide currentGuide = null;
        Category currentCategory = null;
        Entry currentEntry = null;

        for (org.jsoup.nodes.Element element : document.body().children()) {
            String id = element.text().toLowerCase().replace(" ", "_");
            String name = element.text();

            if (!id.isEmpty()) {
                if (element.is("h1")) {
                    currentGuide = new Guide(id, name, guide -> guide.setDisplay(new LineDisplay<>(guide)));
                } else if (element.is("h2")) {
                    currentCategory = new Category(id, name, category -> category.setDisplay(new LineDisplay<>(category)));
                    currentGuide.add(currentCategory);
                } else if (element.is("h3")) {
                    currentEntry = new Entry(id, name, entry -> entry.setDisplay(new LineDisplay<>(entry)));
                    currentCategory.add(currentEntry);
                } else {
                    Element e = new Element(id, name);
                    if (name.matches("/i/.*/i/")) {
                        String itemId = name.replace("/", "");
                        e = new Element(id, name, new ItemstackRenderer(Registry.ITEM.get(new Identifier(itemId))));
                    } else if (name.matches("/r/.*/r/")) {
                        String itemId = name.replace("/", "");
                        e = new Element(id, name, new CraftingRecipeRenderer(Registry.ITEM.get(new Identifier(itemId))));
                    }

                    currentEntry.add(e);
                }
            }
        }

        return currentGuide;
    }
}
