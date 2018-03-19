package io.example.pattern.cake.repo

object ComponentRegistry 
	extends UserServiceComponent
		with UserRepositoryComponent
{
	val repo = new UserRepository	
	val userService = new UserService
  
}

object Program extends App
{
	val   us = ComponentRegistry.userService
	val user = us.authenticate("Joe", "ABC123")
}