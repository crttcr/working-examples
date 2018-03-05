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
import java.util.concurrent.atomic.AtomicInteger
import scala.util.Success
import scala.util.Failure
import slick.jdbc.JdbcBackend.DatabaseDef
import io.example.tally.DBName
import io.example.tally.Pool
import io.example.tally.IOStyle
import io.example.exec._


object TestInsert extends App {
   val iterations = 500

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
      
      val dims = for 
      {
         db <- DBName.values
         pl <- Pool.values
         io <- IOStyle.values
      } yield Dim(db, pl, io)
    
      var runners = List.empty[Runner]
      
      for (d <- dims)
      {
         val r = d.db match
         {
            case DBName.H2       => 
               val helper = if (d.pool == HIKARI) SlickHelper.hp else SlickHelper.h2
               if (d.io == IOStyle.Async) new AsyncRunner(d, h2_dao, helper) else new BlockRunner(d, h2_dao, helper)
            case DBName.Postgres => 
               val helper = if (d.pool == HIKARI) SlickHelper.pp else SlickHelper.pg
               if (d.io == IOStyle.Async) new AsyncRunner(d, pg_dao, helper) else new BlockRunner(d, pg_dao, helper)
         }

         runners = r :: runners
      }
      
      runners
   }

}
