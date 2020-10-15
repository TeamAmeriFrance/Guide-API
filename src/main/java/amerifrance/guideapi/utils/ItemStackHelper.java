package amerifrance.guideapi.utils;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.arguments.ItemStackArgument;
import net.minecraft.command.arguments.ItemStringReader;
import net.minecraft.item.ItemStack;

public class ItemStackHelper {

    public static ItemStack itemStackFromString(String string) {
        try {
            ItemStringReader reader = new ItemStringReader(new StringReader(string), true).consume();
            ItemStackArgument itemStackArgument = new ItemStackArgument(reader.getItem(), reader.getTag());

            return itemStackArgument.createStack(1, false);
        } catch (CommandSyntaxException commandSyntaxException) {
            commandSyntaxException.printStackTrace();
        }

        return ItemStack.EMPTY;
    }
}
