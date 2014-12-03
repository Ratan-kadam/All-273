package main.scala

import java.text.SimpleDateFormat
import javax.validation.Valid
import org.springframework.http.{HttpHeaders, HttpStatus, ResponseEntity}
import org.springframework.web.bind.annotation._
//*****************************************************************************
//*  Library used for ETCD - java 
//*****************************************************************************
import jetcd._
import java.net._

@RestController
@RequestMapping(value=Array("/api/v1"))
class Test {
	 var client: EtcdClient = EtcdClientFactory.newInstance("http://127.0.0.1:4001/")
  //****************************************************************************************
  //*                Module for creation of Users                                                   *
  //****************************************************************************************
	@RequestMapping(value = Array("/users"), method = Array(RequestMethod.POST), consumes = Array("application/json"), produces = Array("application/json"))
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody def createUser(@RequestBody @Valid newUserDetails : Users): java.util.LinkedHashMap[String,String] = {
 //println (" inside test loop ") ;
      val rndnum = new scala.util.Random
      var uid0= rndnum.nextInt();
      var uid_final = ("u" + uid0);
      println("uid_final" , uid_final);
      Runapp.userCollection.put(uid_final,newUserDetails);
 // displaying the object from the hasmap 
      newUserDetails.setuser_id(uid_final);
      newUserDetails.setUserIdCardTable
      newUserDetails.setuserBankAccountTable
      newUserDetails.setUserWebLoginTable
      // displaying object as per the requirement- new hash map created just for display purpose
       val finalMap = new java.util.LinkedHashMap[String,String]
        finalMap.put("userId",newUserDetails.getUser_id)
        finalMap.put("email",newUserDetails.email)
        finalMap.put("password",newUserDetails.password)
        finalMap.put("name",newUserDetails.getname)
       finalMap.put("createdAt",new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(newUserDetails.getCreationDT))
       finalMap.put("updatedAt",new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(newUserDetails.getUpdateOn))
        
    //  newUserDetails.put("createdAt",new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(userDetails.getCreatedAt))
      
      //return Runapp.userCollection.get(uid_final).get 
   return finalMap
	} // Complition of responseBody 

	//****************************************************************************************
  //*                Module for getting the users information                                                   *
  //****************************************************************************************
 @RequestMapping(value= Array("/users/{user_id}"),method = Array(RequestMethod.GET))
 @ResponseStatus(HttpStatus.OK)
 @ResponseBody def getHashMap1(@PathVariable user_id:String): Users = {
  
  return Runapp.userCollection.get(user_id).get} 
//********************************************************************************************
//*            Module for updating the user information - PUT 
//*********************************************************************************************
 // @RequestMapping(value= Array("/users/{user_id}"),method = Array(RequestMethod.PUT))
  //@ResponseStatus(HttpStatus.CREATED)
  //@ResponseBody def getHashMap1(@RequestBody newUserInfo: Users,@PathVariable user_id:String): Users = {
  
    //var userInfo: Option[Users] = Runapp.userCollection.get(user_id)
   // var userInfo  = Runapp.userCollection.get(user_id).get

  //userInfo.get.setemail(newUserInfo.email)
  //userInfo.get.setpassword(newUserInfo.password)
  //userInfo.get.setuser_id(newUserInfo.getUser_id) // private so get method 
  //userInfo.get.setname(newUserInfo.getname)// private so get method
  
  //var chkUpdate: Option[Users] = Runapp.userCollection.put(userInfo.get.getId,userInfo.get)

