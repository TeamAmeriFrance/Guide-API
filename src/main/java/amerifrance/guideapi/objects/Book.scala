package amerifrance.guideapi.objects

import java.util

import net.minecraft.util.StatCollector

class Book(categoryList: util.List[Category] = new util.ArrayList[Category](), unlocTitle: String) {

  var categories: util.List[Category] = categoryList
  var unlocalizedTitle: String = unlocTitle

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
    return StatCollector.translateToLocal(unlocalizedTitle)
  }
}
