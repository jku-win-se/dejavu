import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;


public class TLE_Visualizer {
	
	public static int node_number;
	public static int edge_id;
	
	
	public static void main(String[] args) throws FileNotFoundException, IOException{
		node_number = 0;
		edge_id = 0;
		
		//deletes any existing gv file, so we can create a new one for this run
		Path path_1 = Paths.get("output.gv");
		
		try {
		    Files.delete(path_1);
		} catch (NoSuchFileException x) {
		    System.err.format("%s: no such" + " file or directory%n", path_1);
		} catch (DirectoryNotEmptyException x) {
		    System.err.format("%s not empty%n", path_1);
		} catch (IOException x) {
		    // File permission problems are caught here.
		    System.err.println(x);
		}
		
		//creates the gv file that we will write to
		try {
		    // Create the empty file with default permissions, etc.
		    Files.createFile(path_1);
		} catch (FileAlreadyExistsException x) {
		    System.err.format("file named %s" +
		        " already exists%n", path_1);
		} catch (IOException x) {
		    // Some other sort of failure, such as permissions.
		    System.err.format("createFile error: %s%n", x);
		}
		
		//gets the class we need to visualize from the user
		Scanner in = new Scanner(System.in);
		
		String class_name = in.nextLine();
		
		//begins to search for the class within the files to find its origin
		System.out.println("Searching for : " + class_name);
		
		//boolean value which is only set to true if class is in original tracelinks
		boolean found = false;
		
		//orig_class will be used as the base of the tree we are trying to graph
		//set initially as the user inputted class name, however may be set to something else if class_name is an added class
		//if it is an added class, it will look for the original root class
		//orig_class_2 is initially empty, but if scenario 3 or scenario 9 is chosen, it should be set accordingly
		String orig_class = class_name;
		String orig_class_2 = new String();
		
		String found_file = new String();
		String file_suffix = new String();
		
		String scen_num = new String();
		String scen_num_1 = new String();
	
		//searches the original tracelinks and sets found equal to true if it is found
		BufferedReader br = new BufferedReader(new FileReader("original_tracelinks.txt"));
		try{
			String line = br.readLine();
			
			while (line != null) {
				String[] cSplit = line.split("\\:");
				if(cSplit[1].equals(class_name)){
					found = true;
				}
				line = br.readLine();
			}
		}
		finally{
			br.close();
		}
		
		if(found){
			System.out.println(class_name + " was found in the original tracelink file");
		}
		else{
			System.out.println(class_name + " was  not found in the original tracelink file, searching added classes..");
			
			//This section of code searches through all the added classes files in the added classes folder
			//Once the class is found, the full file name is stored in found_file, and the suffix such as "beta.txt" is stored in file_suffix
			
			File directory = new File("addedClasses");
			File[] myarray;
			boolean found_2 = false;
			myarray = new File[6];
			myarray = directory.listFiles();
			for(int j = 0; j < myarray.length; j++){
				File path = myarray[j];
				FileReader fr = new FileReader(path);
				BufferedReader br_2 = new BufferedReader(fr);
				try{
					String line = br_2.readLine();
					
					while (line != null) {
						line = line.replace(".java", "");
						
						if(line.equals(class_name)){
							System.out.println(class_name + " was found in " + myarray[j]);
							//to-do: stuff
							found_2 = true;
							
							found_file = myarray[j].toString();
							
							String[] temp = found_file.split("-");
							file_suffix = temp[1];
						}
							
						line = br_2.readLine();
					}
				}
				finally{
					br_2.close();
				}
			}
			
			if(!found_2){
				System.out.println(class_name + " was not found in added classes either, exiting program...");
				in.close();
				return;
			}
			in.close();
			
			
			
			//This section of codes searches through the appropriate console output file to get the original class(es)
			
			BufferedReader br_3 = new BufferedReader(new FileReader("consoleOutput/consoleOutput-" + file_suffix));
			try{
				String line = br_3.readLine();
				
				boolean start = false;
				
				while (line != null) {
					if(line.split(".java")[0].equals(class_name))
						start = true;
					if(start){
						if(line.length() > 4){
							String sub = line.substring(0, 4);
							if(sub.equals("test")){
								if(!line.split("test")[1].equals("1")){
									if(line.split("test")[1].equals("0") && !scen_num_1.equals("1")){
										System.out.println("No change scenarios found for this class.");
										return;
									}
									if(!line.split("test")[1].equals("7") && !line.split("test")[1].equals("8") && !line.split("test")[1].equals("9")){
										scen_num = line.split("test")[1];
										start = false;
									}
								}
								else{
									scen_num_1 = "1";
								}
							}	
						}
					}	
					line = br_3.readLine();
				}
			}
			finally{
				br_3.close();
			}
			
			if(!scen_num_1.equals(""))
				System.out.println("Scenario found for added class " + class_name + " is: " + scen_num_1);
			if(!scen_num.equals(""))
				System.out.println("Scenario found for added class " + class_name + " is: " + scen_num);
			
			BufferedReader br_4 = new BufferedReader(new FileReader("consoleOutput/consoleOutput-" + file_suffix));
			try{
				String line = br_4.readLine();
				
				boolean start = false;
				boolean start_2 = false;
				
				while (line != null) {
					if(line.split(".java")[0].equals(class_name))
						start = true;
					if(start){
						if(line.split(".java")[0].equals(class_name)){
							String[] temp = line.split("Scenario ");
							if(temp.length > 1){
							if(temp[1].equals(scen_num)){
								start_2 = true;
							}
							}
						}
					}	
					if(start_2){
						if(line.length() > 4){
							String temp = line.substring(line.length() - 4);
							if(temp.equals("java")){
								if(!scen_num.equals("3")){
									if(!scen_num.equals("7") && !scen_num.equals("8") && !scen_num.equals("9")){
										orig_class = line.replace(".java", "");
									}
								}
								else{
									orig_class = line.split(".java")[0];
									orig_class_2 = line.split(".java")[1];
								}
								start_2 = false;
							}
						}	
					}
					line = br_4.readLine();
				}
			}
			finally{
				br_4.close();
			}
		}
		
		System.out.println("Original class 1: " + orig_class);
		System.out.println("Original class 2: " + orig_class_2);
		
		//Calls an evolve process which will generate the tree we need to graph
		if(found){
			evolve(orig_class, orig_class_2,scen_num, scen_num_1);
		}
		else{
			evolve(orig_class, orig_class_2, scen_num, scen_num_1);
		}
		
	
	}

