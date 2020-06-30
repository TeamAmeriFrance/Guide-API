package amerifrance.guideapi.api;

import amerifrance.guideapi.displays.Display;

public interface DisplayProvider {
    Display getDisplay();

    void setDisplay(Display display);
}
