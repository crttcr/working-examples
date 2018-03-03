package io.example.util

import scala.concurrent.duration.{Duration,FiniteDuration}
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit._

object DurationFormat 
{
  val precision    = 4
  val includeNanos = false
  
  def format(dur: Duration): String = 
  {
       dur match {
        case d: FiniteDuration ⇒
          val nanos = d.toNanos
          val unit  = chooseUnit(dur)
          val value = nanos.toDouble / NANOSECONDS.convert(1, unit)

          s"%.${precision}g %s%s".format(value, abbreviate(unit), if (includeNanos) s" ($nanos ns)" else "")

        case Duration.MinusInf ⇒ s"-∞ (minus infinity)"
        case Duration.Inf      ⇒ s"∞ (infinity)"
        case _                 ⇒ "undefined"
      }
  }
  
     
    def chooseUnit(d: Duration): TimeUnit = 
    {
      if (     d.toDays    > 0) DAYS
      else if (d.toHours   > 0) HOURS
      else if (d.toMinutes > 0) MINUTES
      else if (d.toSeconds > 0) SECONDS
      else if (d.toMillis  > 0) MILLISECONDS
      else if (d.toMicros  > 0) MICROSECONDS
      else                      NANOSECONDS
    }

    def abbreviate(unit: TimeUnit): String = unit match {
      case NANOSECONDS  ⇒ "ns"
      case MICROSECONDS ⇒ "μs"
      case MILLISECONDS ⇒ "ms"
      case SECONDS      ⇒ "s"
      case MINUTES      ⇒ "min"
      case HOURS        ⇒ "h"
      case DAYS         ⇒ "d"
    }
  
}


