package network
import java.net._
import java.io._
import scala.io._

object SocketClientTEST extends App {

    val socket =   new Socket("localhost",9999)
    println("accepting")
    //val client = server.accept

    //while (true) {

      val in = new BufferedReader(new InputStreamReader(socket.getInputStream))
     val out = new PrintStream(socket.getOutputStream)

    println("type something")
    val input=readLine
    out.println(input)
    val s= in.readLine()
    println("server responded:" +s)
  
      
      
   // }
  

}