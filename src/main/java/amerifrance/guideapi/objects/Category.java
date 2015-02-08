package amerifrance.guideapi.objects;

import java.util.ArrayList;
import java.util.List;

public class Category {

    public List<Entry> entries;

    public Category() {
        this.entries = new ArrayList<Entry>();
    }

    public Category(List<Entry> entryList) {
        this.entries = entryList;
    }
}
