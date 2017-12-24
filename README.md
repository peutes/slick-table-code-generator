# SlickTableCodeGenerator
This application generate Slick Table code from mysql schema.

The feature is
* This converts `Timestamp`　to `DateTime` of `Joda-Time`, `Date`　to `LocalDate` of `Joda-Time`, `Time` and to `LocalTime` respectivary and uses `Joda-Time`.
* `AUTO_INCREMENT` column such as Id can use `Option`.
* It is independent of schema and db name.

There is a sample table as.
```
CREATE TABLE `sample` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `text` varchar(128) DEFAULT NULL,
  `number_default_null` int(11) DEFAULT NULL,
  `number_without_null` int(11) NOT NULL DEFAULT '0',
  `timestamp_default_null` timestamp NULL DEFAULT NULL,
  `timestamp_default_current` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `timestamp_default_current_on_update` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `datetime_without_null` datetime NOT NULL,
  `datetime_default_null` datetime DEFAULT NULL,
  `datetime_default_current` datetime DEFAULT CURRENT_TIMESTAMP,
  `date_without_null` date NOT NULL,
  `date_default_null` date DEFAULT NULL,
  `year_without_null` year(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
```

Generator automatically generates code as.

```
  final case class SampleRow(id: Option[Int] = None, text: Option[String], numberDefaultNull: Option[Int], numberWithoutNull: Option[Int], timestampDefaultNull: Option[DateTime], timestampDefaultCurrent: Option[DateTime], timestampDefaultCurrentOnUpdate: Option[DateTime], datetimeWithoutNull: DateTime, datetimeDefaultNull: Option[DateTime], datetimeDefaultCurrent: Option[Option[DateTime]], dateWithoutNull: LocalDate, dateDefaultNull: Option[LocalDate], yearWithoutNull: LocalDate)
  /** GetResult implicit for fetching SampleRow objects using plain SQL queries */
  implicit def GetResultSampleRow(implicit e0: GR[Option[Int]], e1: GR[Option[String]], e2: GR[Option[DateTime]], e3: GR[DateTime], e4: GR[Option[Option[DateTime]]], e5: GR[LocalDate], e6: GR[Option[LocalDate]]): GR[SampleRow] = GR{
    prs => import prs._
    SampleRow.tupled((<<?[Int], <<?[String], <<?[Int], <<[Option[Int]], <<?[DateTime], <<[Option[DateTime]], <<[Option[DateTime]], <<[DateTime], <<?[DateTime], <<?[Option[DateTime]], <<[LocalDate], <<?[LocalDate], <<[LocalDate]))
  }
  /** Table description of table sample. Objects of this class serve as prototypes for rows in queries. */
  class Sample(_tableTag: Tag) extends profile.api.Table[SampleRow](_tableTag, "sample") {
    def * = (Rep.Some(id), text, numberDefaultNull, numberWithoutNull, timestampDefaultNull, timestampDefaultCurrent, timestampDefaultCurrentOnUpdate, datetimeWithoutNull, datetimeDefaultNull, datetimeDefaultCurrent, dateWithoutNull, dateDefaultNull, yearWithoutNull) <> (SampleRow.tupled, SampleRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), text, numberDefaultNull, Rep.Some(numberWithoutNull), timestampDefaultNull, Rep.Some(timestampDefaultCurrent), Rep.Some(timestampDefaultCurrentOnUpdate), Rep.Some(datetimeWithoutNull), datetimeDefaultNull, datetimeDefaultCurrent, Rep.Some(dateWithoutNull), dateDefaultNull, Rep.Some(yearWithoutNull)).shaped.<>({r=>import r._; _1.map(_=> SampleRow.tupled((_1, _2, _3, _4.get, _5, _6.get, _7.get, _8.get, _9, _10, _11.get, _12, _13.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column text SqlType(VARCHAR), Length(128,true) */
    val text: Rep[Option[String]] = column[Option[String]]("text", O.Length(128,varying=true))
    /** Database column number_default_null SqlType(INT) */
    val numberDefaultNull: Rep[Option[Int]] = column[Option[Int]]("number_default_null")
    /** Database column number_without_null SqlType(INT) */
    val numberWithoutNull: Rep[Option[Int]] = column[Option[Int]]("number_without_null")
    /** Database column timestamp_default_null SqlType(TIMESTAMP) */
    val timestampDefaultNull: Rep[Option[DateTime]] = column[Option[DateTime]]("timestamp_default_null")
    /** Database column timestamp_default_current SqlType(TIMESTAMP) */
    val timestampDefaultCurrent: Rep[Option[DateTime]] = column[Option[DateTime]]("timestamp_default_current")
    /** Database column timestamp_default_current_on_update SqlType(TIMESTAMP) */
    val timestampDefaultCurrentOnUpdate: Rep[Option[DateTime]] = column[Option[DateTime]]("timestamp_default_current_on_update")
    /** Database column datetime_without_null SqlType(DATETIME) */
    val datetimeWithoutNull: Rep[DateTime] = column[DateTime]("datetime_without_null")
    /** Database column datetime_default_null SqlType(DATETIME) */
    val datetimeDefaultNull: Rep[Option[DateTime]] = column[Option[DateTime]]("datetime_default_null")
    /** Database column datetime_default_current SqlType(DATETIME) */
    val datetimeDefaultCurrent: Rep[Option[Option[DateTime]]] = column[Option[Option[DateTime]]]("datetime_default_current")
    /** Database column date_without_null SqlType(DATE) */
    val dateWithoutNull: Rep[LocalDate] = column[LocalDate]("date_without_null")
    /** Database column date_default_null SqlType(DATE) */
    val dateDefaultNull: Rep[Option[LocalDate]] = column[Option[LocalDate]]("date_default_null")
    /** Database column year_without_null SqlType(YEAR) */
    val yearWithoutNull: Rep[LocalDate] = column[LocalDate]("year_without_null")
  }
  /** Collection-like TableQuery object for table Sample */
  lazy val Sample = new TableQuery(tag => new Sample(tag))
}
```

# Using
You need to edit config `conf/application.conf` to run.
Especially set `db`, `package.name`.<br>
This is used for DB connection and output.
Other configs can also be edited.

```
db = {
  url = "jdbc:mysql://localhost/eudyptes?useSSL=false&nullNamePatternMatchesAll=true"
  jdbc.driver = "com.mysql.cj.jdbc.Driver"
  slick.profile = "slick.jdbc.MySQLProfile"
  user = "root"
  password = ""
}
table.name = "Tables"
package.name = "infrastructure.sample"
output.dir = "./output"
customType = {
  timestamp = "DateTime"
  date = "LocalDate"
  time = "LocalTime"
}
```

### run
```
sbt run
```
