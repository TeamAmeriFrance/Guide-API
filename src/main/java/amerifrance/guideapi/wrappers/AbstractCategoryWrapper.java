package amerifrance.guideapi.wrappers;

import amerifrance.guideapi.objects.Category;

public abstract class AbstractCategoryWrapper extends AbstractWrapper {

    public Category category;

    public AbstractCategoryWrapper(Category category) {
        this.category = category;
    }
}
