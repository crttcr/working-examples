package io.example.cp

import DTO._

import slick.jdbc.H2Profile
import io.example.util.SlickElf

object AddUser extends App
{
  val db_layer = new DatabaseLayer(H2Profile)
  
  val u = User(45, "person@aol.com", "Pers", "On")
  
  println(s"Adding user [$u]")
  val create = db_layer.User.create
  val insert = db_layer.User.insert(u)
  val select = db_layer.User.all
  
  val r1 = SlickElf.h2_exec(create)
  val r2 = SlickElf.h2_exec(insert)
  val r3 = SlickElf.h2_exec(select)
    
  println(s"Create: [$r1]")
  println(s"Insert: [$r2]")
  println(s"Select: [$r3]")
}