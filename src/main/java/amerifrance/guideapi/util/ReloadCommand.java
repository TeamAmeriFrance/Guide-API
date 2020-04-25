package amerifrance.guideapi.util;

import amerifrance.guideapi.api.GuideAPI;
import amerifrance.guideapi.api.impl.Book;
import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.ResourceLocationArgument;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;

/**
 * Command to be able to reload the book content ingame for debugging purposes.
 * Only registered in dev environment and only works in single player
 */
public class ReloadCommand {

    private static final DynamicCommandExceptionType BOOK_NOT_FOUND = new DynamicCommandExceptionType((id) -> new StringTextComponent("Book with registry id " + id + " not found"));
    private static final SimpleCommandExceptionType NOT_CLIENT = new SimpleCommandExceptionType(new LiteralMessage("This command can only be used in singleplayer"));

    public static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("reload")
                .then(Commands.argument("bookid", ResourceLocationArgument.resourceLocation()).executes((context) -> {
                    if (FMLEnvironment.dist != Dist.CLIENT) {
                        throw NOT_CLIENT.create();
                    }
                    ResourceLocation id = ResourceLocationArgument.getResourceLocation(context, "bookid");
                    Book b = GuideAPI.getBooks().get(id);
                    if (b == null) {
                        throw BOOK_NOT_FOUND.create(id.toString());
                    }
                    b.forceInitializeContent();
                    return 0;
                }));

    }
}
