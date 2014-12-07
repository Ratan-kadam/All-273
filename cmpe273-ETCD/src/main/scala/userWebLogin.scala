package main.scala

import javax.validation.constraints.{NotNull, Size}

import org.springframework.context.annotation.ComponentScan
import org.springframework.web.bind.annotation._

@RestController
//@EnableAutoConfiguration
@ComponentScan
class userWebLogin {
  private var login_id: String =_
  @NotNull @Size(min = 1) private var url: String =_
  @NotNull @Size(min = 1) private var login: String =_
  @NotNull @Size(min = 1) private var password: String =_

  def getlogin_id = this.login_id

  def setlogin_id(newId: String) = this.login_id = newId

  def seturl(newLoginUrl: String) = this.url = newLoginUrl

  def geturl = this.url

  def setwebLogin(newLogin: String) = this.login = newLogin

  def getlogin = this.login

  def setpassword(newPassword: String) = this.password = newPassword

  def getpassword = this.password
}