package amerifrance.guideapi.test;

import amerifrance.guideapi.displays.LineDisplay;
import amerifrance.guideapi.guide.Category;
import amerifrance.guideapi.guide.Element;
import amerifrance.guideapi.guide.Entry;
import amerifrance.guideapi.guide.Guide;
import amerifrance.guideapi.renderers.CookingRecipeRenderer;
import amerifrance.guideapi.renderers.CraftingRecipeRenderer;
import amerifrance.guideapi.renderers.ItemstackRenderer;
import amerifrance.guideapi.renderers.StringRenderer;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeType;

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
                        new StringRenderer<>(),
                        category -> {
                            category.setDisplay(new LineDisplay<>(category));
                            category.add(new Entry(
                                    "first_entry",
                                    "First test entry",
                                    category,
                                    new StringRenderer<>(),
                                    entry -> {
                                        entry.setDisplay(new LineDisplay<>(entry));
                                        entry.add(new Element(
                                                "first_element",
                                                "Yummy, steak!",
                                                entry,
                                                new ItemstackRenderer<>(Items.COOKED_BEEF)
                                        ));
                                        entry.add(new Element(
                                                "second_element",
                                                "This is a cooked steak",
                                                entry,
                                                new StringRenderer<>()
                                        ));
                                        entry.add(new Element(
                                                "third_element",
                                                "Two lines for our steak! It deserves at least this much.",
                                                entry,
                                                new StringRenderer<>()
                                        ));
                                        entry.add(new Element(
                                                "fourth_element",
                                                "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?",
                                                entry,
                                                new StringRenderer<>()
                                        ));
                                        entry.add(new Element(
                                                "fifth_element",
                                                entry,
                                                new CraftingRecipeRenderer<>(Items.DIAMOND_PICKAXE)
                                        ));
                                        entry.add(new Element(
                                                "sixth_element",
                                                entry,
                                                new CraftingRecipeRenderer<>(Items.ACACIA_PRESSURE_PLATE)
                                        ));
                                        entry.add(new Element(
                                                "seventh_element",
                                                entry,
                                                new CraftingRecipeRenderer<>(Items.CRAFTING_TABLE)
                                        ));
                                        entry.add(new Element(
                                                "eighth_element",
                                                entry,
                                                new CookingRecipeRenderer<>(Items.CHARCOAL, RecipeType.SMELTING)
                                        ));
                                        entry.add(new Element(
                                                "ninth_element",
                                                entry,
                                                new CookingRecipeRenderer<>(Items.GOLD_NUGGET, RecipeType.BLASTING)
                                        ));
                                        entry.add(new Element(
                                                "tenth_element",
                                                entry,
                                                new CookingRecipeRenderer<>(Items.COOKED_BEEF, RecipeType.SMOKING)
                                        ));
                                        entry.add(new Element(
                                                "eleverth_element",
                                                entry,
                                                new CookingRecipeRenderer<>(Items.COOKED_SALMON, RecipeType.CAMPFIRE_COOKING)
                                        ));
                                    }
                            ));
                        }
                ));
                guide.add(new Category(
                        "second_category",
                        "Second test category",
                        guide,
                        new ItemstackRenderer<>(Items.DIAMOND_BLOCK),
                        category -> category.setDisplay(new LineDisplay<>(category))
                ));
            });
}