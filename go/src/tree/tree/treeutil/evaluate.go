package treeutil

import (
	"fmt"
	"io/ioutil"
	"log"
	"os"
	"strings"

	"github.com/crttcr/stringutil"
)


var mask = uint32(64 + 8 + 1)

func classify(f os.FileInfo) stringutil.FileAttribute {

	if f.IsDir() { return stringutil.Directory }

	var mode = f.Mode()
	var bits = uint32(mode)
	
	if bits & mask != 0 { return stringutil.Executable }

	return stringutil.None
}

// filter removes all files and directories that start with "." 
// from the output, unless the program's configuration specifies
// that it include show dot files in the output.
//
func filter(fs []os.FileInfo, c *Config) []os.FileInfo {
	if c.ShowDotFiles {
		return fs		
	}

	i := 0
	for _, fi := range fs {
		var pos = strings.Index(fi.Name(), ".")
		
		if pos != 0 {
			fs[i] = fi
			i++	
		}
	}
	
	return fs[:i]
}

func evaluateInfoSequence(is []os.FileInfo) []operation {
	var count = len(is)
	var rv    = make([]operation, count)

	return rv
}

// canRecurse determine whether or not the current entry represents a directory
// that is not too deep in the tree for exploration.
//
func canRecurse(f os.FileInfo, bs []bool, limit int) bool {
	if ! f.IsDir() { return false }
	if limit < 1   { return  true }

	return len(bs) < limit
}

func infoSeqForPath(p string) []os.FileInfo {
	files, err := ioutil.ReadDir(p)
	if err != nil {
		var msg = fmt.Sprintf("Error opening directory: %s\n", p)
		log.Fatal(msg)
	}

	return files
}