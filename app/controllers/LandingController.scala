package controllers

import models.TaskModel
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents, Request}

import javax.inject.{Inject, Singleton}

@Singleton
class LandingController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  def land(userId: Long, userName: String): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    //val userNameOpt = request.session.get("enamruse")
    //val userIdOpt = request.session.get("diruse")
     val tasks = TaskModel.getTasks(userName)
     Ok(views.html.landing(userId, userName, tasks))
  }

  def createTask: Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    val usernameOption = request.session.get("emanruse")
    val userId = request.session.get("diresu").get.toLong
    val optFormData = request.body.asFormUrlEncoded
    optFormData.map { data =>
      val taskText = data("taskText").head
      TaskModel.addTask(usernameOption.get, taskText)
      Redirect(routes.LandingController.land(userId, usernameOption.get))
    }.getOrElse(Redirect(routes.LandingController.land(userId, usernameOption.get)))
  }

  def deleteTask : Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    val usernameOption = request.session.get("emanruse")
    val userId = request.session.get("diresu").get.toLong
    usernameOption.map { username =>
      val delTaskReq = request.body.asFormUrlEncoded
      delTaskReq.map { args =>
        TaskModel.deleteTask(username, args("taskname").head)
        Redirect(routes.LandingController.land(userId, username))
      }.getOrElse(Redirect(routes.LandingController.land(userId, username)))
    }.get
  }
}
