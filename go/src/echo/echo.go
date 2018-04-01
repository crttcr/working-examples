// Echo prints it command line arguments
//
package main

import (
	"flag"
	"fmt"
	"io"
	"os"
	"strings"
)

var (
	n = flag.Bool("n", false, "Omit trailing newline")
	s = flag.String("s", " ", "separator")
)

var out io.Writer = os.Stdout // Explicit type force interface, not impl. Modified when testing

func main() {
	flag.Parse()
	var args = flag.Args()

	if err := echo(!*n, *s, args); err != nil {
		fmt.Fprintf(os.Stderr, "echo: %v\n", err)
		os.Exit(1)
	}
}

func echo(newline bool, sep string, args[]string) error {
	var text = strings.Join(args, sep)

	fmt.Fprintf(out, "%s", text)

	if (newline) {
		fmt.Fprintln(out)
	}

	return nil
}
