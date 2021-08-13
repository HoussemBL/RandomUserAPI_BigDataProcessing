package iot

import java.util.UUID

case class DeviceLog (
     data : DeviceInfo
)

case class DeviceInfo(deviceID : UUID,
    temperature: Int, location:Address,time:String)
case class Address(latitude: String,longitude: String)