	public static void evolve(String og_1, String og_2, String scen_num, String scen_num_1) throws FileNotFoundException, IOException{
		String orig_class = og_1;
		String orig_class_2 = og_2;
		ArrayList<String> reqs = new ArrayList<String>();
		ArrayList<String> reqs_2 = new ArrayList<String>();
		ArrayList<String> offspring = new ArrayList<String>();
		ArrayList<String> reqs_3 = new ArrayList<String>();
		
		ArrayList<String> check_scens = new ArrayList<String>();
		String temp_og_2 = new String();
		String temp_offspring = new String();
		String temp_off_8_1 = new String();
		String temp_off_8_2 = new String();
		String temp_off_9 = new String();
		
		boolean start_8 = false;
		boolean start_9 = false;
		boolean start = false;
		
		String scen_num_out = "0";
		
		FileWriter fw = new FileWriter("output.gv");
		
		fw.write("digraph g { graph [ rankdir = \"LR\"];" + "\n" + "node [ fontsize = \"16\" shape = \"record\" ];");
		fw.write("\n" + "edge [ ];" + "\n");
		fw.close();
		
		//to do: run through console and store values in for reqs and offspring
		
		//if user input was found in original tracelinks file
		
			//start by getting the initial requirements linked to the class
			BufferedReader br = new BufferedReader(new FileReader("original_tracelinks.txt"));
			try{
				String line = br.readLine();
				
				while (line != null) {
					String[] cSplit = line.split("\\:");
					if(cSplit[1].equals(orig_class)){
						reqs.add(cSplit[0]);
					}
					line = br.readLine();
				}
			}
			finally{
				br.close();
			}
			
			boolean added = true;
			
			//find any change scenarios that happen for this class
			//run through all console output files in order
			File directory = new File("consoleOutput");
			File[] myarray;
			myarray = new File[6];
			myarray = directory.listFiles();
			for(int j = 0; j < myarray.length; j++){
				File path = myarray[j];
				FileReader fr = new FileReader(path);
				
				BufferedReader br_2 = new BufferedReader(fr);
				try{
					String line = br_2.readLine();
					
					while (line != null) {
						
						//check to see if you are running through added or deleted scenarios
						if(line.length() > 4){
							if(line.substring(0, 3).equals("***")){
								if(line.split(" CLASSES")[0].substring(31).equals("ADDED")){
									added = true;
								}
								else{
									added = false;
								}
							}
						}
						
						
						//you are looking through added scenarios
						if(added){
							
							if(line.split(".java ---").length > 1){
								temp_offspring = line.split(".java ---")[0];
							}
							
							if(line.length() > 4){
								if(line.substring(line.length() - 4).equals("java")){
									//Scenario 3s
									if(line.split(".java").length > 1){
										if(line.split(".java")[0].equals(orig_class)){
											temp_og_2 = line.split(".java")[1];
											check_scens.add("3");
										}
										else if(line.split(".java")[1].equals(orig_class)){
											temp_og_2 = line.split(".java")[1];
											check_scens.add("3");
										}
									}
									//other scenarios
									else{
										if(line.split(".java")[0].equals(orig_class)){
											line = br_2.readLine();
											line = br_2.readLine();
											
											if(line.substring(0, 4).equals("test")){
												if(line.substring(4).equals("6")){
													check_scens.add("6");
													scen_num_out = "6";
													offspring.add(temp_offspring);
												}
											}
											else{
												int scen = Integer.valueOf(line.split("Scenario ")[1]);
												scen--;
												check_scens.add(Integer.toString(scen));
											}
										}
									}
								}
							}
							
							if(!line.equals("test1")){
								if(line.length() > 4){
								if(line.substring(0, 4).equals("test")){
									for(int i = 0; i < check_scens.size(); i++){
										if(check_scens.get(i).equals(line.substring(4))){
											scen_num_out = check_scens.get(i);
											offspring.add(temp_offspring);
										}
									}
									check_scens.clear();
								}
								}
							}
							
							if(scen_num_out.equals("6")){
								reqs_3.addAll(reqs);
							}
							else if(scen_num_out.equals("4")){
								reqs_3.addAll(reqs);
							}
							else if(scen_num_out.equals("5")){
								reqs_3.addAll(reqs);
							}
							else if(scen_num_out.equals("2")){
								reqs_3.addAll(reqs);
							}
							else if(scen_num_out.equals("3")){
								reqs_3.addAll(reqs);
								orig_class_2 = temp_og_2;
								BufferedReader br_3 = new BufferedReader(new FileReader("original_tracelinks.txt"));
								try{
									String line_3 = br_3.readLine();
									
									while (line_3 != null) {
										String[] cSplit = line_3.split("\\:");
										if(cSplit[1].equals(orig_class_2)){
											reqs_2.add(cSplit[0]);
										}
										line_3 = br_3.readLine();
									}
								}
								finally{
									br_3.close();
								}
								reqs_3.addAll(reqs_2);
							}
							
							if(!scen_num_out.equals("0")){
								
								graph(orig_class, orig_class_2, scen_num_out, scen_num_1, reqs, reqs_2, offspring, reqs_3);
								System.out.println("Scenario is " + scen_num_out);
								System.out.println(offspring);
								
								reqs_3.clear();
								offspring.clear();
								
								scen_num_out = "0";
							}
						}
						//you are looking through deleted scenarios
						else{
							if(line.split(".java ---").length > 1){
								if(line.split(".java ---")[0].equals(orig_class)){
									start = true;
									if(line.split("Scenario ")[1].equals("8")){
										start_8 = true;
									}
									else if(line.split("Scenario ")[1].equals("9")){
										start_8 = false;
										start_9 = true;
									}
								}
							}
							
							if(start_8){
								if(line.length() > 4){
									if(line.substring(line.length() - 4).equals("java")){
										temp_off_8_1 = line.split(".java")[0];
										temp_off_8_2 = line.split(".java")[1];
										start_8 = false;
									}
								}
							}
							
							if(start_9){
								if(line.length() > 4){
									if(line.substring(line.length() - 4).equals("java")){
										temp_off_9 = line.split(".java")[0];
										start_9 = false;
									}
								}
							}
							
							if(start){
								if(line.length() > 4){
									if(line.substring(0, 4).equals("test")){
										start = false;
										if(line.split("test")[1].equals("7")){
											scen_num_out = "7";
										}
										else if(line.split("test")[1].equals("8")){
											scen_num_out = "8";
											offspring.add(temp_off_8_1);
											offspring.add(temp_off_8_2);
										}
										else if(line.split("test")[1].equals("9")){
											scen_num_out = "9";
											offspring.add(temp_off_9);
										}
									}
								}
							}
							
							if(scen_num_out.equals("8")){
								reqs_3.addAll(reqs);
							}
							else if(scen_num_out.equals("9")){
								reqs_3.addAll(reqs);
							}
							
							if(!scen_num_out.equals("0")){
								
								graph(orig_class, orig_class_2, scen_num_out, scen_num_1, reqs, reqs_2, offspring, reqs_3);
								System.out.println("Scenario is " + scen_num_out);
								System.out.println(offspring);
								
								reqs_3.clear();
								offspring.clear();
								
								scen_num_out = "0";
							}
						}
						
						
						line = br_2.readLine();
					}
				}
				finally{
					br_2.close();
				}
				
				
				
				
				
				
				
			}
			
		
		
		
		FileWriter fw_2 = new FileWriter("output.gv", true);
		fw_2.write("\n" + "}");
		graph(orig_class, orig_class_2, scen_num_out, scen_num_1, reqs, reqs_2, offspring, reqs_3);
		fw_2.close();
		//to do: run through offspring and call this function recursively with offspring as orig_class
	}
	
