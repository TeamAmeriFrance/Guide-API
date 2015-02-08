package amerifrance.guideapi.wrappers;

import amerifrance.guideapi.objects.Category;
import amerifrance.guideapi.objects.Entry;

public abstract class AbstractEntryWrapper extends AbstractWrapper {

    public Entry entry;
    public Category category;

    public AbstractEntryWrapper(Entry entry, Category category) {
        this.entry = entry;
        this.category = category;
    }
}
