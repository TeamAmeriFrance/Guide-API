package amerifrance.guideapi.entries;

import amerifrance.guideapi.api.abstraction.IPage;
import amerifrance.guideapi.api.base.EntryBase;

import java.util.List;

/**
 * Use {@link EntryBase} instead. It's the same thing
 */
@Deprecated
public class EntryText extends EntryBase {

    public EntryText(List<IPage> pageList, String unlocEntryName, boolean unicode) {
        super(pageList, unlocEntryName, unicode);
    }

    public EntryText(List<IPage> pageList, String unlocEntryName) {
        this(pageList, unlocEntryName, false);
    }
}
