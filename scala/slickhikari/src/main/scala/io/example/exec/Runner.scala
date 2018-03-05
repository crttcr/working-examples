package io.example.exec


import java.util.concurrent.atomic.AtomicInteger

import scala.util.Success
import scala.util.Failure
import scala.concurrent.Await
import scala.concurrent.duration._

import io.example.cp.DatabaseLayer
import io.example.datagen.Generator
import io.example.tally.ResultTabulator
import io.example.tally.ResultTabulator._
import slick.jdbc.H2Profile
import slick.jdbc.H2Profile.backend.{ DatabaseDef => H2DB }
import slick.jdbc.JdbcProfile
import slick.jdbc.PostgresProfile
import slick.jdbc.PostgresProfile.backend.{ DatabaseDef => PGDB }

trait Runner {
   val gen = new Generator()

   def dim: Dim
   def setup(): Unit
   def run(iterations: Int)
   def teardown(): Unit

}


class AsyncRunner(val dim: Dim, val dao: DatabaseLayer, dba: Any) extends Runner {

   val db = if (dba.isInstanceOf[PGDB]) dba.asInstanceOf[PGDB] else dba.asInstanceOf[H2DB]
   
   val done = new AtomicInteger(0);
   implicit val ec = scala.concurrent.ExecutionContext.global
   
   def run(iterations: Int) =
   {
      for (i <- 0 until iterations) {
         val u = gen.user(i)
         val insert = dao.User.insert(u)
         val f = db.run(insert)
        
         f.onComplete
         {
            case Success(n)  => done.incrementAndGet
            case Failure(ex) => done.incrementAndGet ; println(ex.getMessage)
         }
      }
      
      while (done.get < iterations)
      {
         Thread.sleep(1L)
      }
    }

   def setup(): Unit =
   {
      val p = dao.User.create
      val f = db.run(p)
      Await.result(f, 2 seconds)
   }

   def teardown() =
   {
      val p = dao.User.drop
      val f = db.run(p)
      Await.result(f, 2 seconds)
   }

}

class BlockRunner(val dim: Dim, val dao: DatabaseLayer, dba: Any) extends Runner {
   val db = if (dba.isInstanceOf[PGDB]) dba.asInstanceOf[PGDB] else dba.asInstanceOf[H2DB]

   def run(iterations: Int) =
      {
         for (i <- 0 until iterations) {
            val u = gen.user(i)
            val insert = dao.User.insert(u)
            val f = db.run(insert)
            val x = Await.result(f, 2 seconds)
         }
      }

   def setup(): Unit =
   {
      val p = dao.User.create
      val f = db.run(p)
      Await.result(f, 2 seconds)
   }

   def teardown() =
   {
      val p = dao.User.drop
      val f = db.run(p)
      Await.result(f, 2 seconds)
   }

}
