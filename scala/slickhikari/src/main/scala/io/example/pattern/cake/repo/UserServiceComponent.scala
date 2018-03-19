package io.example.pattern.cake.repo

trait UserServiceComponent
{
	// Declaring a self type of URC makes available all the resources defined by the URC
	// (in this case 'repo')
	//
	this: UserRepositoryComponent =>
	
	val userService: UserService
	
	class UserService
	{
		
		def authenticate(name: String, password: String): User =
		{
			val u = User(name, password)
			repo.authenticate(u)
		}
	  
		def create(name: String, password: String) =
		{
			val u = User(name, password)
			repo.create(u)
		}

		def delete(user: User) = repo.delete(user)
	}
	  
}