package stream
import org.apache.spark.sql.types._
import java.util.Properties
import scala.io.Source

/****Interface of kafka producers/consumers****/
trait Kafka {
  def timewindow: Long
  def topic: String
}

//companion object
object Kafka {

  //schema used to read/write users in kafka topics

      
   final val schema = new StructType()
        .add("gender", StringType, true)
        .add("name",StringType, true)
        .add("location",StringType, true)
        .add("nat", StringType, true)
  
   val schema_name = new StructType()
    .add("title", StringType, true)
    .add("first", StringType, true)
    .add("last", StringType, true)
    
     val schema_location= new StructType()
    .add("street", StringType, true)
    .add("city", StringType, true)
    .add("state", StringType, true)
    .add("country", DoubleType, true)
     .add("coordinates", DoubleType, true)
     .add("timezone", DoubleType, true)

  //specify batch interval as a string
  def convertTimeToString(wait_time: Long): String = {
    var processingTime = wait_time + " seconds"
    processingTime
  }

  //access the schema used for reading apache logs
  def getschema(): StructType = {
    Kafka.schema
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

