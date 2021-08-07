# Installation Instructions


DejaVu requires an Eclipse for RCP developers version and Java8 with JavaFX to run properly
Additionally, the following plug-ins need to be installed:

- GraphViz
- GEF4 and GEF5 Framework 
- GraphViz (Dot)Viewer for eclipse 
- JavaFX Plug-in for Eclipse - e(fx)clipse


## Step-by-Step instructions:

### Prepare the Eclipse IDE

1. Download the latest Eclipse IDE for  for RCP and RAP Developers : https://www.eclipse.org/downloads/packages/

2.Install JavaFX support for Eclipse: e(fx)clipse can be directly installed from the eclipse update site

3. Install the GEF4 and GEF5 framework from the Eclipse Update Site:
    - GEF4 Update Site: http://download.eclipse.org/tools/gef/gef4/updates/releases 
    - GEF5 Update Site: http://download.eclipse.org/tools/gef/updates/releases

### Install GraphViz
4. Install GraphViz: https://graphviz.org/download/


### Import the DejaVu plugins and Prepare workspace
5. Import the tle.dejavu.eclipse plug-in into your eclipse workspace
6. Select a JRE including JavaFX (Window->Preferences->InstalledJ JREs)
7. Start an Eclipse instance containing the DejaVu plug-in (Project -> Run Configuration -> Eclipse Application)
8. Copy the _"TLEScenarios"_ project into the workspace and open the DejaVu view.
9. The provided scenarios can be selected from the drop-down menu




### TLE 

*TLE_Visualizer** infers and structures source code change scenarios in the form of evolutionary graphs. These change scenarios are initially detected by our Trace Linc Evolver (TLE) tool. 
Therefore, in order to visualize the change scenarios, TLE-Visualizer (i.e. DejaVu) originally requires the output files of TLE, which are the detected scenarios of changes across multiple versions of the source code.
To share this tool independent from TLE (as discussed in the paper), we included the TLE detected scenarios for Cassandra versions (as used in the study) as input.
This said, the Cassandra-relevant input files we included in this package include:
- TLE final detected scenarios in directory ...\TLE_Visualizer\consoleOutput
- TLE detected classes involved in the change in directory ...\TLE_Visualizer\addedClasses

The code takes the name of a file/class as an input in the console: (such as, CommitLogAllocator, IMergeIterator, Memory, Migration)
Note--do not include the .java file extension.
Finds all relevant change scenarios to that class (detected by TLE) among the available versions (here we included 6)
Merges all change scenarios relevant to that class
Generates a DOT graph called output.gv (GraphViz input file) to illustrated the result in the form of a single evolutionary graph.

For Additional details about TLE and the algorithm, we refer to our previous publications about TLE https://doi.org/10.1109/ICSME.2016.57, https://doi.org/10.1007/s10664-017-9561-x, https://doi.org/10.1109/RePa.2015.7407734
