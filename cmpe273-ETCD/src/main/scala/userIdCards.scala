package main.scala


import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.web.bind.annotation._
import javax.validation.constraints.{NotNull, Size}
import scala.collection.mutable.HashMap


//@RestController
//@EnableAutoConfiguration
//@ComponentScan
@ComponentScan
class userIdCards {
  //private var userName: String = _
  private var card_name : String =_ ;
  private var card_number : String=_;
  private var CardID : String=_; 
  private var expiration_date :String=_
  
  // getter setter methods 
  
  def getcard_number = this.card_number
  def setcard_number (newCardNumber : String) = this.card_number = newCardNumber ;
  //***************************
  def getcard_name = this.card_name
  def setcard_name (newCardName : String) = this.card_name = newCardName ;
  //*************************
  def getCardID = this.CardID
  def setCardID (newCardID : String) = this.CardID = newCardID ;
  //**********************************
  def getexpiration_date = this.expiration_date
  def setexpiration_date(newExpDT : String) = this.expiration_date = newExpDT ;
  //**********************************
  
  
}