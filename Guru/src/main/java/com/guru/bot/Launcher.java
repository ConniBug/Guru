package com.guru.bot;

/**
 * This is the main class that gets the bot up and running.
 * @author syntex
 * @version 1.0.0
 */
public class Launcher {
	
	//private static final String version = "0.0.5-SNAPSHOT";
	
	public static void main(String[] args) {
		//start the bot.
		
		//final String REPO = "https://github.com/F12-Syntex/Guru";
		
		/*
		
		String version = Network.readURL("https://raw.githubusercontent.com/F12-Syntex/Guru/master/pom.xml")
				.split("<version>")[1]
				.split("</version>")[0];
		
		try {
		
			MavenXpp3Reader reader = new MavenXpp3Reader();
	        Model model = reader.read(new FileReader("pom.xml"));
			
	        if(!version.equals(model.getVersion())) {
	        	System.out.println(version + " -> " + model.getVersion() + ": Version mis-match, pulling Guru repository");
	        }
	       
			Logger.INFO("Downloading repository " + REPO);
			
			File path = new File("C:\\Users\\synte\\OneDrive - University of Kent\\Desktop\\building");
			
			if(path.exists()) {
				Logger.INFO("Clearing local repository cashe...");
				FileUtils.deleteFolder(path);
			}
			
			
			Git.cloneRepository()
			.setURI(REPO)
			.setDirectory(path)
			.call();
			
			Logger.INFO("Downloaded repository, compiling bot...");
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		*/
		
		Guru instance = new Guru();
		instance.start();		
	}
	
	
}
