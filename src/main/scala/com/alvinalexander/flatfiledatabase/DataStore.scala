package com.alvinalexander.flatfiledatabase

import scala.io.Source
import scala.collection.mutable.ArrayBuffer
import java.io.{File, BufferedWriter, FileWriter}

class DataStore(val dataFile: String) {

  private val items = new ArrayBuffer[String]()

  def getAllItems(): List[String] = {
    if ((new File(dataFile).exists())) {
      val stocksFromFile = getLinesFromFile(dataFile)
      items.appendAll(stocksFromFile)
      items.toList
    } else {
      Nil
    }
  }

  def addItem(item: String) {
    items += item
    saveItems()
  }

  def removeItem(item: String) {
    items -= item
    saveItems()
  }

  private def saveItems() {
    val file = new File(dataFile)
    val bw = new BufferedWriter(new FileWriter(file))
    for (item <- items.sorted) bw.write(s"$item\n")
    bw.close()
  }

  // TODO move this to a FileUtils class
  private def getLinesFromFile(file: String): List[String] = {
    val bufferedSource = Source.fromFile(file)
    val records = (for (line <- bufferedSource.getLines) yield line).toList
    bufferedSource.close
    records
  }

}