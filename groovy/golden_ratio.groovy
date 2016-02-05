// Simple example program to compute an approximation to the Golden Ratio
// using Fibbonacci numbers.
//
// Taken from Groovy in Action, second edition page 21
//
// Usage:
// groovy golden_ratio
//

List fibo = [1, 1]
List gold = [1, 2]

while (! isGolden( gold[-1] ))
{
	fibo.add( fibo[-2] + fibo[-1])
	gold.add( fibo[-1] / fibo[-2])

	"Gold approximation: " + gold[-1]
}

println "Found golden ratio with fibo(${fibo.size - 1}) as "
println fibo[-1] + " / " + fibo[-2] +  " = " + gold[-1]
println ""
println " " + "_" * 11 + "_" * (10 * gold[-1])
println "|" + "_" * 10 + "|" + "_" * (10 * gold[-1]) + "|"
println ""

def isGolden(candidate)
{
   def small = 1
   def big   = small * candidate
   
   return isCloseEnough( (small+big)/big, big/small)
}

def isCloseEnough(a, b)
{
   return (a-b).abs() < 1.0e-9
}
