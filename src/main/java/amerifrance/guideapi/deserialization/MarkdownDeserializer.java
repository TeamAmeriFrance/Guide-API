package amerifrance.guideapi.deserialization;

import amerifrance.guideapi.api.Deserializer;
import amerifrance.guideapi.api.RegisterDeserializer;
import amerifrance.guideapi.displays.LineDisplay;
import amerifrance.guideapi.guide.Category;
import amerifrance.guideapi.guide.Element;
import amerifrance.guideapi.guide.Entry;
import amerifrance.guideapi.guide.Guide;
import amerifrance.guideapi.renderers.CookingRecipeRenderer;
import amerifrance.guideapi.renderers.CraftingRecipeRenderer;
import amerifrance.guideapi.renderers.ItemstackRenderer;
import amerifrance.guideapi.renderers.LineBreakRenderer;
import com.google.common.collect.Lists;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.arguments.ItemStackArgument;
import net.minecraft.command.arguments.ItemStringReader;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeType;
import org.commonmark.ext.front.matter.YamlFrontMatterExtension;
import org.commonmark.ext.front.matter.YamlFrontMatterVisitor;
import org.commonmark.ext.image.attributes.ImageAttributesExtension;
import org.commonmark.node.Code;
import org.commonmark.node.Heading;
import org.commonmark.node.Node;
import org.commonmark.node.Text;
import org.commonmark.parser.Parser;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RegisterDeserializer("MARKDOWN")
public class MarkdownDeserializer implements Deserializer {
    private static final Parser PARSER = Parser.builder()
            .extensions(Lists.newArrayList(
                    YamlFrontMatterExtension.create(),
                    ImageAttributesExtension.create()))
            .build();

    @Override
    public Object deserialize(String value, Object... initParameters) {
        Node document = PARSER.parse(value);

        YamlFrontMatterVisitor yamlFrontMatterVisitor = new YamlFrontMatterVisitor();
        document.accept(yamlFrontMatterVisitor);

        int counter = 0;

        Guide currentGuide = null;
        Category currentCategory = null;
        Entry currentEntry = null;

        Node node = document.getFirstChild();
        while (node.getNext() != null) {
            node = node.getNext();

            if (node instanceof Heading) {
                Heading heading = (Heading) node;
                Text text = (Text) heading.getFirstChild();
                String id = text.getLiteral().toLowerCase().replace(" ", "_");
                String name = text.getLiteral();

                switch (heading.getLevel()) {
                    case 1:
                        currentGuide = new Guide(id, name, guide -> guide.setDisplay(new LineDisplay<>(guide)));
                        break;
                    case 2:
                        currentCategory = new Category(id, name, category -> category.setDisplay(new LineDisplay<>(category)));
                        currentGuide.add(currentCategory);
                        break;
                    case 3:
                        currentEntry = new Entry(id, name, entry -> entry.setDisplay(new LineDisplay<>(entry)));
                        currentCategory.add(currentEntry);
                        break;
                }
            } else {
                for (Node child : getAllChildren(node)) {
                    if (child instanceof Code) {
                        Code code = (Code) child;
                        String id = code.getLiteral().toLowerCase().replace(" ", "_");
                        String name = code.getLiteral();

                        Element e = new Element(id, name);

                        String type = typeFromString(code.getLiteral(), "^\\[(.*)]\\(.*\\)$");

                        if (!type.isEmpty()) {
                            Matcher matcher = Pattern.compile("^\\[.*]\\((.*)\\)$").matcher(code.getLiteral());

                            if (matcher.find()) {
                                ItemStack itemStack = itemStackFromString(matcher.group(1));

                                if (type.equalsIgnoreCase("item")) {
                                    e = new Element(id, new ItemstackRenderer(itemStack));
                                } else if (type.equalsIgnoreCase("crafting")) {
                                    e = new Element(id, new CraftingRecipeRenderer(itemStack.getItem()));
                                } else if (type.equalsIgnoreCase("smelting")) {
                                    e = new Element(id, new CookingRecipeRenderer(itemStack.getItem(), RecipeType.SMELTING));
                                }
                            }
                        }

                        currentEntry.add(e);
                    }

                    if (child instanceof Text) {
                        Text text = (Text) child;
                        String id = text.getLiteral().toLowerCase().replace(" ", "_");
                        String name = text.getLiteral();

                        currentEntry.add(new Element(id, name));
                    }

                    counter++;
                }

                currentEntry.add(new Element("break_" + counter, new LineBreakRenderer()));
            }

            counter++;
        }

        return currentGuide;
    }

    private List<Node> getAllChildren(Node node) {
        List<Node> children = Lists.newArrayList();

        Node child = node.getFirstChild();
        while (child.getNext() != null) {
            children.add(child);
            child = child.getNext();
        }
        children.add(child);

        return children;
    }

    private String typeFromString(String string, String regex) {
        Matcher matcher = Pattern.compile(regex).matcher(string);

        return matcher.find() ? matcher.group(1) : "";
    }

    private ItemStack itemStackFromString(String string) {
        try {
            ItemStringReader reader = new ItemStringReader(new StringReader(string), true).consume();
            ItemStackArgument itemStackArgument = new ItemStackArgument(reader.getItem(), reader.getTag());

            return itemStackArgument.createStack(1, false);
        } catch (CommandSyntaxException commandSyntaxException) {
            commandSyntaxException.printStackTrace();
        }

        return ItemStack.EMPTY;
    }
}
