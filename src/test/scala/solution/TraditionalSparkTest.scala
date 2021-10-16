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


  
  test(testName = "Structure of a dataframe")
  {
  val sampleDF=loadDF(spark, "data/sample.csv")
  val colsList=sampleDF.schema.columns.toList
    
    
  assert(colsList==9, clue="number of columns in the dataframe should be 9"  )
  assert(colsList.contains("id), clue="the inferred schema should contains a column called id"  )
  }
  
}

