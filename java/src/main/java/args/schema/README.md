# Args - Schema

Schema is the element that contains the definition of what options and arguments are expeced
for a program and what additional validations are desired.

There are two approaches to defining an option schema, the short form and the long form.

## Short Form Definition
The short form is simple and unobtrusive, but is also limited in that validations and
default values are not available. If you simply want one charcter arg definitions, then
all you need to define them is a single String such as show below.

```
String def = "~h*,p#,v,t";
Schema schema = new SchemaBuilder("MyProgram").build(defs);
```

Note that the Args contructor will handle the simple construction for you so
all that is required of your program with this approach is to pass in the definition
string like this:

```
public void main(String[] args) 
{
   Args arg = new Args("~h*,p#,v,x", args);
   ...
}
```

Option definition and argument processing can generate errors, so the above code must
handle ArgsException. 


The schema show above defines four program options
* -h a string
* -p an integer
* -v a boolean
* -x another boolean

So calling the above main method from java like this `java main -v -h localhost -p 8080`
will result in the "arg" variable being bound with the option values provided on the command line.

In addition, it uses the `FAIL_SLOW` error strategy, as indicated by the leading '~' character.
For this strategy, all exceptions encountered while processing the schema are combined and 
reported at the end. The `FAIL_FAST` strategy ('!') stops processing immediately and returns the
error, while the `WARN_AND_IGNORE` ('&') stategy keeps processing and treats the offending 
option specification as if it does not exist.

## Long Form Definition
The long form schema definition provides more control at the cost of more detailed specification.
This specification format is a sequence of properties presented to the schema builder 
defining the option characteristics and semantics. The propery keys are structured with
the property name prepended with the specific property. So for example

```
verbose.name=verbose
verbose.description=When set, this option increases the program's output to STDOUT
verbose.type=BOOLEAN
verbose.default=false
```
