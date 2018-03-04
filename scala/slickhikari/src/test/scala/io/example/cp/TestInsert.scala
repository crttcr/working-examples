package io.example.cp

import DTO._
import scala.concurrent.Await
import scala.concurrent.duration._
import slick.jdbc.H2Profile
import slick.jdbc.H2Profile.backend.{DatabaseDef => H2DB}
import slick.jdbc.PostgresProfile
import slick.jdbc.PostgresProfile.backend.{DatabaseDef => PGDB}
import slick.jdbc.JdbcProfile
import slick.jdbc.JdbcBackend.Database
import io.example.datagen.Generator
import org.apache.commons.lang3.time.StopWatch
import io.example.util.DurationFormat
import io.example.tally.DBName._
import io.example.tally.Pool._

import io.example.tally.ResultTabulator._

object TestInsert extends App
{
  val iterations = 1000

  val w = new StopWatch
  
  for (r <- makeRunners)
  {
    val name = r.dim.name
    r.setup()
    w.start()
    r.run(iterations)
    val time = w.getNanoTime
    val dur = Duration(time, NANOSECONDS)
    val fmt = DurationFormat.format(dur)
    println(s"Runner[$name] duration:  [$fmt]")
    w.reset()
    r.teardown()
  }

  def makeRunners: Seq[Runner] = 
  {
    val h2_layer = new DatabaseLayer(H2Profile)
    val pg_layer = new DatabaseLayer(PostgresProfile) 
      val d1 = Dim(H2, NO_POOL, iterations)
      val d2 = Dim(H2, HIKARI, iterations)
      val d3 = Dim(Postgres, NO_POOL, iterations)
      val d4 = Dim(Postgres, HIKARI, iterations)  
      
  val h2r = new H2Runner(d1, h2_layer, SlickHelper.h2)
  val hpr = new H2Runner(d2, h2_layer, SlickHelper.hp)
  val pgr = new PGRunner(d3, h2_layer, SlickHelper.pg)
  val ppr = new PGRunner(d4, h2_layer, SlickHelper.pp)
  
  val runners = List(h2r, hpr, pgr, ppr)
  
    runners
  }
}

trait Runner
{
  val gen = new Generator()
  
  def dim: Dim
  def setup(): Unit
  def run(iterations: Int)
  def teardown(): Unit

}

class PGRunner(val dim: Dim, val layer: DatabaseLayer, db: PGDB) extends Runner
{
  
  def run(iterations: Int) = 
  {
    for (i <-  0 until iterations)
    {
      val      u = gen.user(i)
      val insert = layer.User.insert(u)
      val      f = db.run(insert)
      val      x = Await.result(f, 2 seconds)
    }
  }
  
  def setup(): Unit = 
  {
    val create = layer.User.create
    val future = db.run(create)
    Await.result(future, 2 seconds)
  }
  
  def teardown() = 
  {
    val program = layer.User.drop
    val  future = db.run(program)
    Await.result(future, 2 seconds)    
  }
  
}

class H2Runner(val dim: Dim, layer: DatabaseLayer, db: H2DB) extends Runner
{
  def run(iterations: Int) =
  {
    for (i <-  0 until iterations)
   {
      val      u = gen.user(i)
      val insert = layer.User.insert(u)
      val      f = db.run(insert)
      val      x = Await.result(f, 2 seconds)
    }
  }
  
  def setup(): Unit = 
  {
    val create = layer.User.create
    val future = db.run(create)
    Await.result(future, 2 seconds)
  }
  
  def teardown() = 
  {
    val program = layer.User.drop
    val  future = db.run(program)
    Await.result(future, 2 seconds)    
  }
}