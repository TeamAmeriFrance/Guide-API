package amerifrance.guideapi.objects

import java.util

import net.minecraft.util.StatCollector

class Book(categories: util.List[Category] = new util.ArrayList[Category](), unlocTitle: String) {

  def addCategory(category: Category) = {
    this.categories.add(category)
  }

  def addCategoryList(list: util.List[Category]) = {
    this.categories.addAll(list)
  }

  def removeCategory(category: Category) = {
    this.categories.remove(category)
  }

  def removeCategoryList(list: util.List[Category]) = {
    this.categories.remove(list)
  }

  def getLocalizedName(): String = {
    return StatCollector.translateToLocal(unlocTitle)
  }
}
