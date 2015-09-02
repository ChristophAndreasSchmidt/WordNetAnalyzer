/** a wordnet analyzer providing synonyms, hypernyms and derivational words
 * 
 * @author cschmidt
 *
 */

import java.util.Collections;
import java.util.HashSet;
import edu.smu.tspell.wordnet.NounSynset;
import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.SynsetType;
import edu.smu.tspell.wordnet.WordNetDatabase;
import edu.smu.tspell.wordnet.WordSense;

public class WordNetAnalyzer {
	
	/** creates wordnet analyzer
	 * 
	 * @param wordnetpath path to wordnet, pointing to WordNet-X.X/dict subdirectory
	 */
	WordNetAnalyzer(String wordnetpath) {
		 System.setProperty("wordnet.database.dir", wordnetpath);
		 wordNetDatabase = WordNetDatabase.getFileInstance();		 
	}
	
	/** retrieves synonyms from wordnet
	 * @param word word for which synonyms are retrieved
	 * @param delimiter character to delimit synonyms in return value
	 * @return string containing synonyms separated by delimiter */
	public String getSynonyms(String word, String delimiter) {
		HashSet<String> synStringSet = new HashSet<String>();
		Synset[] synsets=wordNetDatabase.getSynsets(word); 
		for (Synset synset:synsets) {
			Collections.addAll(synStringSet, synset.getWordForms());
		}
		return String.join(delimiter, synStringSet);
	}
	
	/** retrieves hypernyms from wordnet
	 * 
	 * @param word word for which hypernyms are retrieved (nouns only)
	 * @param delimiter character to delimit hypernyms in return value
	 * @return string containing hypernyms, separated by delimiter
	 */
	public String getHypernyms(String word,String delimiter) {
		HashSet<String> hypStringSet = new HashSet<String>();
		Synset[] synsets=wordNetDatabase.getSynsets(word,SynsetType.NOUN); 
		for (Synset synset: synsets) {
			for(NounSynset hyp:((NounSynset)synset).getHypernyms()) {
				Collections.addAll(hypStringSet,hyp.getWordForms());
			}
		}
		return String.join(delimiter, hypStringSet);
	}

	/** retrieves synonyms including derivational word forms
	 * 
	 * @param word word for which synonyms and relational word forms are retrieved
	 * @param delimiter character to delimit synonyms and derivational word forms
	 * @return string containing synonyms and derivational words, separated by delimiter
	 */
	public String getDerivationalWords(String word,String delimiter) {
		HashSet<String> derivStringSet = new HashSet<String>();
		Synset[] synsets=wordNetDatabase.getSynsets(word); 
		for (Synset synset:synsets) {
			for(String s:synset.getWordForms()) {
				for(WordSense w:synset.getDerivationallyRelatedForms(s))
				derivStringSet.add(w.getWordForm());
			}
		}
		return String.join(delimiter, derivStringSet);		
	}
	
	private WordNetDatabase wordNetDatabase;
}
