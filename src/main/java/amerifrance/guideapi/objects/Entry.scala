package amerifrance.guideapi.objects

import java.util

import net.minecraft.util.StatCollector

class Entry(pageList: util.List[Page] = new util.ArrayList[Page], unlocEntryName: String) {

  var pages: util.List[Page] = pageList
  var unlocalizedEntryName: String = unlocEntryName

  def getLocalizedName(): String = {
    return StatCollector.translateToLocal(unlocalizedEntryName)
  }
}
