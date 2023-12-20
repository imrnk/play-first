package controllers

import models.{TaskModel, UserModel}
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents, Request}

import javax.inject.{Inject, Singleton}

@Singleton
class LoginController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  def login : Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.login())
  }

  def validateLogin : Action[AnyContent] = Action { implicit request: Request[AnyContent] =>

    val optFormData = request.body.asFormUrlEncoded
    optFormData.map { data =>
      val username = data("username").head
      val password = data("password").head
      UserModel.validateUser(username, password) match
      {
        case Some(user) => Redirect(routes.LandingController.land(user.userId.get, user.username))
          .withSession("emanruse" -> user.username, "diresu" -> user.userId.get.toString)
        case None => Redirect(routes.LoginController.login).flashing("error" -> "Invalid username/password combination")
      }
    }.getOrElse(Redirect(routes.LoginController.login))
  }

  def createUser: Action[AnyContent] = Action { implicit request: Request[AnyContent] =>

    val optFormData = request.body.asFormUrlEncoded
    optFormData.map { data =>
      val username = data("username").head
      val password = data("password").head
      UserModel.createUser(username, password) match {
        case Right(user) => Redirect(routes.LandingController.land(user.userId.get, user.username))
          .flashing("success" -> s"User ${user.username} created successfully")
        case Left(message) => Redirect(routes.LoginController.login).flashing("error" -> message)
      }
    }.getOrElse(Redirect(routes.LoginController.login))
  }

  def logout: Action[AnyContent] = Action {implicit request: Request[AnyContent] =>
    Redirect(routes.LoginController.login).withNewSession
  }

}
