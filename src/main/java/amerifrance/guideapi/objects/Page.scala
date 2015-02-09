package amerifrance.guideapi.objects

import net.minecraft.client.Minecraft

class Page(ts: String) {

  var text: String = ts

  def drawPage(guiLeft: Int, guiTop: Int) = {
    Minecraft.getMinecraft.fontRenderer.drawString(text, guiLeft, guiTop, 0)
  }
}
