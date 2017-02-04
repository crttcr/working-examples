// Run with SpringBoot CLI:
// spring run HelloController.groovy

@RestController
class HelloController {
	
	@RequestMapping("/")
	def hello() 
	{
		return "Hello Moto!"
	}
}

