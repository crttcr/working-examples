package io.example.cp

import DTO._

import slick.jdbc.PostgresProfile
import io.example.util.SlickElf

object AddUserPostgres extends App
{
  val db_layer = new DatabaseLayer(PostgresProfile)
  
  val u = User(-2, "person@aol.com", "Pers", "On")
  
  println(s"Adding user [$u]")
//  val create = db_layer.User.create
  val insert = db_layer.User.insert(u)
  val select = db_layer.User.all
  
//  val r1 = SlickHelper.pg_exec(create)
//  val r2 = SlickHelper.pg_exec(insert)
  val r3 = SlickElf.pg_exec(select)
    
//  println(s"Create: [$r1]")
//  println(s"Insert: [$r2]")
  println(s"Select: [$r3]")
}