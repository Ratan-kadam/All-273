package hello

/*import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoTemplate
import com.mongodb.MongoClientURI
import com.mongodb.MongoClient
import scala.collection.JavaConversions._
import com.mongodb.casbah._
import org.springframework.stereotype.Repository
import java.util.Arrays
import com.mongodb.ServerAddress
import org.springframework.data.mongodb.repository.MongoRepository*/




/*@Repository
class  SpringMongoConfig 
{
   val sample = "Usertest"
     
     def getmongoRepository(): MongoRepository =
     {
      try
      {
        var uri = "mongodb://Jump:jumpingds047930.mongolab.com:47930/spring1"
        var url = new MongoClientURI(uri)
        val details = MongoCredential.createMongoCRCredential(url.getUsername, url.getDatabase, url.getPassword)
        val mongoClient = new MongoClient(new ServerAddress("ds047930.mongolab.com",47930), Arrays.asList(details))
        val mongoRepository = new MongoRepository(mongoClient)
        mongoRepository
      }
      catch{
        case e:Exception => null
      }
     }
   
 
     
  
   
}
*/

//package main.scala.hello

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.authentication._
import com.mongodb.MongoClient
//remove if not needed
import scala.collection.JavaConversions._

@Configuration
class SpringMongoConfig {

  @Bean
  def mongoTemplate(): MongoTemplate = {
    val mongoTemplate = new MongoTemplate(new MongoClient("ds049130.mongolab.com:49130"), "yourdb",new UserCredentials("ratan1","xxxx"))
   ////  val mongoTemplate = new MongoTemplate(new MongoClient("ds047940.mongolab.com:47940"), "cmpe273",new UserCredentials("yashoswal","oswal111"))
    mongoTemplate
  }
}

