	public static void graph(String og_1, String og_2, String scen_num, String scen_num_1, ArrayList<String> reqs_1, ArrayList<String> reqs_2, ArrayList<String> offspring, ArrayList<String> reqs_3) throws FileNotFoundException, IOException{
		
		FileWriter fw = new FileWriter("output.gv", true);
		
		String req_1 = new String();
		for(int i = 0; i < reqs_1.size(); i++){
			if(i == (reqs_1.size() - 1))
				req_1 = req_1 + reqs_1.get(i);
			else
				req_1 = req_1 + reqs_1.get(i) + ", ";
		}
		
		String req_2 = new String();
		for(int i = 0; i < reqs_2.size(); i++){
			if(i == (reqs_2.size() - 1))
				req_2 = req_2 + reqs_2.get(i);
			else
				req_2 = req_1 + reqs_2.get(i) + ", ";
		}
		
		String req_3 = new String();
		for(int i = 0; i < reqs_3.size(); i++){
			if(i == (reqs_3.size() - 1))
				req_3 = req_3 + reqs_3.get(i);
			else
				req_3 = req_3 + reqs_3.get(i) + ", ";
		}
		
		if(scen_num.equals("2")){
			fw.write("\"node" + node_number + "\" [" + "\n" + "label = \"<f0> " + og_1 + " |<f1> " + req_1 + "\"" + "\n" + "shape = \"record\"" + "\n" + "color = \"red\"" + "];" + "\n");
			node_number++;
			fw.write("\"node" + node_number + "\" [" + "\n" + "label = \"<f0> " + offspring.get(0) + " |<f1> " + req_3 + "\"" + "\n" + "shape = \"record\"" + "\n" + "color = \"green\"" + "];" + "\n");
			node_number++;
			fw.write("\"node" + (node_number - 2) + "\":f0 -> \"node" + (node_number - 1) + "\":f0 [" + "\n" + "id = " + edge_id + "\n" + "];" + "\n");
			edge_id++;
		}
		else if(scen_num.equals("3")){
			fw.write("\"node" + node_number + "\" [" + "\n" + "label = \"<f0> " + og_1 + " |<f1> " + req_1 + "\"" + "\n" + "shape = \"record\"" + "\n" + "color = \"red\"" + "];" + "\n");
			node_number++;
			fw.write("\"node" + node_number + "\" [" + "\n" + "label = \"<f0> " + og_2 + " |<f1> " + req_2 + "\"" + "\n" + "shape = \"record\"" + "\n" + "color = \"red\"" + "];" + "\n");
			node_number++;
			fw.write("\"node" + node_number + "\" [" + "\n" + "label = \"<f0> " + offspring.get(0) + " |<f1> " + req_3 + "\"" + "\n" + "shape = \"record\"" + "\n" + "color = \"green\"" + "];" + "\n");
			node_number++;
			fw.write("\"node" + (node_number - 3) + "\":f0 -> \"node" + (node_number - 1) + "\":f0 [" + "\n" + "id = " + edge_id + "\n" + "];" + "\n");
			edge_id++;
			fw.write("\"node" + (node_number - 2) + "\":f0 -> \"node" + (node_number - 1) + "\":f0 [" + "\n" + "id = " + edge_id + "\n" + "];" + "\n");
			edge_id++;
		}
		else if(scen_num.equals("4")){
			fw.write("\"node" + node_number + "\" [" + "\n" + "label = \"<f0> " + og_1 + " |<f1> " + req_1 + "\"" + "\n" + "shape = \"record\"" + "\n" + "];" + "\n");
			node_number++;
			fw.write("\"node" + node_number + "\" [" + "\n" + "label = \"<f0> " + offspring.get(0) + " |<f1> " + req_3 + "\"" + "\n" + "shape = \"record\"" + "\n" + "color = \"green\"" + "];" + "\n");
			node_number++;
			fw.write("\"node" + node_number + "\" [" + "\n" + "label = \"<f0> " + og_1 + " |<f1> " + req_1 + "\"" + "\n" + "shape = \"record\"" + "\n" + "];" + "\n");
			node_number++;
			fw.write("\"node" + (node_number - 3) + "\":f0 -> \"node" + (node_number - 2) + "\":f0 [" + "\n" + "id = " + edge_id + "\n" + "];" + "\n");
			edge_id++;
			fw.write("\"node" + (node_number - 3) + "\":f0 -> \"node" + (node_number - 1) + "\":f0 [" + "\n" + "id = " + edge_id + "\n" + "];" + "\n");
			edge_id++;
		}
		else if(scen_num.equals("5")){
			fw.write("\"node" + node_number + "\" [" + "\n" + "label = \"<f0> " + og_1 + " |<f1> " + req_1 + "\"" + "\n" + "shape = \"record\"" + "\n" + "];" + "\n");
			node_number++;
			fw.write("\"node" + node_number + "\" [" + "\n" + "label = \"<f0> " + offspring.get(0) + " |<f1> " + req_3 + "\"" + "\n" + "shape = \"record\"" + "\n" + "color = \"green\"" + "];" + "\n");
			node_number++;
			fw.write("\"node" + node_number + "\" [" + "\n" + "label = \"<f0> " + og_1 + " |<f1> " + req_1 + "\"" + "\n" + "shape = \"record\"" + "\n" + "];" + "\n");
			node_number++;
			fw.write("\"node" + (node_number - 3) + "\":f0 -> \"node" + (node_number - 2) + "\":f0 [" + "\n" + "id = " + edge_id + "\n" + "];" + "\n");
			edge_id++;
			fw.write("\"node" + (node_number - 3) + "\":f0 -> \"node" + (node_number - 1) + "\":f0 [" + "\n" + "id = " + edge_id + "\n" + "];" + "\n");
			edge_id++;
		}
		else if(scen_num.equals("6")){
			fw.write("\"node" + node_number + "\" [" + "\n" + "label = \"<f0> " + og_1 + " |<f1> " + req_1 + "\"" + "\n" + "shape = \"record\"" + "\n" + "];" + "\n");
			node_number++;
			fw.write("\"node" + node_number + "\" [" + "\n" + "label = \"<f0> " + offspring.get(0) + " |<f1> " + req_3 + "\"" + "\n" + "shape = \"record\"" + "\n" + "color = \"green\"" + "];" + "\n");
			node_number++;
			fw.write("\"node" + node_number + "\" [" + "\n" + "label = \"<f0> " + og_1 + " |<f1> " + req_1 + "\"" + "\n" + "shape = \"record\"" + "\n" + "];" + "\n");
			node_number++;
			fw.write("\"node" + (node_number - 3) + "\":f0 -> \"node" + (node_number - 2) + "\":f0 [" + "\n" + "id = " + edge_id + "\n" + "];" + "\n");
			edge_id++;
			fw.write("\"node" + (node_number - 3) + "\":f0 -> \"node" + (node_number - 1) + "\":f0 [" + "\n" + "id = " + edge_id + "\n" + "];" + "\n");
			edge_id++;
		}
		else if(scen_num.equals("7")){
			fw.write("\"node" + node_number + "\" [" + "\n" + "label = \"<f0> " + og_1 + " |<f1> " + req_1 + "\"" + "\n" + "shape = \"record\"" + "\n" + "color = \"red\"" + "\n" + "];" + "\n");
			node_number++;
		}
		else if(scen_num.equals("8")){
			fw.write("\"node" + node_number + "\" [" + "\n" + "label = \"<f0> " + og_1 + " |<f1> " + req_1 + "\"" + "\n" + "shape = \"record\"" + "\n" + "color = \"red\"" + "];" + "\n");
			node_number++;
			fw.write("\"node" + node_number + "\" [" + "\n" + "label = \"<f0> " + offspring.get(0) + " |<f1> " + req_3 + "\"" + "\n" + "shape = \"record\"" + "\n" + "color = \"green\"" + "];" + "\n");
			node_number++;
			fw.write("\"node" + node_number + "\" [" + "\n" + "label = \"<f0> " + offspring.get(1) + " |<f1> " + req_3 + "\"" + "\n" + "shape = \"record\"" + "\n" + "color = \"green\"" + "];" + "\n");
			node_number++;
			fw.write("\"node" + (node_number - 3) + "\":f0 -> \"node" + (node_number - 2) + "\":f0 [" + "\n" + "id = " + edge_id + "\n" + "];" + "\n");
			edge_id++;
			fw.write("\"node" + (node_number - 3) + "\":f0 -> \"node" + (node_number - 1) + "\":f0 [" + "\n" + "id = " + edge_id + "\n" + "];" + "\n");
			edge_id++;
		}
		else if(scen_num.equals("9")){
			fw.write("\"node" + node_number + "\" [" + "\n" + "label = \"<f0> " + og_1 + " |<f1> " + req_1 + "\"" + "\n" + "shape = \"record\"" + "\n" + "color = \"red\"" + "];" + "\n");
			node_number++;
			fw.write("\"node" + node_number + "\" [" + "\n" + "label = \"<f0> " + og_2 + " |<f1> " + req_2 + "\"" + "\n" + "shape = \"record\"" + "\n" + "color = \"red\"" + "];" + "\n");
			node_number++;
			fw.write("\"node" + node_number + "\" [" + "\n" + "label = \"<f0> " + offspring.get(0) + " |<f1> " + req_3 + "\"" + "\n" + "shape = \"record\"" + "\n" + "color = \"green\"" + "];" + "\n");
			node_number++;
			fw.write("\"node" + (node_number - 3) + "\":f0 -> \"node" + (node_number - 1) + "\":f0 [" + "\n" + "id = " + edge_id + "\n" + "];" + "\n");
			edge_id++;
			fw.write("\"node" + (node_number - 2) + "\":f0 -> \"node" + (node_number - 1) + "\":f0 [" + "\n" + "id = " + edge_id + "\n" + "];" + "\n");
			edge_id++;
		}
		
		fw.close();
	}
}
