package main

import (
	"bytes"
	"fmt"
	"testing"
)

// TestEcho tests "echo" the single command in this main package.
//
// Run the test by typing 
//
// $ go test
//
// from this directory.
//
// Example from "The Go Programming Language"
// Alan A. A. Donovan
// Brian W. Kernigan
// 1st edition pp 308 - 310
//

func TestEcho(t *testing.T) {

	var tests = []struct {
		newline      bool
		sep        string
		args     []string
		want       string
	}{
		{true,  "", []string{}, "\n"},
		{false, "", []string{}, ""},
		{true , "\t", []string{"one", "two", "four"}, "one\ttwo\tfour\n"},
		{false, "\t", []string{"one", "two", "four"}, "one\ttwo\tfour"},
		{true , ",", []string{"101", "Apple", "10,000"}, "101,Apple,10,000\n"},
		{false, ":", []string{"dave", "davis", "12/01/1984"}, "dave:davis:12/01/1984"},
	}

	for _, test := range tests {
		desc := fmt.Sprintf("echo(%v, %q, %q)", test.newline, test.sep, test.args)

		out = new (bytes.Buffer)
		if err := echo(test.newline, test.sep, test.args); err != nil {
			t.Errorf("%s failed: %v\n", desc, err)
		}

		got := out.(*bytes.Buffer).String()
		if got != test.want {
			t.Errorf("%s = %q, want: %q\n", desc, got, test.want)
		}
	}
}