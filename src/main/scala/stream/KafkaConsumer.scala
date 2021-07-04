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

case class KafkaConsumer(topic: String, timewindow: Long) extends Kafka

//companion object
object KafkaConsumer {

  // transform the data consumed from a kafka topic to a dataframe  using the specified schema
  def convertStreamToDF(schema: List[StructType], df_st: DataFrame): DataFrame = {

    val personStringDF = df_st.selectExpr("CAST(value AS STRING)")
    val personDF = personStringDF.select(from_json(col("value"), schema(0)).as("data"))
      .select("data.*")
      .select(col("nat"), col("gender"), from_json(col("name"), schema(1)).as("name"), from_json(col("location"), schema(2)).as("location"))
      //.select("data.*")
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
      .start()
    df
  }

  //store number of visits into mysql
  def storeData_mysql(intervalBatch: Long, df_read: StreamingQuery) = {
    while (df_read.isActive) {
      if (df_read.lastProgress != null) {
        val time_visit = df_read.lastProgress.timestamp
        val visit_num = df_read.lastProgress.sink.numOutputRows
        //DAO_visit.insert(time_visit, visit_num)
        println("rows number --> " + df_read.lastProgress.sink.numOutputRows)
        Thread.sleep(intervalBatch * 1000 - 1000)
      }
    }

  }

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

}