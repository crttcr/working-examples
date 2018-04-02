package treeutil

type Config struct {
	DepthLimit      int
	Compact        bool
	ShowDotFiles   bool
	ShowConfig     bool
}

type operation int

const (
	display_op operation = iota   // display the entry
	recurse_op                    // recurse into directory
	compact_op                    // aggregate content into summary
	summary_op                    // display the summary of compacted entries
)

