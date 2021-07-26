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

