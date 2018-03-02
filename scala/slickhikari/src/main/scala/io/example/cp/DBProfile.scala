package io.example.cp

import slick.jdbc.JdbcProfile

trait DBProfile
{
	val profile: JdbcProfile
}