import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * 	a simple demo binary to test the wordnet analyzer class
 * @author cschmidt
 *
 */

public class EntitySearch {

	/**
	 * @param args program arguments: [path to wordnet, , pointing to WordNet-X.X/dict subdirectory] [file with words to process] 
	 */
	public static void main(String[] args) {
		WordNetAnalyzer analyzer=new WordNetAnalyzer(args[0]);
		
		try (BufferedReader br = new BufferedReader(new FileReader(args[1]))) {
			String term;
			while ((term = br.readLine()) != null) {
				System.out.println("Term:"+term);		
				String synstring=analyzer.getSynonyms(term," ");
				System.out.println("Synonyms:"+synstring);
				String derivedstring=analyzer.getDerivationalWords(term," ");
				System.out.println("Derivational:"+derivedstring);		
				String hyperstring=analyzer.getHypernyms(term," ");
				System.out.println("Hypernyms:"+hyperstring);			       
			}
		} catch(FileNotFoundException e) {
			System.out.println("File not found!");
			System.exit(1);
		} catch (IOException e) {
			System.out.println("Something went wrong: IOException");
			System.exit(2);
		}
	}

}
