package amerifrance.guideapi.api;

import amerifrance.guideapi.gui.GuideGui;

import java.util.List;

public interface MultipageProvider<T> {

    List<T> split(GuideGui guideGui, int x, int y);
}
