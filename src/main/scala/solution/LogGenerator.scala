package solution

import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.streaming.Trigger
import stream._
import iot._

import java.util.Properties
import scala.io.Source
import Utils.Utils
import iot.Device
import java.util.UUID
import scala.util.Random
import java.time.Instant
import java.util.ArrayList
import com.google.gson.Gson


class LogGenerator
object LogGenerator {

    val uuid1 = UUID.randomUUID()
    val uuid2 = UUID.randomUUID()
    val uuid3 = UUID.randomUUID()
    val device1 = Device(uuid1, 52.14691120000001, 11.658838699999933)
    val device2 = Device(uuid2, 	58.545284, 23.614328)
    val device3 = Device(uuid3, 36.806389, 10.181667)


    val list_devices: List[Device]= List(device1,device2,device3)

            val gson = new Gson
    
   def printLog():String={
      val temp=randomTemp()
      val unixTimestamp  = Instant.now.getEpochSecond.toString()
      val random = new Random
      val elt= list_devices(random.nextInt(list_devices.length))
      val add= Address(elt.latitude.toString(),elt.longitude.toString())
      val devInfo=DeviceInfo(elt.deviceID,temp, add,unixTimestamp)
      val device_log=DeviceLog(devInfo)

      val jsonString = gson.toJson(device_log)
     
     jsonString
    }
  

  def randomTemp(): Int= {

    val min= -10
    val max = 45
//    val random = new Random()
//    val res = random.nextInt() * (maximum - minimum) + minimum
    val random = new Random()
    val randomNumber = random.nextInt(max + 1 - min) + min
    randomNumber
  }

}
