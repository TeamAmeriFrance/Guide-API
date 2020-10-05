package amerifrance.guideapi.test;

import amerifrance.guideapi.displays.FixedShapeDisplay;
import amerifrance.guideapi.displays.LineDisplay;
import amerifrance.guideapi.guide.Category;
import amerifrance.guideapi.guide.Element;
import amerifrance.guideapi.guide.Entry;
import amerifrance.guideapi.guide.Guide;
import amerifrance.guideapi.renderers.*;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeType;

public class TestGuide {

    public static final String LOREM_IPSUM = "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?";
    public static final Guide TEST_GUIDE_1 = new Guide(
            "first_guide",
            "First test guide",
            guide -> {
                guide.setDisplay(new FixedShapeDisplay<>(guide, true, "X X", " X ", "X X"));
                guide.add(new Category(
                        "first_category",
                        "First test category",
                        new ItemstackRenderer(Items.DIRT, "First test category"),
                        category -> {
                            category.setDisplay(new LineDisplay<>(category));
                            category.add(new Entry(
                                    "first_entry",
                                    "First test entry",
                                    entry -> {
                                        entry.setDisplay(new LineDisplay<>(entry));
                                        entry.add(new Element("first", new ItemstackRenderer(Items.COOKED_BEEF, "Yummy, steak!")));
                                        entry.add(new Element("second", "This is a cooked steak"));
                                        entry.add(new Element("linebreak", new LineBreakRenderer()));
                                        entry.add(new Element("third", "Two lines for our steak! It deserves at least this much."));
                                        entry.add(new Element("pagebreak", new PageBreakRenderer()));
                                        entry.add(new Element("fourth", LOREM_IPSUM));
                                        entry.add(new Element("fifth", new CraftingRecipeRenderer(Items.DIAMOND_PICKAXE)));
                                        entry.add(new Element("sixth", new CraftingRecipeRenderer(Items.ACACIA_PRESSURE_PLATE)));
                                        entry.add(new Element("seventh", new CraftingRecipeRenderer(Items.CRAFTING_TABLE)));
                                        entry.add(new Element("eighth", new CookingRecipeRenderer(Items.CHARCOAL, RecipeType.SMELTING)));
                                        entry.add(new Element("ninth", new CookingRecipeRenderer(Items.GOLD_NUGGET, RecipeType.BLASTING)));
                                        entry.add(new Element("tenth", new CookingRecipeRenderer(Items.COOKED_BEEF, RecipeType.SMOKING)));
                                        entry.add(new Element("eleverth", new CookingRecipeRenderer(Items.COOKED_SALMON, RecipeType.CAMPFIRE_COOKING)));
                                        entry.add(new Element("twelfth", new CuttingRecipeRenderer(Items.STONE_BRICK_STAIRS)));
                                    }
                            ));
                        }
                ));

                guide.add(new Category("second_category",
                        "Second test category",
                        new ItemstackRenderer(Items.OAK_LOG, "Second test category"),
                        category -> category.setDisplay(new LineDisplay<>(category))));

                guide.add(new Category("third_category",
                        "Third test category",
                        new ItemstackRenderer(Items.STONE, "Third test category"),
                        category -> category.setDisplay(new LineDisplay<>(category))));

                guide.add(new Category("fourth_category",
                        "Fourth test category",
                        new ItemstackRenderer(Items.GOLD_BLOCK, "Fourth test category"),
                        category -> category.setDisplay(new LineDisplay<>(category))));

                guide.add(new Category("fifth_category",
                        "Fifth test category",
                        new ItemstackRenderer(Items.DIAMOND_BLOCK, "Fifth test category"),
                        category -> category.setDisplay(new LineDisplay<>(category))));
            });
}