# Args - Simple, overachieving option/argument processing

This is yet another library for processing program arguments. Inspired by Robert C. Martin's 
[Clean Code](http://www.amazon.com/Clean-Code-Handbook-Software-Craftmanship/dp/0132350882"),
chapter 14, this library keeps the simple **one-character + optional modifier** specification string for cases
where advanced capabilities are overkill. Additionally, by providing a more detailed specification,
it's possible to provide descriptions, default values, environment variable lookup, and validatation
to ensure programs are started in a consistent state.


## Getting Started

To use this code, simply add the sources or a jar file to your project, describe the program 
options, and pass the program arguments to the Args constructor. If the constructor does not
throw an ArgsException error, then the instance can be used to access program arguments by
name. Here is the simple usage example that does not use advanced the features.

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

## Running the tests

The tests that come with the library source can be run vi [JUnit](http://http://junit.org/junit4)

## Built With

* [Java 8](http://www.oracle.com/technetwork/java/javase/overview/java8-2100321.html) 
* [Lombok](https://projectlombok.org/) - Taking some of the pain out of Java

## Contributing


## Authors

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Robert C. Martin for the Clean Code and chapter 14 in particular
