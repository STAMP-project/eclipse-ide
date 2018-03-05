package com.dspot.menu.wizard;


import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.File;

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;


public class DspotWiz extends Wizard{
	
	protected DsPa1 one;
	protected DsPa2 two;
	private WizardConf wConf;
	
	// [0] Dspot jar path, [1] project path, [2] number of iterations i, [3] -t test class, [4] -a Method
	// [5] test criterion, [6] max Test Amplified
	private String[] parameters = new String[7];   // this will be the execution parameters

	
	public DspotWiz(WizardConf wConf) {
		super();
		setNeedsProgressMonitor(true);
		setHelpAvailable(true);
		this.wConf = wConf;
		if(System.getenv("MAVEN_HOME") == null) { // this is a warning if MAVEN_HOME is not set
			MessageDialog.openWarning(new Shell(), "Maven Home not set", "The enviroment variable MAVEN_HOME is not set, please set it in your computer or set it in the text in advanced options in page 2");
		}
	} // end of the constructor
	
	@Override
	public String getWindowTitle() { 
		return "Dspot Wizard";
	}
	
	
	@Override
	public void addPages() {
		one = new DsPa1(wConf);
		addPage(one);
		two = new DsPa2(wConf);
		addPage(two);
	}
	
	@Override
	public boolean performFinish() {
		String[] advParameters = two.getAdvparameters();
		if(System.getenv("MAVEN_HOME") == null && (advParameters[4] == null || advParameters[4] == "")) { // an error message if MAVEN_HOME is not set
			MessageDialog.openError(new Shell(),"Maven Home not set","Error the enviroment variable MAVEN_HOME is required, please set it in your computer or in the text in advanced options in page 2");
		}else {  // if MAVEN_HOME is set
		writeTheFile(one);    // writing the properties file
        MessageConsole MyConsole = createConsole("Dspot Console");  // obtaining the console of the eclipse application
        MyConsole.activate();  // activate the console of the eclipse application
        MessageConsoleStream out = MyConsole.newMessageStream();
        String[] MyS = two.getMyStrings(); // obtain the user information from page 2
        for(int i = 0; i < MyS.length; i++) {
        	parameters[i+2] = MyS[i];
        } // end of the for
        boolean verbose = two.getVerbose(); // more user information
        boolean clean = two.getClean();
        Job job = new DspotJob("Dspot Working",out,parameters,advParameters,verbose,clean,wConf); // execute Dspot in background
        job.schedule();  // background invocation of Dspot
		}
		return true;
	}
	
	
	private void writeTheFile(DsPa1 Pa) {   
		
		
		
		// this is the method to write the dspot.properties                        
    // it uses the information in page 1 and it is called by performFinish
		String[] Values = Pa.getTheProperties();
		String[] Keys = {"project","src","testSrc","javaVersion","outputDirectory","filter"};
		
		    
			String p = wConf.getProjectPath();
			parameters[1] = p;  // this will be set when perform finish will use it
			File file = new File(p+"/dspot.properties");
			try {
			file.createNewFile();
			BufferedWriter fw = new BufferedWriter(new FileWriter(file));
			fw.write("# Properties File #");
			fw.newLine();
			if(Values[4] == null || Values[4] == "") {   // use the default output directory
				Values[4] = "dspot-out/";
			}
			Values[4] = p+"/"+Values[4];
			if(Values[5] == null) { Values[5] = ""; } // if there is no filter
			for(int i = 0; i < Values.length; i++){
				fw.write(Keys[i]+"="+Values[i]);
				fw.newLine();
			}  // end of the for
			fw.close();
			} catch(IOException ioe) {ioe.printStackTrace();}		
	}    // end of writTheFile
	
	
	private MessageConsole createConsole(String name) {  // this will show the console information in the eclipse application console

	      ConsolePlugin plugin = ConsolePlugin.getDefault(); // obtaining the plugin console and it's manager
	      IConsoleManager conMan = plugin.getConsoleManager();
	       MessageConsole myConsole = new MessageConsole(name,null); // generating the new console
	      conMan.addConsoles(new IConsole[] {myConsole});
	      return myConsole;
		
	}  // end of the method create console
}
	

