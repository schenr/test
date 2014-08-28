//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.StringReader;
//import java.util.ArrayList;
//import java.util.List;
//
//import opennlp.tools.chunker.ChunkerME;
//import opennlp.tools.chunker.ChunkerModel;
//import opennlp.tools.cmdline.PerformanceMonitor;
//import opennlp.tools.cmdline.postag.POSModelLoader;
//import opennlp.tools.postag.POSModel;
//import opennlp.tools.postag.POSSample;
//import opennlp.tools.postag.POSTaggerME;
//import opennlp.tools.tokenize.WhitespaceTokenizer;
//import opennlp.tools.util.InvalidFormatException;
//import opennlp.tools.util.ObjectStream;
//import opennlp.tools.util.PlainTextByLineStream;
//import opennlp.tools.util.Span;
//
//import com.swabunga.spell.engine.SpellDictionaryHashMap;
//import com.swabunga.spell.engine.Word;
//import com.swabunga.spell.event.SpellCheckEvent;
//import com.swabunga.spell.event.SpellCheckListener;
//import com.swabunga.spell.event.SpellChecker;
//import com.swabunga.spell.event.StringWordTokenizer;
//import com.swabunga.spell.event.TeXWordFinder;
//
//public class JazzySpellChecker implements SpellCheckListener {
//
//	private SpellChecker spellChecker;
//	private List<String> misspelledWords;
//
//	/**
//	 * get a list of misspelled words from the text
//	 * 
//	 * @param text
//	 */
//	public List<String> getMisspelledWords(String text) {
//		StringWordTokenizer texTok = new StringWordTokenizer(text,
//				new TeXWordFinder());
//		spellChecker.checkSpelling(texTok);
//		return misspelledWords;
//	}
//
//	private static SpellDictionaryHashMap dictionaryHashMap;
//
//	static {
//
//		File dict = new File("dictionary/words.utf-8.txt");
//		try {
//			dictionaryHashMap = new SpellDictionaryHashMap(dict);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	private void initialize() {
//		spellChecker = new SpellChecker(dictionaryHashMap);
//		spellChecker.addSpellCheckListener(this);
//	}
//
//	public JazzySpellChecker() {
//
//		misspelledWords = new ArrayList<String>();
//		initialize();
//	}
//
//	/**
//	 * correct the misspelled words in the input string and return the result
//	 */
//	public String getCorrectedLine(String line) {
//		List<String> misSpelledWords = getMisspelledWords(line);
//
//		for (String misSpelledWord : misSpelledWords) {
//			List<String> suggestions = getSuggestions(misSpelledWord);
//			if (suggestions.size() == 0)
//				continue;
//			String bestSuggestion = suggestions.get(0);
//			line = line.replace(misSpelledWord, bestSuggestion);
//		}
//		return line;
//	}
//
//	public String getCorrectedText(String line) {
//		StringBuilder builder = new StringBuilder();
//		String[] tempWords = line.split(" ");
//		for (String tempWord : tempWords) {
//			if (!spellChecker.isCorrect(tempWord)) {
//				List<Word> suggestions = spellChecker.getSuggestions(tempWord,
//						0);
//				if (suggestions.size() > 0) {
//					builder.append(spellChecker.getSuggestions(tempWord, 0)
//							.get(0).toString());
//				} else
//					builder.append(tempWord);
//			} else {
//				builder.append(tempWord);
//			}
//			builder.append(" ");
//		}
//		return builder.toString().trim();
//	}
//
//	public List<String> getSuggestions(String misspelledWord) {
//
//		@SuppressWarnings("unchecked")
//		List<Word> su99esti0ns = spellChecker.getSuggestions(misspelledWord, 0);
//		List<String> suggestions = new ArrayList<String>();
//		for (Word suggestion : su99esti0ns) {
//			suggestions.add(suggestion.getWord());
//			System.out.println(suggestion.getWord());
//		}
//
//		return suggestions;
//	}
//
//	@Override
//	public void spellingError(SpellCheckEvent event) {
//		event.ignoreWord(true);
//		misspelledWords.add(event.getInvalidWord());
//	}
//
//	public static void main(String[] args) throws InvalidFormatException, IOException {
//		POSModel model = new POSModelLoader()
//				.load(new File("src/en-pos-maxent.bin"));
//		PerformanceMonitor perfMon = new PerformanceMonitor(System.err, "sent");
//		POSTaggerME tagger = new POSTaggerME(model);
//
//		String input = "I am a Senior Lecturer and a member of the Language and Computation Group here at Essex .";
//		ObjectStream<String> lineStream = new PlainTextByLineStream(
//				new StringReader(input));
//
//		perfMon.start();
//		String line;
//		String whitespaceTokenizerLine[] = null;
//
//		String[] tags = null;
//		while ((line = lineStream.read()) != null) {
//
//			whitespaceTokenizerLine = WhitespaceTokenizer.INSTANCE
//					.tokenize(line);
//			tags = tagger.tag(whitespaceTokenizerLine);
//
//			POSSample sample = new POSSample(whitespaceTokenizerLine, tags);
//			System.out.println(sample.toString());
//
//			perfMon.incrementCounter();
//		}
//		perfMon.stopAndPrintFinalResult();
//
//		// chunker
//		InputStream is = new FileInputStream("src/en-chunker.bin");
//		ChunkerModel cModel = new ChunkerModel(is);
//
//		ChunkerME chunkerME = new ChunkerME(cModel);
//		String result[] = chunkerME.chunk(whitespaceTokenizerLine, tags);
//
//		for (String s : result)
//			System.out.println(s);
//
//		Span[] span = chunkerME.chunkAsSpans(whitespaceTokenizerLine, tags);
//		for (Span s : span)
//			System.out.println(s.toString());
//	}
//}