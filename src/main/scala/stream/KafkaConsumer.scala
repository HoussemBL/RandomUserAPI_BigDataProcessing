package stream

import db._
import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.KafkaUtils

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.streaming.Trigger
import org.apache.spark.sql.streaming.StreamingQuery
import org.apache.spark.sql.functions._
import org.apache.spark.sql.catalyst.expressions.Uuid
import org.apache.spark.sql.cassandra._
import scala.util.Properties


case class KafkaConsumer(topic: String, timewindow: Long) extends Kafka

//companion object
object KafkaConsumer {

  // transform the data consumed from a kafka topic to a dataframe  using the specified schema
  def convertStreamToDF(schema: List[StructType], df_st: DataFrame): DataFrame = {

    val personStringDF = df_st.selectExpr("CAST(value AS STRING)")
    val personDF = personStringDF.select(from_json(col("value"), schema(0)).as("data"))
         .select("data.*")
      .select(col("nat"), col("gender"), from_json(col("name"), schema(1)).as("name"), from_json(col("location"), schema(2)).as("location"))
      .select("nat", "gender", "name.*", "location.city", "location.state", "location.country").withColumn("id", expr("uuid()"))
      .select("id","nat", "gender", "title","first","last", "city", "state", "country")
      return personDF
  }

  //query the data stream available as dataframe
  def print_console_StreamingDF(intervalBatchStr: String, df_out: DataFrame): StreamingQuery = {
    val df = df_out.writeStream
      .format("console")
      .outputMode("append")
      .trigger(Trigger.ProcessingTime(intervalBatchStr))
    // .trigger(Trigger.ProcessingTime("5 seconds"))
      .start()
   
    df
  }

  //store number of visits into mysql
 /* def storeData_mysql(intervalBatch: Long, df_read: StreamingQuery) = {
    while (df_read.isActive) {
      if (df_read.lastProgress != null) {
        val time_visit = df_read.lastProgress.timestamp
        val visit_num = df_read.lastProgress.sink.numOutputRows
        //DAO_visit.insert(time_visit, visit_num)
        println("rows number --> " + df_read.lastProgress.sink.numOutputRows)
        Thread.sleep(intervalBatch * 1000 - 1000)
      }
    }
  }*/

  //store number of visits into mysql
  def save_cassandra( df_read: DataFrame) = {
    val mm:Map[String,String]=Map[String,String]("table"-> DAO_Cassandra.getTable(),"keyspace"-> DAO_Cassandra.getKeySpace())
    // while (df_read.isActive) {
    df_read.write
      .format("org.apache.spark.sql.cassandra")
      .mode("append")
      .options(mm)
      .save
    //}
  }

  
    //store number of visits into mysql
  def save_mysql( df_read: DataFrame, batchId: Long) = {
    
    val df_agg = df_read.groupBy("country").agg(count("id").as("total_sum"))
       .withColumn("processed_at", current_timestamp())
      .withColumn("batch_id", lit(batchId))
      .select("batch_id","country","processed_at","total_sum")
 

    df_agg.write.format("jdbc")
        .option("url",DAO_visit.getURL())
        .option("dbtable", DAO_visit.getTable())
        .option("user", DAO_visit.getUser())
        .option("password", DAO_visit.getPass())
       // .option("driver", DAO_visit.getDriver())
        .mode("append")   
        .save()
 
        
          // df_agg.write.jdbc(DAO_visit.getURL(),DAO_visit.getTable(),DAO_visit.readMYSQLProperties2())
    
  
  }

  
  
  //load function used later for testing
  def loadDF(spark: SparkSession, path:String) : DataFrame={
  
    val df= spark.read.option("header",true).option("inferSchema",true).csv(path)
     df
  }
  
  
  

  
}
