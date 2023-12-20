package services

import models.User

import scala.collection.mutable

class UserService {

  private val users: mutable.Map[Long, User] = mutable.Map[Long, User](1L -> User(Some(1L), "Imran", "pass"),
                                                                               2L -> User(Some(2L), "Dariya", "pass2"),
                                                                               3L -> User(Some(3L), "Sahana", "pass3"))

  def userExists(user: User) : Option[User] = users.toSeq.map(_._2).filter(u => u.username == user.username && u.password == user.password).headOption


  def createUser(user: User) : Option[User] = userExists(user) match {
    case Some(u) => None
    case None => {
       val newUserId = users.keySet.max + 1
       users += (newUserId -> User(Some(newUserId), user.username, user.password))
       getUser(newUserId)
    }
  }

  def getUser(userId: Long) : Option[User] = users.get(userId)

  def getUser(userName: String, password: String) : Option[User] = userExists(User(None, userName, password))

}
