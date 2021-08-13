package stream
import org.apache.spark.sql.types._
import java.util.Properties
import scala.io.Source
import java.util.ArrayList

/****Interface of kafka producers/consumers****/
trait Kafka {
  def timewindow: Long
  def topic: String
}

//companion object
object Kafka {

  //schema used to read/write users in kafka topics
 final val schema0 = new StructType()
        .add("data", StringType, true)

        
   final val schema = new StructType()
        .add("deviceID", StringType, true)
        .add("temperature",IntegerType, true)
        .add("location",StringType, true)
        .add("time", StringType, true)
  
  
    final val schema_location= new StructType()
    .add("latitude", StringType, true)
    .add("longitude", StringType, true)


     final val schemas:List[StructType]= List[StructType](schema0,schema,schema_location)
     
  //specify batch interval as a string
  def convertTimeToString(wait_time: Long): String = {
    var processingTime = wait_time + " seconds"
    processingTime
  }

  //access the schema used for reading apache logs
  def getschema(): List[StructType] = {
    Kafka.schemas
  }

  //read properties of kafka specified in src.main.resources
  def readKafkaProperties(): Properties =
    {
      val url = getClass.getResource("/kafka.properties")
      val source = Source.fromURL(url)
      val Kafkaparameters = new Properties
      Kafkaparameters.load(source.bufferedReader())

      Kafkaparameters

    }
}

