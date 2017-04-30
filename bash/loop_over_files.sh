## This is a reminder of how to loop over files using
## the bash shell to perform operations on a set of files
##
$ for f in *.c; do echo $f; cp $f $f.backup; done
