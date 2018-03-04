package io.example.tally

object ResultTabulator 
{
  
  case class Dim(db: DBName, pool: Pool, iterations: Int)
  {
    
    def name = List(db, pool, iterations).mkString(".")
  }
  
}