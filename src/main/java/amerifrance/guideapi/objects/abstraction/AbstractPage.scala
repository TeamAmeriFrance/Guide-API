package amerifrance.guideapi.objects.abstraction

import amerifrance.guideapi.gui.{GuiBase, GuiEntry}
import amerifrance.guideapi.objects.Book
import net.minecraft.client.gui.FontRenderer
import net.minecraft.entity.player.EntityPlayer

abstract class AbstractPage() {

  def draw(book: Book, category: AbstractCategory, entry: AbstractEntry, guiLeft: Int, guiTop: Int, mouseX: Int, mouseY: Int, guiBase: GuiBase, fontRenderer: FontRenderer)

  def drawExtras(book: Book, category: AbstractCategory, entry: AbstractEntry, guiLeft: Int, guiTop: Int, mouseX: Int, mouseY: Int, guiBase: GuiBase, fontRenderer: FontRenderer)

  def canSee(player: EntityPlayer): Boolean

  def onLeftClicked(book: Book, category: AbstractCategory, entry: AbstractEntry, mouseX: Int, mouseY: Int, player: EntityPlayer, guiEntry: GuiEntry)

  def onRightClicked(book: Book, category: AbstractCategory, entry: AbstractEntry, mouseX: Int, mouseY: Int, player: EntityPlayer, guiEntry: GuiEntry)
}
