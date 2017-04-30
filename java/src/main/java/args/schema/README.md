# Args - Schema

Schema is the element that contains the definition of what options and arguments are expeced
for a program and what additional validations are desired.

There are two approaches to defining an option schema, the short form and the long form.
The short form is simple and unobtrusive, but is also limited in that validations and
default values are not available. If you simply want one charcter arg definitions, then
all you need to define them is a single String such as described below.

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
