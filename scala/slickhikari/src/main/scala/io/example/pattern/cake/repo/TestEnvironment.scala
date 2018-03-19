package io.example.pattern.cake.repo

import org.scalamock.scalatest.MockFactory

trait TestEnvironment 
	extends UserServiceComponent
		with UserRepositoryComponent
		with MockFactory
{

	val repo = mock[UserRepository]
	val userService = mock[UserService]

}