package io.example.cp

import scala.concurrent.Await
import scala.concurrent.duration._

object SlickHelper
{
  lazy val pg = slick.jdbc.PostgresProfile.api.Database.forConfig("postgres")
  lazy val pp = slick.jdbc.PostgresProfile.api.Database.forConfig("postgrespool")
 
  lazy val h2 = slick.jdbc.H2Profile.api.Database.forConfig("h2");
  lazy val hp = slick.jdbc.H2Profile.api.Database.forConfig("h2pool");
  

  def pg_exec[T](program: slick.jdbc.PostgresProfile.api.DBIO[T]): T = Await.result(pg.run(program), 2 seconds)
  def pp_exec[T](program: slick.jdbc.PostgresProfile.api.DBIO[T]): T = Await.result(pp.run(program), 2 seconds)
  
  def h2_exec[T](program: slick.jdbc.H2Profile.api.DBIO[T]): T = Await.result(h2.run(program), 2 seconds)
  def hp_exec[T](program: slick.jdbc.H2Profile.api.DBIO[T]): T = Await.result(hp.run(program), 2 seconds)
  
}