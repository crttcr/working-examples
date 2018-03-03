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

object TestInsert extends App
{
  val iterations = 1000
  
  val h2_layer = new DatabaseLayer(H2Profile)
  val pg_layer = new DatabaseLayer(PostgresProfile) 
  
  val h2r = new H2Runner("H2_No_Pool", h2_layer, SlickHelper.h2)
  val hpr = new H2Runner("H2_HK_Pool", h2_layer, SlickHelper.hp)
  val pgr = new PGRunner("PG_No_Pool", h2_layer, SlickHelper.pg)
  val ppr = new PGRunner("PG_HK_Pool", h2_layer, SlickHelper.pp)
  
  val runners = List(h2r, hpr, pgr, ppr)
  val       w = new StopWatch
  
  for (r <- runners)
  {
    val name = r.name
    val last = r.setup()
    println(s"Last user [$last] from [$name]")
    w.start()
    r.run(iterations, last)
    val time = w.getNanoTime
    val dur = Duration(time, NANOSECONDS)
    val fmt = DurationFormat.format(dur)
    println(s"Runner[$name] duration:  [$fmt]")
    w.reset()
    r.teardown()
  }

}

trait Runner
{
  val gen = new Generator()
  
  def name: String
  def setup(): Int
  def run(iterations: Int, last: Int)
  def teardown(): Unit

}

class PGRunner(val name: String, val layer: DatabaseLayer, db: PGDB) extends Runner
{
  
  def run(iterations: Int, last: Int) = 
  {
    val range = last + 1 until last + iterations + 1
    for (i <- range)
    {
      val      u = gen.user(i)
      val insert = layer.User.insert(u)
      val      f = db.run(insert)
      val      x = Await.result(f, 2 seconds)
    }
  }
  
  def setup(): Int = 
  {
    val create = layer.User.create
    val future = db.run(create)
    Await.result(future, 2 seconds)
    
    0
  }
  
  def teardown() = 
  {
    val drop = layer.User.drop
    val future = db.run(drop)
    Await.result(future, 2 seconds)    
  }
  
}

class H2Runner(val name: String, layer: DatabaseLayer, db: H2DB) extends Runner
{
  // For H2, setup means creating the database; the table will be empty.
  // So return 0 as last ID
  // 
    
  def run(iterations: Int, last: Int) =
  {
    val range = last + 1 until last + iterations + 1
    
    for (i <- range)
    {
      val      u = gen.user(i)
      val insert = layer.User.insert(u)
      val      f = db.run(insert)
      val      x = Await.result(f, 2 seconds)
    }
  }
  
  def setup(): Int = 
  {
    val create = layer.User.create
    val future = db.run(create)
    Await.result(future, 2 seconds)
    
    0
  }
  
  def teardown() = 
  {
    val drop = layer.User.drop
    val future = db.run(drop)
    Await.result(future, 2 seconds)    
  }
}