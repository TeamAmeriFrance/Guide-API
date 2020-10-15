package amerifrance.guideapi.deserialization.markdown;

import amerifrance.guideapi.api.Deserializer;
import amerifrance.guideapi.api.RegisterDeserializer;
import amerifrance.guideapi.api.Renderer;
import amerifrance.guideapi.deserialization.DeserializerRegistry;
import amerifrance.guideapi.displays.LineDisplay;
import amerifrance.guideapi.guide.Category;
import amerifrance.guideapi.guide.Element;
import amerifrance.guideapi.guide.Entry;
import amerifrance.guideapi.guide.Guide;
import amerifrance.guideapi.renderers.LineBreakRenderer;
import com.google.common.collect.Lists;
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
    private static final Parser PARSER = Parser.builder().build();

    @Override
    public Object deserialize(String value, Object... initParameters) {
        Node currentNode = PARSER.parse(value).getFirstChild();
        Guide currentGuide = null;
        Category currentCategory = null;
        Entry currentEntry = null;

        int elementId = 0;
        while (currentNode != null) {
            if (currentNode instanceof Heading) {
                Heading heading = (Heading) currentNode;
                Text text = (Text) heading.getFirstChild();
                String id = text.getLiteral().toLowerCase().replace(" ", "_");
                String name = text.getLiteral();

                int level = heading.getLevel();
                switch (level) {
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
                for (Node child : getAllChildren(currentNode)) {
                    if (child instanceof Code)
                        currentEntry.add(deserializeCustomElement(elementId, (Code) child));
                    else if (child instanceof Text)
                        currentEntry.add(new Element(String.valueOf(elementId), ((Text) child).getLiteral()));

                    elementId++;
                }

                currentEntry.add(new Element("break_" + elementId, new LineBreakRenderer()));
            }

            elementId++;
            currentNode = currentNode.getNext();
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

    private Element deserializeCustomElement(int elementId, Code child) {
        String content = child.getLiteral();
        Element element = new Element(String.valueOf(elementId), content);

        String type = typeFromString(content, "^\\[(.*)]\\(.*\\)$");
        if (!type.isEmpty()) {
            Matcher matcher = Pattern.compile("^\\[.*]\\((.*)\\)$").matcher(content);

            if (matcher.find()) {
                Renderer renderer = (Renderer) DeserializerRegistry.get(type + "-MARKDOWN").deserialize(content);
                element = new Element(String.valueOf(elementId), renderer);
            }
        }
        return element;
    }

    private String typeFromString(String string, String regex) {
        Matcher matcher = Pattern.compile(regex).matcher(string);

        return matcher.find() ? matcher.group(1) : "";
    }
}
