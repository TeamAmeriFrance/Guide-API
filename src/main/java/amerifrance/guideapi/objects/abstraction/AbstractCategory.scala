package amerifrance.guideapi.objects.abstraction

import java.util

import amerifrance.guideapi.gui.GuiBase
import amerifrance.guideapi.objects.Book
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.client.renderer.entity.RenderItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.StatCollector

abstract class AbstractCategory(entryList: util.List[AbstractEntry] = new util.ArrayList[AbstractEntry](), unlocCategoryName: String) {

  var entries: util.List[AbstractEntry] = entryList
  var unlocalizedCategoryName: String = unlocCategoryName

  def addEntry(entry: AbstractEntry) = {
    this.entries.add(entry)
  }

  def addEntryList(list: util.List[AbstractEntry]) = {
    this.entries.addAll(list)
  }

  def removeEntry(entry: AbstractEntry) = {
    this.entries.remove(entry)
  }

  def removeCategoryList(list: util.List[AbstractEntry]) = {
    this.entries.removeAll(list)
  }

  def getTooltip: util.List[String] = {
    val list: util.ArrayList[String] = new util.ArrayList[String]
    list.add(getLocalizedName)
    return list
  }

  def getLocalizedName(): String = {
    return StatCollector.translateToLocal(unlocalizedCategoryName)
  }

  def onLeftClicked(book: Book, mouseX: Int, mouseY: Int, player: EntityPlayer, bookStack: ItemStack)

  def onRightClicked(book: Book, mouseX: Int, mouseY: Int, player: EntityPlayer, bookStack: ItemStack)

  @SideOnly(Side.CLIENT)
  def draw(book: Book, categoryX: Int, categoryY: Int, categoryWidth: Int, categoryHeight: Int, mouseX: Int, mouseY: Int, guiBase: GuiBase, drawOnLeft: Boolean, renderItem: RenderItem)

  @SideOnly(Side.CLIENT)
  def drawExtras(book: Book, categoryX: Int, categoryY: Int, categoryWidth: Int, categoryHeight: Int, mouseX: Int, mouseY: Int, guiBase: GuiBase, drawOnLeft: Boolean, renderItem: RenderItem)

  def canSee(player: EntityPlayer, bookStack: ItemStack): Boolean
}
