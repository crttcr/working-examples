// Example demonstrates explicitly defining external library
// dependencies with @Grab
//
// Taken from Groovy in Action, second edition page 39
//
// Usage:
// groovy grab
//
@Grab('commons-lang:commons-lang:2.4')
import org.apache.commons.lang.ClassUtils

class Outer
{
	class Inner
	{
	}
}

assert !ClassUtils.isInnerClass(Outer)
assert ClassUtils.isInnerClass(Outer.Inner)

println "Assertions pass.\nDone."
