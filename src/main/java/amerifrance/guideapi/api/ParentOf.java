package amerifrance.guideapi.api;

import java.util.List;

public interface ParentOf<T> {
    List<T> getChildren();

    void add(T child);

    T getChild(String id);
}
