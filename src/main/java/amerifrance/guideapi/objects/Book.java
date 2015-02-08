package amerifrance.guideapi.objects;

import java.util.ArrayList;
import java.util.List;

public class Book {

    public List<Category> categories;

    public Book() {
        this.categories = new ArrayList<Category>();
    }

    public Book(List<Category> categoryList) {
        this.categories = categoryList;
    }
}
