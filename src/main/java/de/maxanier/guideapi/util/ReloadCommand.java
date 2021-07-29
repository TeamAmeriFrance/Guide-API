package de.maxanier.guideapi.util;

import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import de.maxanier.guideapi.api.GuideAPI;
import de.maxanier.guideapi.api.impl.Book;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;

/**
 * Command to be able to reload the book content ingame for debugging purposes.
 * Only registered in dev environment and only works in single player
 */
public class ReloadCommand {

    private static final DynamicCommandExceptionType BOOK_NOT_FOUND = new DynamicCommandExceptionType((id) -> new TextComponent("Book with registry id " + id + " not found"));
    private static final SimpleCommandExceptionType NOT_CLIENT = new SimpleCommandExceptionType(new LiteralMessage("This command can only be used in singleplayer"));

    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("reload")
                .then(Commands.argument("bookid", ResourceLocationArgument.id()).executes((context) -> {
                    if (FMLEnvironment.dist != Dist.CLIENT) {
                        throw NOT_CLIENT.create();
                    }
                    ResourceLocation id = ResourceLocationArgument.getId(context, "bookid");
                    Book b = GuideAPI.getBooks().get(id);
                    if (b == null) {
                        throw BOOK_NOT_FOUND.create(id.toString());
                    }
                    b.forceInitializeContent();
                    return 0;
                }));

    }
}
