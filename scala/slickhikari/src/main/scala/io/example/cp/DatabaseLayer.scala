package io.example.cp

import slick.jdbc.JdbcProfile

class DatabaseLayer(val profile: JdbcProfile)
  extends DBProfile
  with UserDAO
{
  
}