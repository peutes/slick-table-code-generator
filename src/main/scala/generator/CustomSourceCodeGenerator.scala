package generator

import slick.codegen.SourceCodeGenerator
import slick.model.Model

class CustomSourceCodeGenerator(model: Model) extends SourceCodeGenerator(model) {

  override def code = "import com.github.tototoshi.slick.MySQLJodaSupport._\n" + "import org.joda.time.DateTime\n" + super.code

  override def Table = new Table(_) {
    override def Column = new Column(_) {
      override def rawType = model.tpe match {
        case "java.sql.Timestamp" => "DateTime"
        case "java.sql.Date" => "DateTime"
        case _ => super.rawType
      }
    }
  }
}
