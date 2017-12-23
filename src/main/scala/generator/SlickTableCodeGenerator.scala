package generator

import com.typesafe.config.{ Config, ConfigFactory }
import slick.driver.MySQLDriver.api._
import slick.driver.MySQLDriver.backend.DatabaseDef
import slick.model.Model

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

object SlickTableCodeGenerator extends App {

  val config: Config = ConfigFactory.load
  val outputDir = config.getString("output.dir")
  val packageName = config.getString("package.name")
  val tablesName = config.getString("table.name")

  val db: DatabaseDef = Database.forURL(
    url = config.getString("db.url"),
    driver = config.getString("db.jdbc.driver"),
    user = config.getString("db.user"),
    password = config.getString("db.password")
  )

  val model: Model = Await.result(db.run(CustomMySQLProfile.createModel().withPinnedSession), Duration.Inf)

  new CustomSourceCodeGenerator(model).writeToFile(
    config.getString("db.slick.profile"),
    outputDir,
    packageName,
    tablesName,
    tablesName + ".scala"
  )
}
