package amerifrance.guideapi.util.serialization;

import amerifrance.guideapi.entries.EntryText;
import amerifrance.guideapi.interfaces.IEntrySerializing;
import amerifrance.guideapi.interfaces.ITypeReader;
import amerifrance.guideapi.objects.EntryBase;
import amerifrance.guideapi.objects.abstraction.EntryAbstract;
import amerifrance.guideapi.objects.abstraction.IPage;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class EntryTypeReader<T extends EntryAbstract> implements ITypeReader<T> {


//
//    public static void init() {
//        BookCreator.addEntrySerializingToMap(EntryBase.class, entryBaseSerialization);
//        BookCreator.addEntrySerializingToMap(EntryText.class, entryTextSerialization);
//    }
}
