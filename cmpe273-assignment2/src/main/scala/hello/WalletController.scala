package hello

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.ComponentScan
import org.springframework.stereotype._
import org.springframework.boot.autoconfigure._
import org.springframework.web.bind.annotation._
import java.util.concurrent.atomic.AtomicLong
import org.springframework.http.{HttpHeaders, ResponseEntity}
import org.springframework.web.bind.annotation.{ResponseBody, RequestMapping, RequestParam, RestController}
import javax.ws.rs.core._
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter
import org.springframework.http.HttpStatus
import collection.JavaConversions._
import java.util.ArrayList
import collection.JavaConversions._
import javax.validation.Valid
import org.springframework.validation.BindingResult
import org.joda.time.DateTime
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.BasicQuery
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{ Failure, Success }
import java.util.{ArrayList, Date}
import javax.servlet.http.{HttpServletRequest, HttpServletResponse}
import org.springframework.boot._
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure._
import org.springframework.http.HttpStatus
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation._
import javax.validation.Valid
import org.springframework.web.client.RestTemplate
import scala.collection.JavaConverters._
import scala.collection.JavaConversions._
import org.springframework.boot.json.JacksonJsonParser
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter

@RestController
@Configuration
@EnableAutoConfiguration
@ComponentScan
class WalletController {
val ctx = new AnnotationConfigApplicationContext(classOf[SpringMongoConfig])
val mongoOperation = ctx.getBean("mongoTemplate").asInstanceOf[MongoOperations]
val users = new ArrayList[User]()
  var counter1 = 0//new AtomicLong()
   var  user=new User();
	var user_map: Map[String, User] = Map();
	//******************************************************************************************************************
	//*                               Creation of user 
	//******************************************************************************************************************
  @RequestMapping(value = Array("/api/v1/users"), method = Array(RequestMethod.POST), headers = Array("content-type=application/json"), consumes = Array("application/json"))
        def CreationOfUserModule(@Valid @RequestBody user : User, result: BindingResult):User = {
                
    val Random = new scala.util.Random
    val Ran1 =  Random.nextInt();
    this.user = user
    var new_id = "U" + Ran1
    user.setUserId(new_id)
    val currentTime = DateTime.now;
    this.user.setcreated_at(currentTime.toString)
    users.add(this.user)
    mongoOperation.insert(user)
    println("ratan user creation")
    return user
    
   
}
  //*************************************************************************************************************
  //*  View Specific User info
  //*************************************************************************************************************
 @RequestMapping(value=Array("api/v1/users/{userid}"), method=Array(RequestMethod.GET))
	def DisplayUsers(@PathVariable("userid")  userId:String,@RequestHeader(value="If-None-Match", required=false) Etag: String):ResponseEntity[_]={
		
      val getuser = new Query();
		getuser.addCriteria(Criteria.where("_id").is(userId)); 
		val userTest2 = mongoOperation.findOne(getuser, classOf[User])
                var tag: String = Etag
		var cc: CacheControl = new CacheControl()
        	cc.setMaxAge(500)
        var etag: EntityTag = new EntityTag(Integer.toString(userTest2.hashCode()));
               println(etag);
        	var responseHeader: HttpHeaders = new HttpHeaders	
        	responseHeader.setCacheControl(cc.toString())
        	responseHeader.add("Etag", etag.getValue())
        	if(etag.getValue().equalsIgnoreCase(tag)){
        		 
                    new ResponseEntity[String]( null, responseHeader, HttpStatus.NOT_MODIFIED )   
        	} else {
                       
        		new ResponseEntity[User]( userTest2, responseHeader, HttpStatus.OK )  
        	}
		
	}  
 //*********************************************************************************************************************
 //*                                                        Update User 
 //*********************************************************************************************************************
    @RequestMapping(value = Array("/api/v1/users/{userid}"), method = Array(RequestMethod.PUT), headers = Array("content-type=application/json"), consumes = Array("application/json"))
		def upduser(@PathVariable("userid")  userId:String ,@RequestBody user : User ):User={
      var u = new User()
		
        val nowTime = DateTime.now; // for updating timestamp
        user.setUserId(userId) // to locate the perticular user by id = userId
		user.setcreated_at(nowTime.toString) // setting the timeStamp
		mongoOperation.save(user) // updating the users
		
		//********* Fetching the user from  database************//
		val query8 = new Query();
		query8.addCriteria(Criteria.where("_id").is(userId)); 
		 u = mongoOperation.findOne(query8, classOf[User])
		println(u.getemail)
        return u
		
    }
    //*****************************************************************************************************************
    //***                                         IdCards 
    //*****************************************************************************************************************
    
   @RequestMapping(value = Array("/api/v1/users/{userid}/idcards"), method = Array(RequestMethod.POST), headers = Array("content-type=application/json"), consumes = Array("application/json"))
       def idcards( @PathVariable("userid")  userId:String ,@Valid @RequestBody usercard : Card,result: BindingResult): Card = {
    		    if (result.hasErrors()) {
      throw new ParameterMissingException(result.toString)
    } 
    else 
    {  
      
      val query8 = new Query();
		query8.addCriteria(Criteria.where("_id").is(userId)); 
		val userTest2 = mongoOperation.findOne(query8, classOf[User])
    		   userTest2.makecardmap(usercard)
    		   mongoOperation.save(userTest2)
		return usercard
    }
 }
   
   //******************************************************************************************************************
   //* Display Particular Id card
   //*****************************************************************************************************************
   @RequestMapping(value=Array("/api/v1/users/{userid}/idcards"), method=Array(RequestMethod.GET), produces = Array("application/json"))
	def viewcards(@PathVariable("userid")  userId:String ):Array[Card]={
     val query8 = new Query();
		query8.addCriteria(Criteria.where("_id").is(userId)); 
		val userTest2 = mongoOperation.findOne(query8, classOf[User])
		println("userId")
		println(userId)
		 val idCards = userTest2.cardmap.values.toArray
        idCards
   }
   
