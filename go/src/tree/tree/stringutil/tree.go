package stringutil

import (
	"fmt"
	"path/filepath"
	"github.com/fatih/color"
)


func path2label(path string, fa FileAttribute) string {
	var  base = filepath.Base(path)

	if base == "." { return "." }

	switch fa {
		case Directory:  return base + "/"
		case Executable: return base + "*"
		default:         return base
	}
}

func preamble(bs [] bool) string {
   if len(bs) == 0 {
	   return ""
   } 
   
	var pre = ""
	for _, last := range bs[0:len(bs) - 1] {
		if last { 
			pre += "    " 
		} else { 
			pre += bar + "   " 
		}
	}

   if bs[len(bs) - 1] {
	   	pre += corner
   } else {
	   pre += tee
   }

	pre += pipe + pipe + space
	
	return pre 
}

func Emit(f string, bs []bool, fa FileAttribute) {
	var label = path2label(f, fa)
	var   pre = preamble(bs)
//	var  text = fmt.Sprintf("%-30s : %v", pre + label, bs)
	
	fmt.Printf(pre)
	switch fa {
		case None:       fmt.Println(label)
		case Directory:  color.Blue(label)
		case Executable: color.Red(label)
	}
}

func EmitComment(bs []bool, comment string) {
	var  pre = preamble(bs)
	var text = pre + comment
//	var text = fmt.Sprintf("%-30s : %v", pre + comment, bs)
	
	fmt.Println(text)
}


