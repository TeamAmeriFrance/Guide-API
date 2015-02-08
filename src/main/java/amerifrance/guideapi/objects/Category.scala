package amerifrance.guideapi.objects

import java.util

class Category(entries: util.List[Entry] = new util.ArrayList[Entry](), unlocCategoryName: String) {

  def addEntry(entry: Entry) = {
    this.entries.add(entry)
  }

  def addEntryList(list: util.List[Entry]) = {
    this.entries.addAll(list)
  }

  def removeEntry(entry: Entry) = {
    this.entries.remove(entry)
  }

  def removeCategoryList(list: util.List[Entry]) = {
    this.entries.remove(list)
  }
}
