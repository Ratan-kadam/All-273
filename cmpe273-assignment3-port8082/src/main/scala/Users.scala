package main.scala

import java.util.Date

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.web.bind.annotation._
import javax.validation.constraints.{NotNull, Size}
import scala.collection.mutable.HashMap


@RestController
@EnableAutoConfiguration
@ComponentScan
class Users {
	private var user_id: String =_
			@NotNull @Size(min = 1) var email: String = _ // act as a validations for empty string 
			@NotNull @Size(min = 1) var password: String = _
			private var name: String = _
			private var creationDT: Date = new Date()
			private var updateOn: Date = new Date()
			private var userIDcardtable : HashMap[String,userIdCards] = _  // _ --> Initialization 
		    private var userWebLoginTable : HashMap[String,userWebLogin] = _
		    private var userBankAccountTable : HashMap[String,userBankAcc] = _
            
			// getter setter methods for request class.
			def getUser_id = this.user_id
			def getemail = this.email
			def getname = this.name
			def getpassword = this.password
			def getCreationDT = this.creationDT
			def getUpdateOn  =  this.updateOn
			def getUserIDcardTable = this.userIDcardtable
			def getUserWebLoginTable = this.userWebLoginTable
            def getuserBankAccountTable = this.userBankAccountTable
            //
			def setuser_id(newUserId : String ) = this.user_id = newUserId
			def setemail(newUserEmail : String ) = this.email = newUserEmail
			def setpassword(newUserPassword : String) = this.password = newUserPassword
			def setname(newUserName :String ) = this.name = newUserName
			def setUserIdCardTable = this.userIDcardtable = new HashMap	
			def setUserWebLoginTable = this.userWebLoginTable = new HashMap
			def setuserBankAccountTable = this.userBankAccountTable = new HashMap
			

}   