   //****************************************************************************************************************
   //* Deleting Particular ID cards 
   //****************************************************************************************************************
   
  @RequestMapping(value=Array("/api/v1/users/{userid}/idcards/{card_id}"), method=Array(RequestMethod.DELETE), headers=Array("content-type=application/json"))@ResponseStatus(HttpStatus.NO_CONTENT)
	def deletecards(@PathVariable("userid")  userId:String, @PathVariable("card_id")  cardId:String ):Unit={
     val query8 = new Query();
		query8.addCriteria(Criteria.where("_id").is(userId)); 
		val userTest2 = mongoOperation.findOne(query8, classOf[User])
   userTest2.cardmap -= cardId
   mongoOperation.save(userTest2)
  
   }
  //********************************************************************************************************************
  //*                                            Web logins 
  //********************************************************************************************************************
   
   @RequestMapping(value = Array("/api/v1/users/{userid}/weblogins"), method = Array(RequestMethod.POST), headers = Array("content-type=application/json"), consumes = Array("application/json"))
       def webloginscards( @PathVariable("userid")  userId:String ,@Valid @RequestBody userlogin : Web,result: BindingResult): Web = {
    if (result.hasErrors()) {
      throw new ParameterMissingException(result.toString)
    } 
    else 
    {
      val query8 = new Query();
		query8.addCriteria(Criteria.where("_id").is(userId)); 
		val u = mongoOperation.findOne(query8, classOf[User])
    		   u.makewebmap(userlogin)
    		   mongoOperation.save(u)
    		   return u.web
    }  
     }
   
   //**********************************************************************************************************************
   //*                                  View Particular web login of user 
   //*********************************************************************************************************************
   @RequestMapping(value=Array("/api/v1/users/{userid}/weblogins"), method=Array(RequestMethod.GET), produces = Array("application/json"))
	def viewweb(@PathVariable("userid")  userId:String ): Array[Web]={
   
        val query8 = new Query();
		query8.addCriteria(Criteria.where("_id").is(userId)); 
		val userTest3 = mongoOperation.findOne(query8, classOf[User])
        val idCards = userTest3.webmap.values.toArray
        return idCards
   }
   
   //*******************************************************************************************************************
   //*                                       Deleting user ID
   //*******************************************************************************************************************
   @RequestMapping(value=Array("/api/v1/users/{userid}/weblogins/{login_id}"), method=Array(RequestMethod.DELETE), headers=Array("content-type=application/json"))@ResponseStatus(HttpStatus.NO_CONTENT)
	def deleteweb(@PathVariable("userid")  userId:String, @PathVariable("login_id")  loginId:String ):Unit={
     val query8 = new Query();
		query8.addCriteria(Criteria.where("_id").is(userId)); 
		val u = mongoOperation.findOne(query8, classOf[User])
     u.webmap -= loginId
     mongoOperation.save(u)
   }
   
   //*******************************************************************************************************************
   //*                        Creation of Bank account
   //*******************************************************************************************************************
    @RequestMapping(value = Array("/api/v1/users/{userid}/bankaccounts"), method = Array(RequestMethod.POST), headers = Array("content-type=application/json"), consumes = Array("application/json"))
       def userbank( @PathVariable("userid")  userId:String ,@Valid @RequestBody bank : Bank,result: BindingResult): Bank = {
    if (result.hasErrors()) {
      println("here1")
      throw new ParameterMissingException(result.toString)
    } 
    else 
    {
     println("here")
     var info  = new RoutingInformationClient().getRoutingInformation(bank.getrouting_number)
     println("customer name ==>"+info.getCustomer_name)
     if (info.getCustomer_name == "")
     {
       println(" Routing number not found")
       throw new IllegalArgumentException("*** Routing Number not Found ******")
     } 
     else
     {
    bank.setaccount_name(info.getCustomer_name)
     }
     
     //******************
      val query8 = new Query();
		query8.addCriteria(Criteria.where("_id").is(userId)); 
		val u = mongoOperation.findOne(query8, classOf[User])
    		   u.makebankmap(bank)
    		   mongoOperation.save(u)
    		   return u.bank
    }  
     }
    //*********************************************************************************************
   //*     Display  the Bank Id
   //***********************************************************************************************
   @RequestMapping(value=Array("/api/v1/users/{userid}/bankaccounts"), method=Array(RequestMethod.GET), produces = Array("application/json"))
	def viewbank(@PathVariable("userid")  userId:String ): Array[Bank]={
    val query8 = new Query();
		query8.addCriteria(Criteria.where("_id").is(userId))
		val userTest4 = mongoOperation.findOne(query8, classOf[User])
		 val idcards = userTest4.bankmap.values.toArray
         return idcards
   }
   //*********************************************************************************************
   //*     Deleting the Bank Id
   //************************************************************************************************
   @RequestMapping(value=Array("/api/v1/users/{userid}/bankaccounts/{ba_id}"), method=Array(RequestMethod.DELETE), headers=Array("content-type=application/json"))@ResponseStatus(HttpStatus.NO_CONTENT)
	def deletebank(@PathVariable("userid")  userId:String, @PathVariable("ba_id")  baId:String ):Unit={
     val query8 = new Query();
		query8.addCriteria(Criteria.where("_id").is(userId)); 
		val u = mongoOperation.findOne(query8, classOf[User])
     u.bankmap -= baId
   mongoOperation.save(u)
   }
}