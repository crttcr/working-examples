# Args - Simple, overachieving option/argument processing

This is yet another library for processing program arguments. Inspired by Robert C. Martin's 
<a href="http://www.amazon.com/Clean-Code-Handbook-Software-Craftmanship/dp/0132350882" target="_blank">
Clean Code</a> chapter 14, this library keeps the simple one-character + modifier specification string for cases
where advanced capabilities are overkill. Additionally, by providing a more detailed specification,
it's possible to provide descriptions, default values, environment variable lookup, and validatation
to ensure programs are started in a consistent state.


## Getting Started

To use this code, simply add the sources or a jar file to your project, describe the program 
options, and pass the program arguments to the Args constructor. If the constructor does not
throw an ArgsException error, then the instance can be used to access program arguments by
name.

```
	public static void main(String[] args)
	{
		try
		{
			Args arg = new Args("l,p#,d*", args);

			String path = arg.getValue("d");
			Integer port = arg.getValue("p");
			Boolean logging = arg.getValue("l");

			run(path, port, logging);
		}
		catch (ArgsException e)
		{
			System.out.printf("Argument error: %s\n", e.errorMessage());
		}
	}

```

### Prerequisites

None, other than adding the sources or jar to your project.

### Installing

A step by step series of examples that tell you have to get a development env running

Say what the step will be

```
Give the example
```

And repeat

```
until finished
```

End with an example of getting some data out of the system or using it for a little demo

## Running the tests

Explain how to run the automated tests for this system

### Break down into end to end tests

Explain what these tests test and why

```
Give an example
```

### And coding style tests

Explain what these tests test and why

```
Give an example
```

## Deployment

Add additional notes about how to deploy this on a live system

## Built With

* [Dropwizard](http://www.dropwizard.io/1.0.2/docs/) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [ROME](https://rometools.github.io/rome/) - Used to generate RSS Feeds

## Contributing

Please read [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/your/project/tags). 

## Authors

* **Billie Thompson** - *Initial work* - [PurpleBooth](https://github.com/PurpleBooth)

See also the list of [contributors](https://github.com/your/project/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Hat tip to anyone who's code was used
* Inspiration
* etc

0

