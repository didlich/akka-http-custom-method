package sample


object App {
  def main(args: Array[String]) {
    println("Run Akka-HTTP Server")
    WebServer.run()
  }
}
