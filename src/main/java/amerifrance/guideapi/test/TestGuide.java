package amerifrance.guideapi.test;

import amerifrance.guideapi.displays.LineDisplay;
import amerifrance.guideapi.guide.*;
import amerifrance.guideapi.renderers.IdTextRenderer;
import amerifrance.guideapi.renderers.ItemstackRenderer;
import net.minecraft.item.Items;

public class TestGuide {

    public static final Guide TEST_GUIDE_1 = new Guide(
            "first_guide",
            "First test guide",
            guide -> {
                guide.setDisplay(new LineDisplay<>(guide));
                guide.add(new Category(
                        "first_category",
                        "First test category",
                        guide,
                        new IdTextRenderer<>(),
                        category -> {
                            category.setDisplay(new LineDisplay<>(category));
                            category.add(new Entry(
                                    "first_entry",
                                    "First test entry",
                                    category,
                                    new IdTextRenderer<>(),
                                    entry -> {
                                        entry.setDisplay(new LineDisplay<>(entry));
                                        entry.add(new Page(
                                                "first_page",
                                                "First test page",
                                                entry,
                                                new IdTextRenderer<>(),
                                                page -> {
                                                    page.setDisplay(new TestPageDisplay(page));
                                                    page.add(new Element(
                                                            "first_element",
                                                            "Yummy, steak!",
                                                            page,
                                                            new ItemstackRenderer<>(Items.COOKED_BEEF)
                                                    ));
                                                    page.add(new Element(
                                                            "second_element",
                                                            "This is a cooked steak",
                                                            page,
                                                            new IdTextRenderer<>()
                                                    ));
                                                    page.add(new Element(
                                                            "third_element",
                                                            "Two lines for our steak! It deserves at least this much.",
                                                            page,
                                                            new IdTextRenderer<>()
                                                    ));
                                                }
                                        ));
                                    }
                            ));
                        }
                ));
                guide.add(new Category(
                        "second_category",
                        "Second test category",
                        guide,
                        new ItemstackRenderer<>(Items.DIAMOND_PICKAXE),
                        category -> {
                            category.setDisplay(new LineDisplay<>(category));
                        }
                ));
            });
}