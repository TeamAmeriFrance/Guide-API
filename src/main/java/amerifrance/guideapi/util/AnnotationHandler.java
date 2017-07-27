package amerifrance.guideapi.util;

import amerifrance.guideapi.api.GuideAPI;
import amerifrance.guideapi.api.GuideBook;
import amerifrance.guideapi.api.IGuideBook;
import amerifrance.guideapi.api.impl.Book;
import com.google.common.collect.Lists;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

public class AnnotationHandler {

    public static final List<Pair<Book, IGuideBook>> BOOK_CLASSES = Lists.newArrayList();

    public static void gatherBooks(ASMDataTable dataTable) {
        for(EventPriority priority : EventPriority.values())
            for (ASMDataTable.ASMData data : dataTable.getAll(GuideBook.class.getCanonicalName())) {
                try {
                    Class<?> genericClass = Class.forName(data.getClassName());
                    if (!IGuideBook.class.isAssignableFrom(genericClass))
                        continue;

                    IGuideBook guideBook = (IGuideBook) genericClass.newInstance();
                    boolean wrongPriority = false;
                    for (Annotation f: genericClass.getAnnotations()) {
                        if(f.annotationType() != GuideBook.class)
                            continue;
                        GuideBook gb = (GuideBook)f;
                        if(gb.priority() != priority)
                            wrongPriority = true;
                    }
                    if(wrongPriority)
                        continue;
                    Book book = guideBook.buildBook();
                    if (book == null)
                        continue;
                    APISetter.registerBook(book);
                    BOOK_CLASSES.add(Pair.of(book, guideBook));
                } catch (Exception e) {
                    LogHelper.error("Error registering book for class " + data.getClassName());
                    e.printStackTrace();
                }
            }

        APISetter.setIndexedBooks(Lists.newArrayList(GuideAPI.getBooks().values()));
    }
}
