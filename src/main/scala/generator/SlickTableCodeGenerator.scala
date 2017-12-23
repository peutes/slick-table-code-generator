package generator

import com.typesafe.config.{ Config, ConfigFactory }
import slick.driver.MySQLDriver.api._
import slick.driver.MySQLDriver.backend.DatabaseDef
import slick.model.{ Model, QualifiedName, Table }

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

  val model: Model = Await.result(
    db.run(CustomMySQLProfile.createModel().withPinnedSession)
      .map(createModelWithoutSchema),
    Duration.Inf
  )

  new CustomSourceCodeGenerator(model).writeToFile(
    config.getString("db.slick.profile"),
    outputDir,
    packageName,
    tablesName,
    tablesName + ".scala"
  )

  private[this] def createModelWithoutSchema(model: Model): Model = {
    val tables: Seq[Table] = model.tables.map((table: Table) => Table(
      name = QualifiedName(table = table.name.table, schema = None, catalog = table.name.catalog),
      columns = table.columns,
      primaryKey = table.primaryKey,
      foreignKeys = table.foreignKeys,
      indices = table.indices,
      options = table.options
    ))
    Model(tables, model.options)
  }
}
