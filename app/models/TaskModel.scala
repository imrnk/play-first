package models

import scala.collection.mutable

object TaskModel {
  private val tasks = mutable.Map[String, List[String]]("Imran" -> List("Watch videos", "Code","Sleep"),
  "Dariya" -> List("Study", "School", "Sleep"),
  "Sahana" -> List("Counsel", "Dance", "Sleep"))

  def addTask(userName: String, task: String): Boolean = {
    tasks(userName) = task :: tasks.get(userName).getOrElse(Nil)
    true
  }

  def createTask(userName: String, task: String*) : Boolean = {
    tasks += (userName -> List(task:_*))
    true
  }

  def deleteTask(userName: String, task: String) : Boolean = {
    tasks.get(userName) match {
      case None => false
      case Some(_) =>
        tasks(userName) = tasks(userName).filterNot(_ == task)
      println(tasks)
      true
    }
  }

  def getTasks(userName: String) : List[String] = tasks.get(userName).getOrElse(Nil)


}
