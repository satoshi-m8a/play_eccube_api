package models

import anorm.{TypeDoesNotMatch, MetaDataItem, Column}

object AnormConversions {
  implicit def rowToLong: Column[Long] = Column.nonNull { (value, meta) =>
    val MetaDataItem(qualified, nullable, clazz) = meta
    value match {
      case int: Int => Right(int: Long)
      case long: Long => Right(long)
      case bigDecimal: java.math.BigDecimal => Right(bigDecimal.longValue)
      case _ => Left(TypeDoesNotMatch("Cannot convert " + value + ":" + value.asInstanceOf[AnyRef].getClass + " to Long for column " + qualified))
    }
  }
}
