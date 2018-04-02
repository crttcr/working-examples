// Package treeutil contains utility functions for working with strings.
//
package treeutil

import (
	"fmt"
	"github.com/crttcr/stringutil"
)

var directories = 0
var executables = 0
var files       = 0

// Walk walks the directory structures rooted at the paths argument
//
func Walk(paths []string, c *Config) {

	// fmt.Printf("Walking [%v] with [%v]\n\n", paths, *c)
	
	//var max = c.maxDepth()
	// Use the configured value to create the slice.
	
	// NOTE: bs is a boolean slice that indicates whether or not each level of the directory
	// tree the last entry at that level. This is used to determine whether or not intermediate
	// vertical lines are produced at each level.
	//

	for _, p := range(paths) {
		is := infoSeqForPath(p)
		var bs = make([]bool, 0, 10)
		
		if c.ShowConfig { 
			fmt.Printf("Path = %s, Length = %d, bs = %v\n", p, len(is), bs) 
		}

		walk(p, c, bs)
	}

	displayTallies()
}


func walk(path string, c* Config, bs []bool) {
	stringutil.Emit(path, bs, stringutil.Directory)
	is := infoSeqForPath(path)
	is = filter(is, c)
	bs = append(bs, len(bs) > 1)

	for i, f := range is {
		var    n = f.Name()
		var   fa = classify(f)
		bs[len(bs) - 1] = i == len(is) - 1

		if f.IsDir() {
			directories++
			if canRecurse(f, bs, c.DepthLimit)	{
				var  p = path + "/" + f.Name()
				walk(p, c, bs)
			} else {
				var comment = fmt.Sprintf("%s/ [CONTENT EXCLUDED. DepthLimit = %d]", n, c.DepthLimit)
				stringutil.EmitComment(bs, comment)
			}
		} else {
			files++
			if fa == stringutil.Executable { executables++ }
			stringutil.Emit(n, bs, fa)
		}
	}
}



func displayTallies() {
	var d = "directories"
	var f = "files"
	var e = "executable"

	if directories == 1 { d = "directory"  }
	if files       == 1 { f = "file"       }
	if executables == 1 { e = "executable" }
	
	var t = fmt.Sprintf("\n%%d %s, %%d %s, %%d %s\n", d, f, e)
	fmt.Printf(t, directories, files, executables)
}



