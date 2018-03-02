package io.example.cp

import slick.jdbc.JdbcProfile

import io.example.cp.DTO.User

trait UserDAO 
{
   self: DBProfile =>
   import profile.api._
   
   ///////////////////////////////////
   // Functional-Relational Mapping //
   ///////////////////////////////////
   
   final class UserTable(tag: Tag)
     extends Table[User](tag, "usr")
  {
     def id      = column[Int]("id")
     def email   = column[String]("email")
     def first   = column[String]("first")
     def last    = column[String]("last")
     
     def pk      = primaryKey("usr_pk", (id))
     
     def * = (id, email, first, last).mapTo[User]
  }
   
   ///////////////////////////////////
   // Public Interface              //
   ///////////////////////////////////
   
   object User
   {
     def  table = TableQuery[UserTable]
     def create = table.schema.create
     def    all = table.result
     
     def u4id(id: Int)   = findById(id).result
     def save(u: User)   = table.insertOrUpdate(u)
     def insert(u: User) = table += u
     
   }
   
   ///////////////////////////////////
   // Public Interface              //
   ///////////////////////////////////
   
   private def findById(id: Int) = User.table.filter(_.id === id)
   
}