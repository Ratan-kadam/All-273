package main.scala

import javax.validation.constraints.{NotNull, Size}

import org.springframework.context.annotation.ComponentScan
import org.springframework.web.bind.annotation._

@RestController
//@EnableAutoConfiguration
@ComponentScan
class userBankAcc {
  private var ba_id: String =_
  private var account_name: String =_
  @NotNull @Size(min = 1) private var routing_number: String =_
  @NotNull @Size(min = 1) private var account_number: String =_

  def getBaId = this.ba_id

  def setBa_id(newBaId: String) = this.ba_id = newBaId

  def setaccount_name(newAccountName: String) = this.account_name = newAccountName

  def getaccount_name = this.account_name

  def setrouting_number(newRoutineNumber: String) = this.routing_number = newRoutineNumber

  def getrouting_number = this.routing_number

  def setcount_number(newAccountNumber: String) = this.account_number = newAccountNumber

  def getaccount_number = this.account_number
}