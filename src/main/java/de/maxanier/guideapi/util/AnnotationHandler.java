package de.maxanier.guideapi.util;

import com.google.common.collect.Lists;
import de.maxanier.guideapi.api.GuideAPI;
import de.maxanier.guideapi.api.GuideBook;
import de.maxanier.guideapi.api.IGuideBook;
import de.maxanier.guideapi.api.impl.Book;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.ModFileScanData;
import org.apache.commons.lang3.tuple.Pair;
import org.objectweb.asm.Type;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AnnotationHandler {

    public static final List<Pair<Book, IGuideBook>> BOOK_CLASSES = Lists.newArrayList();

    private static final Type GUIDE = Type.getType(GuideBook.class);


    public static void gatherBooks() {
        final List<ModFileScanData.AnnotationData> annotations = ModList.get().getAllScanData().stream()
                .map(ModFileScanData::getAnnotations)
                .flatMap(Collection::stream)
                .filter(a -> GUIDE.equals(a.getAnnotationType()))
                .collect(Collectors.toList());


        for (EventPriority priority : EventPriority.values())
            for (ModFileScanData.AnnotationData data : annotations) {
                try {

                    EventPriority priority1 = (EventPriority) data.getAnnotationData().getOrDefault("priority", EventPriority.NORMAL);
                    if (priority != priority1) continue;
                    Class<?> genericClass = Class.forName(data.getClassType().getClassName());
                    if (!IGuideBook.class.isAssignableFrom(genericClass))
                        continue;
                    IGuideBook guideBook = (IGuideBook) genericClass.newInstance();
                    Book book = guideBook.buildBook();
                    if (book == null)
                        continue;
                    APISetter.registerBook(book);
                    BOOK_CLASSES.add(Pair.of(book, guideBook));

                } catch (Exception e) {
                    LogHelper.error("Error registering book for class " + data.getClassType().getClassName());
                    e.printStackTrace();
                }

            }


        APISetter.setIndexedBooks(Lists.newArrayList(GuideAPI.getBooks().values()));
    }
}
