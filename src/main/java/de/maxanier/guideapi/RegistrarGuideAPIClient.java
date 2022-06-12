package de.maxanier.guideapi;

import de.maxanier.guideapi.api.GuideAPI;
import de.maxanier.guideapi.api.IGuideBook;
import de.maxanier.guideapi.api.impl.Book;
import de.maxanier.guideapi.util.AnnotationHandler;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ForgeModelBakery;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.tuple.Pair;


@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = GuideMod.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistrarGuideAPIClient {
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void registerModels(ModelRegistryEvent event) {
        for (Pair<Book, IGuideBook> guide : AnnotationHandler.BOOK_CLASSES) {
            ResourceLocation loc = guide.getRight().getModel();
            if (loc != null) {
                ForgeModelBakery.addSpecialModel(new ModelResourceLocation(loc, "inventory"));
            }
        }
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void bakeModel(ModelBakeEvent event) {
        for (Pair<Book, IGuideBook> guide : AnnotationHandler.BOOK_CLASSES) {
            ResourceLocation loc = guide.getRight().getModel();
            if (loc != null) {
                ModelResourceLocation newMrl = new ModelResourceLocation(loc, "inventory");
                Item bookItem = GuideAPI.getStackFromBook(guide.getLeft()).getItem();
                ModelResourceLocation oldMrl = new ModelResourceLocation(ForgeRegistries.ITEMS.getKey(bookItem), "inventory");
                BakedModel model = event.getModelRegistry().get(newMrl);

                event.getModelRegistry().put(oldMrl, model);

            }
        }

    }
}
