package amerifrance.guideapi.proxies;

import amerifrance.guideapi.test.TestBooks;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerBooks() {
        TestBooks.setTestBooks();
    }
}
