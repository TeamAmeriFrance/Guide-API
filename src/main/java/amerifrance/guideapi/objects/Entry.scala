package amerifrance.guideapi.objects

import net.minecraft.util.StatCollector

class Entry(unlocEntryName: String) {

  def drawEntry() = {
  }

  def getLocalizedName(): String = {
    return StatCollector.translateToLocal(unlocEntryName)
  }
}
