package amerifrance.guideapi.guide;

import amerifrance.guideapi.api.*;
import amerifrance.guideapi.gui.GuideGui;
import amerifrance.guideapi.renderers.Renderer;
import com.google.common.collect.Lists;
import net.minecraft.text.StringRenderable;

import java.util.Collections;
import java.util.List;

public class Element implements IdProvider, TextProvider, ChildOf<Entry>, RendererProvider<Element>, MultipageProvider<Element> {

    private final String id;
    private final String name;
    private final Entry entry;
    private final Renderer<Element> renderer;

    public Element(String id, String name, Entry entry, Renderer<Element> renderer) {
        this.id = id;
        this.name = name;
        this.entry = entry;
        this.renderer = renderer;
    }

    public Element(String id, Entry entry, Renderer<Element> renderer) {
        this(id, "", entry, renderer);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getText() {
        return name;
    }

    @Override
    public Entry getParent() {
        return entry;
    }

    @Override
    public Renderer<Element> getRenderer() {
        return renderer;
    }

    @Override
    public ViewingRequirement getViewingRequirement() {
        return () -> true;
    }

    @Override
    public List<Element> split(GuideGui guideGui, int x, int y) {
        if (y + getRenderer().getArea(this, guideGui).getHeight() > guideGui.getDrawEndHeight()) {
            int yPos = y + guideGui.getFontHeight();
            StringBuilder text = new StringBuilder();
            List<Element> list = Lists.newArrayList();

            for (StringRenderable line : guideGui.wrapLines(getText(), guideGui.getGuiWidth())) {
                yPos += guideGui.getFontHeight();

                if (yPos > guideGui.getDrawEndHeight()) {
                    list.add(new Element(id + "_" + list.size(), text.toString(), entry, renderer));

                    text = new StringBuilder();
                    yPos = guideGui.getDrawStartHeight();
                }

                text.append(line.getString());
            }

            list.add(new Element(id + "_" + list.size(), text.toString(), entry, renderer));

            return list;
        }

        return Collections.singletonList(this);
    }
}
