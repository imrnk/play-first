package models

import services.UserService

case class User(userId: Option[Long] = None, username: String, password: String)

object UserModel {

  private val userService = new UserService()

  def validateUser(username: String, password: String) : Option[User] = userService.userExists(User(None, username, password))

  def createUser(username: String, password: String) : Either[String, User] = userService.createUser(User(None, username, password)) match {
    case Some(user) => Right(user)
    case None => Left(s"Could not create the user: $username")
  }



}
