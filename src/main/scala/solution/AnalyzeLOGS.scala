package solution

import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.streaming.Trigger
import stream._
import db._

import java.util.Properties
import scala.io.Source
import Utils.Utils

object AnalyzeLOGS {
  def main(args: Array[String]): Unit = {

    val spark = Utils.getSpark()
    import spark.implicits._
    //val kafkaprameters = Utils.getKafkaParameters()

    //consuming Kafka topic
    val df = spark.readStream
      .format("socket")
      .option("host", "localhost")
      .option("port", 9999)
      .load()

      
    val df_out = KafkaConsumer.convertStreamToDF(Kafka.getschema(), df)

    val df_read = KafkaConsumer.print_console_StreamingDF("3 seconds", df_out)

    

    df_read.awaitTermination()

  }

}
