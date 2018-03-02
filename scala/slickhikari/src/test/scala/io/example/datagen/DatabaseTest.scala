package io.example.datagen

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.Matchers
import org.scalatest.FlatSpecLike

import slick.dbio.DBIO
import slick.jdbc.H2Profile.api._

import io.example.cp.DatabaseLayer
import org.scalatest.BeforeAndAfter

import scala.concurrent.Await
import scala.concurrent.duration._


@RunWith(classOf[JUnitRunner])
class DatabaseTest
  extends FlatSpecLike
    with BeforeAndAfter
    with Matchers
{
    
//  import db_layer.User.table
  
//  var db: Database = _
//  var db_layer = new DatabaseLayer(slick.jdbc.H2Profile)
//  val query    = db_layer.User.all

  
  before {
//    db = Database.forConfig("h2")
//    val f = db.run(setup)
    
//    Await.result(f, 1 second)
  }
  
//  val setup = DBIO.seq(table.schema.create)
//  val teardown = DBIO.seq(table.schema.drop)
  
}