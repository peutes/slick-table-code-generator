# SlickTableCodeGenerator
This application generate Slick Table code from mysql schema.
It uses a customized SlickCodeGen.

The feature is
* This converts `Timestamp`　to `DateTime`, `Date`　to `LocalDate`, `Time` to `LocalTime` respectivary and uses `Joda-Time`.
* `Timestamp` default `null` and `CURRENT_TIMESTAMP` can use `Option`. ←←
* `AUTO_INCREMENT` column such as Id can use `Option`.
* It is independent of schema and db name.
* You can customize by editing `application.conf`. For exmaple, you can change output directory, package name, Tables name.

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
  `time_without_null` time NOT NULL,                                                                              
  `time_default_null` time DEFAULT NULL,                                                                          
  `year_without_null` year(4) NOT NULL,                                                                           
  PRIMARY KEY (`id`)                                                                                              
) ENGINE=InnoDB DEFAULT CHARSET=utf8                                                                              
```

Generator automatically generates code as.<br>
In this example, `Sample` Class and `SampleRow` Case Class can be used.

```
  final case class SampleRow(id: Option[Int] = None, text: Option[String], numberDefaultNull: Option[Int], numberWithoutNull: Int = 0, timestampDefaultNull: Option[DateTime], timestampDefaultCurrent: Option[DateTime], timestampDefaultCurrentOnUpdate: Option[DateTime], datetimeWithoutNull: DateTime, datetimeDefaultNull: Option[DateTime], datetimeDefaultCurrent: Option[DateTime], dateWithoutNull: LocalDate, dateDefaultNull: Option[LocalDate], timeWithoutNull: LocalTime, timeDefaultNull: Option[LocalTime], yearWithoutNull: LocalDate)
  /** GetResult implicit for fetching SampleRow objects using plain SQL queries */
  implicit def GetResultSampleRow(implicit e0: GR[Option[Int]], e1: GR[Option[String]], e2: GR[Int], e3: GR[Option[DateTime]], e4: GR[DateTime], e5: GR[LocalDate], e6: GR[Option[LocalDate]], e7: GR[LocalTime], e8: GR[Option[LocalTime]]): GR[SampleRow] = GR {
    prs =>
      import prs._
      SampleRow.tupled((<<?[Int], <<?[String], <<?[Int], <<[Int], <<?[DateTime], <<[Option[DateTime]], <<[Option[DateTime]], <<[DateTime], <<?[DateTime], <<?[DateTime], <<[LocalDate], <<?[LocalDate], <<[LocalTime], <<?[LocalTime], <<[LocalDate]))
  }
  /** Table description of table sample. Objects of this class serve as prototypes for rows in queries. */
  class Sample(_tableTag: Tag) extends profile.api.Table[SampleRow](_tableTag, "sample") {
    def * = (Rep.Some(id), text, numberDefaultNull, numberWithoutNull, timestampDefaultNull, timestampDefaultCurrent, timestampDefaultCurrentOnUpdate, datetimeWithoutNull, datetimeDefaultNull, datetimeDefaultCurrent, dateWithoutNull, dateDefaultNull, timeWithoutNull, timeDefaultNull, yearWithoutNull) <> (SampleRow.tupled, SampleRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), text, numberDefaultNull, Rep.Some(numberWithoutNull), timestampDefaultNull, Rep.Some(timestampDefaultCurrent), Rep.Some(timestampDefaultCurrentOnUpdate), Rep.Some(datetimeWithoutNull), datetimeDefaultNull, datetimeDefaultCurrent, Rep.Some(dateWithoutNull), dateDefaultNull, Rep.Some(timeWithoutNull), timeDefaultNull, Rep.Some(yearWithoutNull)).shaped.<>({ r => import r._; _1.map(_ => SampleRow.tupled((_1, _2, _3, _4.get, _5, _6.get, _7.get, _8.get, _9, _10, _11.get, _12, _13.get, _14, _15.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column text SqlType(VARCHAR), Length(128,true) */
    val text: Rep[Option[String]] = column[Option[String]]("text", O.Length(128, varying = true))
    /** Database column number_default_null SqlType(INT) */
    val numberDefaultNull: Rep[Option[Int]] = column[Option[Int]]("number_default_null")
    /** Database column number_without_null SqlType(INT), Default(0) */
    val numberWithoutNull: Rep[Int] = column[Int]("number_without_null", O.Default(0))
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
    val datetimeDefaultCurrent: Rep[Option[DateTime]] = column[Option[DateTime]]("datetime_default_current")
    /** Database column date_without_null SqlType(DATE) */
    val dateWithoutNull: Rep[LocalDate] = column[LocalDate]("date_without_null")
    /** Database column date_default_null SqlType(DATE) */
    val dateDefaultNull: Rep[Option[LocalDate]] = column[Option[LocalDate]]("date_default_null")
    /** Database column time_without_null SqlType(TIME) */
    val timeWithoutNull: Rep[LocalTime] = column[LocalTime]("time_without_null")
    /** Database column time_default_null SqlType(TIME) */
    val timeDefaultNull: Rep[Option[LocalTime]] = column[Option[LocalTime]]("time_default_null")
    /** Database column year_without_null SqlType(YEAR) */
    val yearWithoutNull: Rep[LocalDate] = column[LocalDate]("year_without_null")
  }
  /** Collection-like TableQuery object for table Sample */
  lazy val Sample = new TableQuery(tag => new Sample(tag))
}
```

# Using

You need to edit config `conf/application.conf` to run.
Especially edit `db`.<br>
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

# Run
```
sbt run
```

# Other
## Used Slick Version
* "com.typesafe.slick" %% "slick" % "3.2.0",
* "com.typesafe.slick" %% "slick-codegen" % "3.2.0",
