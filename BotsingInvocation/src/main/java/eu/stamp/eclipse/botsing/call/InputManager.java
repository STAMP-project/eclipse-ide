package eu.stamp.eclipse.botsing.call;

public class InputManager {
	
	private String[] command;
	
	private String outputFilePath;
	
	private final static String COMAND_SEPARATOR = "%";
	
	private final static String OUT_SEPARATOR = "%%%";
	
	public InputManager() {}
	
	public InputManager(String[] command) {
		this(command,null);
	}
	
	public InputManager(String[] command,String outputFilePath) {
		this.command = command;
		this.outputFilePath = outputFilePath;
	}
	
	public void loadFromString(String loadString) {
		
		if(loadString.contains(OUT_SEPARATOR)) {
			String[] strings = loadString.split(OUT_SEPARATOR);
			outputFilePath = strings[1];
			command = strings[0].split(COMAND_SEPARATOR);
		} else {
			outputFilePath = null;
			command = loadString.split(COMAND_SEPARATOR);
		}
	}
	
	public String[] getCommand() { return command; }
	
	public String getoutputFilePath() { return outputFilePath; }
	
	public boolean outputFileSet() {
		if(outputFilePath == null) return false;
		return !outputFilePath.isEmpty();
	}
	
	public String serializeToString() {
		
		if(command.length < 1) return null;
		String result = command[0];
		for(int i = 1; i < command.length; i++)
			result += COMAND_SEPARATOR + command[i];
		if(outputFilePath != null)if(!outputFilePath.isEmpty())
			result += OUT_SEPARATOR + outputFilePath;
		return result;
	}
}