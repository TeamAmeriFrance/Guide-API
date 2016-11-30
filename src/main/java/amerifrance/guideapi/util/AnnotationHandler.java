package amerifrance.guideapi.util;

import amerifrance.guideapi.api.GuideAPI;
import amerifrance.guideapi.api.GuideBook;
import amerifrance.guideapi.api.IGuideBook;
import amerifrance.guideapi.api.impl.Book;
import com.google.common.collect.Lists;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class AnnotationHandler {

    public static final List<Pair<Book, IGuideBook>> BOOK_CLASSES = Lists.newArrayList();

    public static void registerBooks(ASMDataTable dataTable) {
        for (ASMDataTable.ASMData data : dataTable.getAll(GuideBook.class.getCanonicalName())) {
            try {
                Class<?> genericClass = Class.forName(data.getClassName());
                if (!IGuideBook.class.isAssignableFrom(genericClass))
                    return;

                IGuideBook guideBook = (IGuideBook) genericClass.newInstance();
                Book book = guideBook.buildBook();
                if (book == null)
                    continue;
                APISetter.registerBook(book);
                BOOK_CLASSES.add(Pair.of(book, guideBook));
            } catch (Exception e) {
                LogHelper.error("Error registering book for class " + data.getClassName());
            }
        }

        APISetter.setIndexedBooks(Lists.newArrayList(GuideAPI.getBooks().values()));
    }
}
