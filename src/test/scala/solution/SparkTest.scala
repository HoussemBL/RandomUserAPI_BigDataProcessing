package solution

import org.apache.spark.sql.SparkSession
import org.scalatest.{BeforeAndAfterAll, FunSuite}
import stream.KafkaConsumer.{loadDF}


class SparkTest extends FunSuite with BeforeAndAfterAll{

@transient var spark: SparkSession = _
  
  
override def beforeAll():Unit= 
  {
  spark=SparkSession.builder()
    .appName(name = "Spark for testing")
    .master( master="local[3]")
    .getOrCreate()
  }
  
  
override def afterAll():Unit= 
  {
  spark.stop()
  }


test(testName = "Data File loading")
  {
  val sampleDF=loadDF(spark, "data/sample.csv")
  val rCount=sampleDF.count()
  assert(rCount==9, clue="record count should be 9"  )
  }


}