  // return Runapp.userCollection.get(user_id).get
 @RequestMapping(value= Array("/users/{user_id}"),method = Array(RequestMethod.PUT))
 @ResponseStatus(HttpStatus.CREATED)
 @ResponseBody def updateUserById(@RequestBody newUserInfo: java.util.HashMap[String,String], @PathVariable user_id: String): 
 java.util.LinkedHashMap[String,String] = {
        val userDetails = Runapp.userCollection.apply(user_id)
        val newItr = newUserInfo.keySet.iterator
        //var isUpdated = false
        while(newItr.hasNext){
            newItr.next() match {
                case "email" =>
                    //isUpdated = true
                    println("email"+newUserInfo.get("email"))
                    userDetails.setemail(newUserInfo.get("email"))
                case "password" =>
                  //isUpdated = true
                  println("password"+newUserInfo.get("password"))
                  userDetails.setpassword(newUserInfo.get("password"))
                case "name" =>
                  //isUpdated = true
                  println("name"+newUserInfo.get("name"))
                  userDetails.setname(newUserInfo.get("name"))
                  
                  
            }
        }
        val finalMap = new java.util.LinkedHashMap[String,String]
        finalMap.put("userId",userDetails.getUser_id)
        finalMap.put("email",userDetails.email)
        finalMap.put("password",userDetails.password)
        finalMap.put("name",userDetails.getname)
        finalMap.put("createdAt",new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(userDetails.getCreationDT))
        finalMap.put("updatedAt",new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(userDetails.getUpdateOn))
        
    //  newUserDetails.put("createdAt",new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(userDetails.getCreatedAt))
      
      //return Runapp.userCollection.get(uid_final).get 
       return finalMap
 }
//**********************************************************************************************
//*  Module for creation of ID cards for perticular users 
//*************************************************************************************************
 @RequestMapping(value= Array("/users/{user_id}/idcards"),method = Array(RequestMethod.POST))
 @ResponseStatus(HttpStatus.CREATED)
 @ResponseBody def getHashMap1(@RequestBody newIdInfo: userIdCards ,@PathVariable user_id:String) : userIdCards = {
  
   
      val rndnum1 = new scala.util.Random
      var cid= rndnum1.nextInt();
     
    
    Runapp.increment = Runapp.increment + 1
   
   //var ucardid = ("C" + user_id + Runapp.increment)
    var ucardid = ("c" + cid + Runapp.increment )
   println("ucardid:" + ucardid)
   val updateVar = Runapp.userCollection.apply(user_id);
   
   updateVar.getUserIDcardTable.update(ucardid,newIdInfo); // put will work
   newIdInfo.setCardID(ucardid)
  
  val update2 = updateVar.getUserIDcardTable.apply(ucardid);
  return update2 
 
  }
//************************************************************************************************
//*  Module for listing all cards of user 
//************************************************************************************************* 
 @RequestMapping(value= Array("/users/{user_id}/idcards"),method = Array(RequestMethod.GET),produces= Array("application/json"))
 @ResponseStatus(HttpStatus.OK)
 @ResponseBody def getHashMap2(@PathVariable user_id: String) : Array[userIdCards] = {
   
    val userDetails = Runapp.userCollection.apply(user_id)
    
        val idCards = userDetails.getUserIDcardTable.values.toArray
        idCards
 }
 
 //***************************************************************************************************
 //** Deleting particular Id card of USER . 
 //******************************************************************************************************'
 @RequestMapping(value= Array("/users/{user_id}/idcards/{idcard}"),method = Array(RequestMethod.DELETE))
 @ResponseStatus(HttpStatus.NO_CONTENT)
 @ResponseBody def getHashMap3(@PathVariable user_id:String ,@PathVariable idcard: String ) = {
   
   val getObj = Runapp.userCollection.apply(user_id)
   getObj.getUserIDcardTable -= idcard
 
 }
 
 ///////////////////////////////////web login ////////////////////////////////
 @RequestMapping(value= Array("/users/{user_id}/weblogins"),method = Array(RequestMethod.POST))
 @ResponseStatus(HttpStatus.CREATED)
 @ResponseBody def getHashMap4(@RequestBody newwebInfo: userWebLogin ,@PathVariable user_id:String) : userWebLogin = {
      val rndnum2 = new scala.util.Random
      var wid= rndnum2.nextInt();
   Runapp.increment = Runapp.increment + 1 
   var ucardid = ("l" + wid + Runapp.increment )
   val updateVar = Runapp.userCollection.apply(user_id);
   //updateVar.setUserWebLoginTable
   updateVar.getUserWebLoginTable.update(ucardid,newwebInfo)
   newwebInfo.setlogin_id(ucardid);
   val update2 = updateVar.getUserWebLoginTable.apply(ucardid);
   update2 
 
  }
//************************************************************************************************
//*                Module for listing all the web logins
//************************************************************************************************* 
 @RequestMapping(value= Array("/users/{user_id}/weblogins"),method = Array(RequestMethod.GET))
 @ResponseStatus(HttpStatus.OK)
 @ResponseBody def getHashMap5(@PathVariable user_id:String) : Array[userWebLogin] = {
   
    val userDetails = Runapp.userCollection.apply(user_id)
    
        val weblogin = userDetails.getUserWebLoginTable.values.toArray
        
        return weblogin
        
 }
 
