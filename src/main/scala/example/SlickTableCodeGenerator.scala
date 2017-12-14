package example

import com.typesafe.config.ConfigFactory
import slick.codegen.SourceCodeGenerator

object SlickTableCodeGenerator extends App {

  val config = ConfigFactory.load
  SourceCodeGenerator.main(Array(
    config.getString("db.slick.driver"),
    config.getString("db.jdbc.driver"),
    config.getString("db.url"),
    config.getString("output.dir"),
    config.getString("package"),
    config.getString("db.user"),
    config.getString("db.password")
  ))
}
