# DejaVu - TLE Scenario Visualizer

The DejaVu Visualzier (*tle.dejavu.eclipse*) is an Eclipse Plug-in for visualizing scenarios generated with TLE (Trace Link Evolver).

We provide the eclipse Plug-Ins and a sample datset from the Cassandra database.

Please see th [installation instructions](install.md) for furhter details on how to install and run DejaVu.


In order to run DejaVu, copy the "TLEScenarios" project into your workspace, open the DejaVu View, and select the scenario in the Drop-Down Menu



*TLE_Visualizer** infers and structures source code change scenarios in the form of evolutionary graphs. These change scenarios are initially detected by our Trace Linc Evolver (TLE) tool. 
Therefore, in order to visualize the change scenarios, TLE-Visualizer (i.e. DejaVu) originally requires the output files of TLE, which are the detected scenarios of changes across multiple versions of the source code.
To share this tool independent from TLE (as discussed in the paper), we included the TLE detected scenarios for Cassandra versions (as used in the study) as input.
This said, the Cassandra-relevant input files we included in this package include:
-- TLE final detected scenarios in directory ...\TLE_Visualizer\consoleOutput
-- TLE detected classes involved in the change in directory ...\TLE_Visualizer\addedClasses

The code takes the name of a file/class as an input in the console: (such as, CommitLogAllocator, IMergeIterator, Memory, Migration)
Note--do not include the .java file extension.
Finds all relevant change scenarios to that class (detected by TLE) among the available versions (here we included 6)
Merges all change scenarios relevant to that class
Generates a DOT graph called output.gv (GraphViz input file) to illustrated the result in the form of a single evolutionary graph.

  
