package network
import java.net._
import java.io._
import scala.io._
import solution.LogGenerator

object SocketServer extends App {

  val server = new ServerSocket(9999)
  println("initialized server")
  val client = server.accept

  val in = new BufferedReader(new InputStreamReader(client.getInputStream))
  val out = new PrintStream(client.getOutputStream)

  while (true) {
   // val client_message = in.readLine()
    val input = LogGenerator.printLog()
 //   println("Server received:" + client_message) // print out the input message
    out.println(input+"\r")
    out.flush()
    Thread.sleep(2000)
  }
  server.close()
}
//package network
//import java.net._
//import java.io._
//import scala.io._
//
//object SocketServer extends App {
//
//  val server = new ServerSocket(9999)
//  println("initialized server")
//  val client = server.accept
//
//   while (true) {
//
//  val in = new BufferedReader(new InputStreamReader(client.getInputStream))
//  val out = new PrintStream(client.getOutputStream)
//
//  val client_message = in.readLine()
//  
//
//  println("Server received:" + client_message) // print out the input message
//  //out.flush
//   Thread.sleep(2000)
//   }
//  server.close()
//}