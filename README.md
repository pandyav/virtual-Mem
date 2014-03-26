virtual-Mem
===========

memory manager simulator for the OS. 

Applies the First-Fit and Best-Fit memory allocation algorithms 
to both Fixed and Dynamic memory partitions.

Total algorithms implemented: 

-fixed-first

-fixed-best

-dynamic-first

-dynamic-best

If you dont want to run it manually through an IDE, easier way is through the command line.

open cmd and browse to the bin folder. type the following command: java MemoryDriver. Make sure the program is compiled or built before doing this. For building through cmd, go to the src folder, type the following command: javac -d ../bin *.java

*command java is for running a compiled java program

*command javac is for compiling a java program

*MemoryDriver is the class with main function

How the application works?

Total memory size is set as default to 200k

Total of 4 partitions are set and to default size of partition 1:100k, partition 2:25k, partititon 3:25k, partition 4:50k.


-when started, choose the partition type (fixed or dynamic)

-then choose the allocation scheme: first or best

-then type an option (just 'help' for the help menu which will show all options)

-obviously you will want to add jobs, so type 'a' and start adding jobs

-'m' will show you the actual main memory in a table form

-'dealloc' will deallocate jobs from memory

-to try new algorithm, just 'restart'
