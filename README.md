# AStar
"A*" algorithm implementation in java

The	A*	(A-Star)	search	algorithm	is	a	path-finding	algorithm	with	many	uses,	including	Artificial	
Intelligence	for	games.	The	search	builds	the	route	from	tile	to	tile	until	it	reaches	the	goal.

# Running the code
It is an eclipse project, import it from eclipse, the program expects a the graph file as command line argument.set the arguments in run configuration ex: graphs\input\test1.txt

### Map representation
Graph file uses the following representation.<br />

@ - Source node.  
X - Destination node.  
. - Flatlands (Edge with weight 1).<br />
* - Forests (Edge with weight 2).<br />
^ - Forests (Edge with weight 3).<br />
