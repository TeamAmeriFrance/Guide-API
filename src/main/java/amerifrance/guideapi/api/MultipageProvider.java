package amerifrance.guideapi.api;

import amerifrance.guideapi.gui.GuideGui;

import java.util.List;

public interface MultipageProvider {
    List<Renderer> split(GuideGui guideGui, int x, int y);
}