 //***************************************************************************************************
 //** Deleting particular Id card of USER . 
 //******************************************************************************************************'
 @RequestMapping(value= Array("/users/{user_id}/weblogins/{web_login}"),method = Array(RequestMethod.DELETE))
 @ResponseStatus(HttpStatus.NO_CONTENT)
 @ResponseBody def getHashMap6(@PathVariable user_id:String ,@PathVariable web_login: String ) = {
   
   val getObj = Runapp.userCollection.apply(user_id)
   getObj.getUserWebLoginTable -= web_login
 
 }
 
 
/////////////////////////////// bank account ////////////////////////////////////////////////////////////
  @RequestMapping(value= Array("/users/{user_id}/bankaccounts"),method = Array(RequestMethod.POST))
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody def getHashMap5(@RequestBody newbankInfo: userBankAcc ,@PathVariable user_id:String) : userBankAcc = {
    val rndnum2 = new scala.util.Random  ;
    var bid= rndnum2.nextInt();
    Runapp.increment = Runapp.increment + 1 ;
   
     var ucardid = ("b" + bid + Runapp.increment)
     val updateVar = Runapp.userCollection.apply(user_id);
    // updateVar.setuserBankAccountTable
     newbankInfo.setBa_id(ucardid)
     updateVar.getuserBankAccountTable.update(ucardid,newbankInfo)
     var update2 = updateVar.getuserBankAccountTable.apply(ucardid);
     update2 
  }
  ////// displaying all account
  
   @RequestMapping(value = Array("/users/{userId}/bankaccounts"), method = Array(RequestMethod.GET), produces = Array("application/json"))
   @ResponseStatus(HttpStatus.OK)
   @ResponseBody def getBankAccountsById(@PathVariable userId: String): Array[userBankAcc] = {
        val userDetails = Runapp.userCollection.apply(userId)
        
        val bankAccounts = userDetails.getuserBankAccountTable.values.toArray
        bankAccounts
   }

  //Delete 
   @RequestMapping(value = Array("users/{userId}/bankaccounts/{bankId}"), method = Array(RequestMethod.DELETE), produces = Array("application/json"))
   @ResponseStatus(HttpStatus.NO_CONTENT)
   @ResponseBody def deleteBankAccountById(@PathVariable userId: String, @PathVariable bankId: String): Unit = {
        val userDetails = Runapp.userCollection.apply(userId)
        userDetails.getuserBankAccountTable -= bankId
   }
   //**************************************************************************
   //*                        Assignment 3 - ETCD 
   //**************************************************************************
   @RequestMapping(value=Array("users/counter"), method=Array(RequestMethod.GET))
	@ResponseBody
   def counter( ): String={
  
    var response = ""
	var indicator = false
    var countValue =""
	var applicationkey = "0"
	
		
   try{
		println(" putting  " + applicationkey)
		response = this.client.get(applicationkey)
		println("getting  " + response)
	}
	catch{
		
		case e : Exception => println(" Found Exception==>" + e)

		var keyval = this.client.set(applicationkey, "0")
		indicator = true
		
	}
		
		if(!indicator)
		{
			var res = this.client.get(applicationkey)
			var intCount = res.toInt
			var finalCount = intCount + 1
			var update = this.client.set(applicationkey, finalCount.toString)
			countValue =  this.client.get(applicationkey)
 		}
		

	return countValue

   
   }
   
}
//*************************************************************************
//* Completion of assignment 3
//***************************************************************************


