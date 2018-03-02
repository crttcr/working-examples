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
class GeneratorTest
  extends FlatSpecLike
    with BeforeAndAfter
    with Matchers
{
  def fixture = new
  {
    val gen = new Generator
  }

  
  behavior of "name"
  
  it should "Produce a random name when called" in 
  {
    // Arrange
    //
    val f = fixture

    // Act
    //
    val (first, last) = f.gen.name()
    val email = f.gen.email(first, last)
    
    // Assert
    //
    email should not be (null)
    println(email)
  }
  
    behavior of "user"
  
  it should "Produce a random User object when called" in 
  {
    // Arrange
    //
    val f = fixture

    // Act
    //
    val user = f.gen.user(34)
    
    // Assert
    //
    user should not be (null)
    println(user)
  }
}