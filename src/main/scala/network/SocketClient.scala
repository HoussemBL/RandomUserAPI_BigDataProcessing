//package network
//import java.net._
//import java.io._
//import scala.io._
//import solution._
//
//object SocketClient extends App {
//
//  println("accepting")
//
//  var i = 0
//  while ( /*i < 10*/ true) {
//    val socket = new Socket("localhost", 9999)
//    val out = new PrintStream(socket.getOutputStream)
//    //val out = new PrintWriter(socket.getOutputStream,true)
//    println("type something")
//
//    val input = LogGenerator.printLog()
//    out.println("sss\r\n")
//    out.flush()
//
//    i = i + 1
//    out.close()
//    socket.close()
//    Thread.sleep(5000)
//  }
//
//}

package network
import java.net._
import java.io._
import scala.io._
import solution._

object SocketClient extends App {

  val socket = new Socket("localhost", 9999)
  println("accepting")
  //val client = server.accept
  var i = 0

  println("type something")
  val out = new PrintStream(socket.getOutputStream)
  while (i < 10) {

    //val out = new PrintWriter(socket.getOutputStream,true)
    val input = LogGenerator.printLog()

    i = i + 1
    out.println( /*input*/ "sss" + i + "\n\r")
    // out.flush()

    Thread.sleep(3000)
  }
  out.close()
  socket.close()

}