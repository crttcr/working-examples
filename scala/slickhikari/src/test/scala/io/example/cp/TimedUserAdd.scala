package io.example.cp
import DTO._

import slick.jdbc.H2Profile
import org.apache.commons.lang3.time.StopWatch
import io.example.datagen.Generator
import io.example.util.SlickElf

object TimedUserAdd extends App
{
  val ONE_K = 100000
  
  val db_layer = new DatabaseLayer(H2Profile)
  val generator = new Generator
  
  val create = db_layer.User.create
  val r1 = SlickElf.hp_exec(create)
 
  val w = StopWatch.createStarted()

  for (i <- 0 until ONE_K)
  {
    val ux: User = generator.user(i)
    val insert = db_layer.User.insert(ux)    
    val r2 = SlickElf.h2_exec(insert)
  }

  val split = w.getNanoTime
  
  val select = db_layer.User.count
  val r3 = SlickElf.hp_exec(select)
  val end = w.getNanoTime;

  println(s"Added [$r3] users in [$split] nanos. Total time: [$end]")

}