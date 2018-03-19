package io.example.pattern.cake.repo

class UserRepo 
{	
	def authenticate(user: User): User =
	{
		println("Authenticating user: " + user)
		user
	}
  
	def create(user: User) = println("Creating user: " + user)
	def delete(user: User) = println("Creating user: " + user)
  
}