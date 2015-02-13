package amerifrance.guideapi.objects

import java.awt.Color
import java.util

import amerifrance.guideapi.objects.abstraction.AbstractCategory
import net.minecraft.util.StatCollector

class Book(categoryList: util.List[AbstractCategory] = new util.ArrayList[AbstractCategory](), unlocTitle: String, bookColor: Color = new Color(171, 80, 30), unlocWelcomeMessage: String) {

  var categories: util.List[AbstractCategory] = categoryList
  var unlocalizedTitle: String = unlocTitle
  var color: Color = bookColor
  var welcomeMessage: String = unlocWelcomeMessage

  def addCategory(category: AbstractCategory) = {
    this.categories.add(category)
  }

  def addCategoryList(list: util.List[AbstractCategory]) = {
    this.categories.addAll(list)
  }

  def removeCategory(category: AbstractCategory) = {
    this.categories.remove(category)
  }

  def removeCategoryList(list: util.List[AbstractCategory]) = {
    this.categories.remove(list)
  }

  def getLocalizedName(): String = {
    return StatCollector.translateToLocal(unlocalizedTitle)
  }

  def getLocalizedWelcomMessage(): String = {
    return StatCollector.translateToLocal(welcomeMessage)
  }
}
