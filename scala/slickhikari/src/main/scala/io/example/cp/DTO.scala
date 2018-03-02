package io.example.cp

import play.api.libs.json._

object DTO 
{
  implicit val userWrites = Json.writes[User]
  implicit val userReads  = Json.reads[User]
  
  final case class User(
      id:     Int,
      email:   String,
      first:   String,
      last:    String)
      {
        def name = s"$first $last"
      }
   
}