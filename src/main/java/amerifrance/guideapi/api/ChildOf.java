package amerifrance.guideapi.api;

public interface ChildOf<T> {
    T getParent();

    void setParent(T parent);
}
