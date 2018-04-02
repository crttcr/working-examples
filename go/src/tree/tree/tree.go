package main

import (
	"flag"
	"fmt"
	"github.com/crttcr/treeutil"
)

func main() {

	var c treeutil.Config
	var paths = processArguments(&c)

	treeutil.Walk(paths, &c)
	if c.ShowConfig {
		displayConfig(paths, &c)
	}
}

func processArguments(c *treeutil.Config) []string {

	p := flag.String("path", ".", "The path to display")
	flag.IntVar(&c.DepthLimit, "depth", -1, "How many levels deep to display, -1 for unlimited")
	flag.BoolVar(&c.Compact, "compact", true, "Whether or not to produce compact results by eliding long entries (default true)")
	flag.BoolVar(&c.ShowDotFiles, "dots", false, "Show hidden files (default false)")
	flag.BoolVar(&c.ShowConfig, "config", false, "Show program configuration (default false)")
	flag.Parse()

	var args = flag.Args()

	// If there are no positional arguments, or the user has provided a -path option
	// then append the path option to the returned arguments. We also swap the -path
	// value to be the first item in the returned array.
	//
	if (len(args) == 0 || *p != ".") {
		args = append(args, *p)
		args[0], args[len(args) - 1] = args[len(args) - 1], args[0]
	}

	return args
}

func displayConfig(p []string, c *treeutil.Config) {
	fmt.Printf("Paths          : %s\n", p)
	fmt.Printf("DepthLimit     : %d\n", c.DepthLimit)
	fmt.Printf("Compact        : %t\n", c.Compact)
	fmt.Printf("Show Dot Files : %t\n", c.ShowDotFiles)
	fmt.Printf("Show Config    : %t\n", c.ShowConfig)
}