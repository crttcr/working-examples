package stringutil

const (
	bar    = "\u2502" // "\u2503"
	pipe   = "\u2500" // "\u2501"
	tee    = "\u251c" // "\u2523"
	corner = "\u2514" // "\u2517"
	space  = " "
	empty  = bar + "    "
)

type FileAttribute int
const (
	None        FileAttribute = iota
	Directory
	Executable
)