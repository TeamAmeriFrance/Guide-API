package amerifrance.guideapi;

import amerifrance.guideapi.gui.GuideGui;
import amerifrance.guideapi.guide.Guide;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import static net.minecraft.item.ItemGroup.MISC;

public class ItemGuide extends Item {

    private Guide guide;

    public ItemGuide(Guide guide) {
        super(new Settings().group(MISC).maxCount(1));

        this.guide = guide;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (world.isClient)
            MinecraftClient.getInstance().openScreen(new GuideGui(guide));

        return new TypedActionResult<>(ActionResult.SUCCESS, player.getStackInHand(hand));
    }

    @Override
    public Text getName() {
        return new LiteralText(guide.getText());
    }
}
