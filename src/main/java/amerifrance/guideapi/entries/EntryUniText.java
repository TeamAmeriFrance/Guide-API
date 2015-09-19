package amerifrance.guideapi.entries;

import amerifrance.guideapi.api.abstraction.IPage;
import amerifrance.guideapi.api.base.EntryBase;

import java.util.List;

/**
 * Use one of the other constructors in {@link EntryBase}, {@link EntryItemStack}, or {@link EntryResourceLocation}
 */
@Deprecated
public class EntryUniText extends EntryBase {

    public EntryUniText(List<IPage> pageList, String unlocEntryName) {
        super(pageList, unlocEntryName, true);
    }
}
