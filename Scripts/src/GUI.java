import java.io.File;

public class GUI {

	public static void main(String[] args) {
		File folder = new File("C:/Users/alexa_000/Music");
		iCantThinkOfAGoodNameForThisShittyShit(folder);
	}
	
	public static void iCantThinkOfAGoodNameForThisShittyShit(File folder){
		for(File f : folder.listFiles()){
			if(f.isDirectory()){
				iCantThinkOfAGoodNameForThisShittyShit(f);
			}else{
				System.out.println(f.getParent() + "/" + ((int) (Math.random()*50000)) + f.getName());
				f.renameTo(new File(f.getParent() + "/" + ((int) (Math.random()*50000)) + f.getName()));
			}
		}
	}
}