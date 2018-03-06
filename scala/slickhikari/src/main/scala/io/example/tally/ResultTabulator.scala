package io.example.tally

import io.example.tally._
import io.example.tally.DBName._
import io.example.tally.Pool._
import io.example.tally.IOStyle._
import scala.concurrent.duration.Duration
import java.util.concurrent.TimeUnit.NANOSECONDS
import io.example.util.DurationFormat


object ResultTabulator 
{
   val ONE_BILLION = 1000000000L
   
  case class Result(nanos: Long, iterations: Int)
  {
     override def toString(): String = 
     {
        val dur = Duration(nanos, NANOSECONDS)
        val fmt = DurationFormat.format(dur)
        f"[nanos = $fmt%s][iterations = $iterations%8d]"
     }

  }
  case class Stats(
        runs: Int, 
        total_iterations: Int, 
        total_nanos: Long, 
        ops_per_sec_best: Float,
        ops_per_sec_avg: Float,
        ops_per_sec_worst: Float,
        percent_of_best: Float
        )
        {
      override def toString(): String = 
         /*    
        val dur = Duration(time, NANOSECONDS)
    val fmt = DurationFormat.format(dur)
    println(s"Runner[$name] duration:  [$fmt]")
    
*/
          f"[$runs%4d][$total_iterations%8d][$total_nanos%12d][$ops_per_sec_best%8.0f][$ops_per_sec_avg%8.0f][$ops_per_sec_worst%8.0f]    [$percent_of_best%5.1f]"

        }
  
  
   case class Dim(db: DBName, pool: Pool, io: IOStyle) extends Ordered[Dim]
   {   
      def name = List(db, pool).mkString(".")
    override def toString() = 
    {
     f"[$db%8s][$pool%7s][$io%5s]"
     }
      
     import scala.math.Ordered.orderingToOrdered

     def compare(that: Dim): Int = (this.db, this.pool, this.io) compare (that.db, that.pool, that.io)
  }
  
  def apply() = new ResultTabulator()
}

class ResultTabulator
{
  import ResultTabulator._
  
  val results = collection.mutable.Map.empty[Dim, Seq[Result]]
  
  def collect(d: Dim, nanos: Long, iterations: Int): Unit = 
  {
    val r = Result(nanos, iterations)
    collect(d, r)
  }
  
 def collect(d: Dim, r: Result): Unit = 
  {
    val list = results.getOrElse(d, Vector())
    val  add = list :+ r
    results += (d -> add)
  }
  
  def stats(rs: Seq[Result]): Stats =
  {
     val runs = rs.size
     val tits = rs.map(_.iterations).sum
     val tnan = rs.map(_.nanos).sum
     
     
     def throughput(iterations: Int, nanos: Long): Float = 
     {
        iterations.toFloat / (nanos.toFloat / ONE_BILLION)
     }
      
     var  th_best = Float.NaN
     var   th_avg = throughput(tits, tnan)
     var th_worst = Float.NaN
     var pct_base = Float.NaN
     
     for (r <- rs)
     {
        val    t = throughput(r.iterations, r.nanos)
        th_best  = if ( th_best.isNaN()) t else if (th_best  < t) t else th_best
        th_worst = if (th_worst.isNaN()) t else if (th_worst > t) t else th_worst
     }
     
      Stats(runs, tits, tnan, th_best, th_avg, th_worst, pct_base)
  }
  
  def computeFactors(stats: Seq[(Dim, Stats)]): Seq[(Dim, Stats)] =
  {
    val (ps, hs) = stats.partition(_._1.db == Postgres)
    
    val min_ps = ps.map(_._2.ops_per_sec_avg).min
    val min_hs = hs.map(_._2.ops_per_sec_avg).min
    
    def factorate(stats: Stats, min: Float): Stats = {
      val  found = stats.ops_per_sec_avg
      val   diff = found - min
      val factor = 1 + diff / min
      
      stats.copy(percent_of_best = factor)
    }
    
    val ps_revised = ps.map(t => (t._1, factorate(t._2, min_ps)))
    val hs_revised = hs.map(t => (t._1, factorate(t._2, min_hs))) 
    
    ps_revised ++ hs_revised
  }
  
  def report: String =
  {
    val tuples = results.toSeq
    val revised = tuples.map(t => (t._1, stats(t._2)))
    val factored = computeFactors(revised)
    val sorted = factored.sortBy(_._1)
        
    val   sb = new StringBuilder()
     
     sb.append("                                                             ----------OPS/SECOND---------      PERF\n")
     sb.append("-DATABASE- --CP--- --IO--      RUNS   INSERTS   TOTAL_NANOS      BEST       AVG     WORST     FACTOR\n")
     for (t <- sorted)
     {
        sb.append(t._1)
        sb.append(" :: ")
        sb.append(t._2)
        sb.append("\n")
     }

    sb.toString
  }
}