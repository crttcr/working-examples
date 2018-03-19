package io.example.cp

object ConfigDebug extends App
{
  lazy val pp = slick.jdbc.PostgresProfile.api.Database.forConfig("postgrespool")
 
  println(pp)
}