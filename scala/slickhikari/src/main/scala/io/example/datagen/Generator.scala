package io.example.datagen

import io.example.cp.DTO.User


object Generator 
{
  val random = new scala.util.Random
  
  val first = Array(
      "Arnie", "Al", "Andy", "Amos", "Amy", "Ann", "Anne", "Annette",
      "Bill", "Buck", "Bob", "Brick", "Ben", "Beth", "Betsy", "Blithe", "Bone", "Babs", "Barbara",
      "Chaz", "Charles", "Chuck", "Chris", "Carlton", "Courtney", "Corbett", "Claire", "Cindy", "Coco",
      "Dick", "Dave", "David", "Dilbert", "Dan", "Dana", "Delilah", "Debbie", "Denise",   
      )
      
    val last = Array(
      "Anderson", "Anthony", "Adams", "Avery", "Apple", "Anesia", "Albright", "Axlerod", "Apeface",
      "Bowers", "Beavers", "Beason", "Butkis", "Beverly", "Benson", "Bennett", "Bengas", "Boniface",
      "Carter", "Carlson", "Caprioti", "Clevis", "Chinchilla", "Charles", "Conifer", "Clover", "Covis",
      "Dickens", "Davis", "Davids", "Dilbertson", "Daniels",
      "Early", "Evans", "Escher", "Edwards", "Electra",
      "Fields", "Farmer", "Fullbright", "Flannery", "Ferris", "Franks",
      "Lee",
      "Mathis",
      "Turner", 
      )
      
    val domains = Array(
        "me.com", "apple.com", "apple.org", 
        "google.com", "gmail.com", "aol.com", "bellsouth.net",
        "boogie.io", "pobox.com", "uptake.org", "cnn.com", "microsoft.com",
      )

}

class Generator {
  import Generator._
  
  def email(first: String, last: String): String = 
  {
    val d = randomDomain
    val n = randomEmailName(first, last)
    
    s"$n@$d"
  }
  
  def user(id: Int): User = 
  {
    val (f, l) = name()
    val      e = email(f, l)
    val   user = new User(id, e, f, l)
    user
  }
  
  def name() = {
    (randomFirst, randomLast)
  }
    
  def names(count: Int = 1): Seq[(String, String)] = 
  {
    val buf = scala.collection.mutable.ArrayBuffer.empty[(String, String)]
    for (i <- 0 to count) 
    {
      val n = name()
      buf += n
    }
    
    buf
  }
 
  private def randomFirst: String = {
    val i = Generator.random.nextInt(first.length)
    val n = first(i)
    n
  }
  
  private def randomLast: String = {
    val i = Generator.random.nextInt(last.length)
    val n = last(i)
    n
  }
  
  private def randomDomain: String = {
    val i = Generator.random.nextInt(domains.length)
    val n = domains(i)
    n
  }
  
  private def randomEmailName(f: String, l: String): String = {
    
    val i = Generator.random.nextInt(100)
    if (i < 70)
    {
      s"$f.$l"      
    } else if (i < 90) 
    {
      val letter = f.take(1)
      s"$letter$l"
    }
    else 
    {
      val      n = Generator.random.nextInt(500)
      val letter = f.take(1)
      letter + l + n.toString
    }
  }
}