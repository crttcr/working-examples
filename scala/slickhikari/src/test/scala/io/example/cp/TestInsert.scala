package io.example.cp

import DTO._

import scala.concurrent.Await
import scala.concurrent.duration._
import slick.jdbc.H2Profile
import slick.jdbc.H2Profile.backend.{ DatabaseDef => H2DB }
import slick.jdbc.PostgresProfile
import slick.jdbc.PostgresProfile.backend.{ DatabaseDef => PGDB }
import slick.jdbc.JdbcProfile
import slick.jdbc.JdbcBackend.Database
import io.example.datagen.Generator
import org.apache.commons.lang3.time.StopWatch
import io.example.util.DurationFormat
import io.example.tally.DBName._
import io.example.tally.Pool._
import io.example.tally.IOStyle._

import io.example.tally.ResultTabulator
import io.example.tally.ResultTabulator._
import slick.dbio.DBIOAction

object TestInsert extends App {
   val iterations = 100

   val w = new StopWatch
   val rt = ResultTabulator()

   for (r <- makeRunners) {
      val name = r.dim.name
      r.setup()
      w.start()
      r.run(iterations)
      rt.collect(r.dim, w.getNanoTime, iterations)
      w.reset()
      r.teardown()
   }
   
   println(rt.report)

   def makeRunners: Seq[Runner] =
   {
      val h2_dao = new DatabaseLayer(H2Profile)
      val pg_dao = new DatabaseLayer(PostgresProfile)
      val d1 = Dim(H2, NO_POOL, Block)
      val d2 = Dim(H2, HIKARI, Block)
      val d3 = Dim(Postgres, NO_POOL, Block)
      val d4 = Dim(Postgres, HIKARI, Block)

      val h2r = new H2Runner(d1, h2_dao, SlickHelper.h2)
      val hpr = new H2Runner(d2, h2_dao, SlickHelper.hp)
      val pgr = new PGRunner(d3, pg_dao, SlickHelper.pg)
      val ppr = new PGRunner(d4, pg_dao, SlickHelper.pp)

      List(h2r, hpr, pgr, ppr)
   }

}

trait Runner {
   val gen = new Generator()

   def dim: Dim
   def setup(): Unit
   def run(iterations: Int)
   def teardown(): Unit

}

class PGRunner(val dim: Dim, val dao: DatabaseLayer, db: PGDB) extends Runner {

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

class H2Runner(val dim: Dim, dao: DatabaseLayer, db: H2DB) extends Runner {
   def run(iterations: Int) =
   {
      for (i <- 0 until iterations) 
      {
         val u = gen.user(i)
         val p = dao.User.insert(u)
         val f = db.run(p)
         val x = Await.result(f, 2 seconds)
      }
   }

   def setup(): Unit =
   {
      val create = dao.User.create
      val future = db.run(create)
      Await.result(future, 2 seconds)
   }

   def teardown() =
   {
      val program = dao.User.drop
      val future = db.run(program)
      Await.result(future, 2 seconds)
   }
}