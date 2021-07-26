# Installation Instructions


DejaVu requires an Eclipse for RCP developers version and Java8 with JavaFX to run properly
Additionally, the following plug-ins need to be installed:

- GraphViz
- GEF4 and GEF5 Framework 
- GraphViz (Dot)Viewer for eclipse 
- JavaFX Plug-in for Eclipse - e(fx)clipse


## Step-by-Step instructions:

### Prepare the Eclipse IDE
1. Download the latest Eclipse IDE for  for RCP and RAP Developerss (tested with version 06-2021) :https://www.eclipse.org/downloads/packages/release/2021-06/r/eclipse-ide-rcp-and-rap-developers
2.Install JavaFX support for Eclipse: e(fx)clipse can be directly installed from the eclipse update site (http://download.eclipse.org/releases/2020-03 when using Eclipse 4.15)
3. Install the GEF4 and GEF5 framework from the Eclipse Update Site:
    - GEF4: http://download.eclipse.org/tools/gef/gef4/updates/releases 
    - GEF5: http://download.eclipse.org/tools/gef/updates/releases


### Install GraphViz
4. Install GraphViz: https://graphviz.org/download/


### Import the DejaVu plugins and Prepare workspace
5. Import the tle.dejavu.eclipse plug-in into your eclipse workspace
6. Select a JRE with JavaFX installed (Window->Preferences->InstalledJ JREs)
