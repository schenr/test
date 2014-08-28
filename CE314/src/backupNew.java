//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.io.StringReader;
//import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Map.Entry;
//import java.util.SortedSet;
//import java.util.TreeMap;
//import java.util.TreeSet;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import opennlp.tools.chunker.ChunkerME;
//import opennlp.tools.chunker.ChunkerModel;
//import opennlp.tools.postag.POSModel;
//import opennlp.tools.postag.POSTaggerME;
//import opennlp.tools.sentdetect.SentenceDetectorME;
//import opennlp.tools.sentdetect.SentenceModel;
//import opennlp.tools.tokenize.TokenizerME;
//import opennlp.tools.tokenize.TokenizerModel;
//import opennlp.tools.util.InvalidFormatException;
//import opennlp.tools.util.Span;
//
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.nodes.Node;
//import org.jsoup.nodes.TextNode;
//import org.jsoup.select.Elements;
//import org.jsoup.select.NodeTraversor;
//import org.jsoup.select.NodeVisitor;
//
//public class backupNew {
//
//	public static void main(String[] args) throws InvalidFormatException,
//			IOException {
//
//		/* Declaration of openNLP tools */
//		TokenizerME tokenizer;
//		POSTaggerME posTagger;
//		ChunkerME chunker;
//		SentenceDetectorME sdetector;
//
////		File input = new File("src/The Udo.htm");
//		// File input = new File("src/CE314 CE887  Syllabus.htm");
//		 File input = new File("src/BBC News1.htm");
//
//		final StringBuilder buffer = new StringBuilder();
//		Document doc = Jsoup.parse(input, "UTF-8", "");// Declare jSoup document
//		/*
//		 * Declare meta element for extracting keywords from <meta> tag
//		 */
//		Elements metaContents = doc.select("meta[name=keywords]");
//
//		/* Declaration of openNLP tools model */
//		SentenceModel model = new SentenceModel(new FileInputStream(
//				"src/en-sent.bin"));
//		sdetector = new SentenceDetectorME(model);
//
//		TokenizerModel tmodel = new TokenizerModel(new FileInputStream(
//				"src/en-token.bin"));
//		tokenizer = new TokenizerME(tmodel);
//
//		POSModel pmodel = new POSModel(new FileInputStream(
//				"src/en-pos-maxent.bin"));
//		posTagger = new POSTaggerME(pmodel);
//
//		ChunkerModel cmodel = new ChunkerModel(new FileInputStream(
//				"src/en-chunker.bin"));
//		chunker = new ChunkerME(cmodel);
//
//		String text = "";
//
//		/* Extract keywords from meta tag of the HTML page */
//		for (Element content : metaContents) {
//			text += content.attr("content").replaceAll("\\,\\s*", "\n") + "\n";
//		}
//
//		/*
//		 * jSoup NodeTraversor extract structured HTML content and stores into a
//		 * stringBuilder. This function avoid the problem that jSoup turns all
//		 * contents into one string.
//		 * Reference:http://stackoverflow.com/questions
//		 * /5640334/how-do-i-preserve
//		 * -line-breaks-when-using-jsoup-to-convert-html-to-plain-text
//		 */
//		new NodeTraversor(new NodeVisitor() {
//			boolean lineBreak = true;
//
//			public void head(Node node, int depth) {
//				if (node instanceof TextNode) {
//					TextNode textNode = (TextNode) node;
//					String text = textNode.text().replace('\u00A0', ' ').trim()
//							+ " ";
//					if (!text.isEmpty()) {
//						buffer.append(text);
//						lineBreak = false;
//					}
//				} else if (node instanceof Element) {
//					Element element = (Element) node;
//					if (!lineBreak) {
//						if ((element.isBlock() || element.tagName()
//								.equals("br"))) {
//							buffer.append("\n");
//							lineBreak = true;
//						}
//					}
//				}
//			}
//
//			@Override
//			public void tail(Node node, int depth) {
//			}
//		}).traverse(doc);
//
//		/* Turn extracted contents into a String */
//		text = text + buffer.toString();
//		BufferedReader br = new BufferedReader(new StringReader(text));
//		String line = br.readLine();
//		String sortedText = "";
//		/*
//		 * Extract keywords from meta tag of the HTML page and operate the first
//		 * normalize
//		 */
//		while (line != null) {
//			Pattern pattern = Pattern.compile("\\((.*?)\\)\\s?");
//
//			Matcher matcher = pattern.matcher(line);
//			while (matcher.find()) {
//				// sortedText = sortedText + matcher.group(1) + "\n";
//				sortedText = sortedText + "\n";
//			}
//			sortedText = sortedText + line + "\n";
//			line = br.readLine();
//		}
//		/*
//		 * first normalize, remove some punctuations/white spaces and add a full
//		 * stop at the end of the string for the sentence detection
//		 */
//		sortedText = sortedText.replaceAll("\\((.*?)\\)\\s?", "")
//				.replaceAll("\\s{2,}\\,", " ,")
//				.replaceAll("(\\w{3,})(\\s?\\.+|$)", "$1\n")
//				.replaceAll("\\s{2,}", "\n").replaceAll("(.+)", "$1 .");
//
//		/* Sentence split */
//		Span sentences[] = sdetector.sentPosDetect(sortedText);
//
//		/* Initialize array to store keywords */
//		ArrayList<String> keywords = new ArrayList<String>();
//
//		/*
//		 * Keywords Selecting/POS tagging - get nouns(NN,NNS,NNP,NNPS) and uses
//		 * chunker to retrieval nouns phrase(NP) and store them into an array
//		 */
//		for (Span sentSpan : sentences) {
//			String sentence = sentSpan.getCoveredText(sortedText).toString();
//			int startPos = sentSpan.getStart();
//			Span[] tokenSpans = tokenizer.tokenizePos(sentence);
//			String[] tokens = new String[tokenSpans.length];
//
//			/*
//			 * ignores a single punctuation word and any symbol at the end of
//			 * the word.
//			 */
//			Pattern pattern = Pattern.compile("\\W+$|\\_$");
//
//			for (int i = 0; i < tokens.length; i++) {
//				tokens[i] = tokenSpans[i].getCoveredText(sentence).toString();
//			}
//			String[] tags = posTagger.tag(tokens);// POS tagging
//			/*
//			 * chunking identify the phrases
//			 */
//			Span[] chunks = chunker.chunkAsSpans(tokens, tags);
//			String NN = "";// String that stores Nouns
//			/*
//			 * POS tagging get nouns(NN,NNS,NNP,NNPS) and store them into
//			 * keywords array
//			 */
//			for (int i = 0; i < tags.length; i++) {
//				Matcher matcher = pattern.matcher(tokens[i].toString());
//				if (tags[i].equals("NN") || tags[i].equals("NNS")
//						|| tags[i].equals("NNP") || tags[i].equals("NNPS")) {
//
//					if (!matcher.find()) {
//						NN = tokens[i].toLowerCase();// turn NN to lower-case
//						if (NN != "")
//							keywords.add(NN);// add to the keyword arr
//					}
//				}
//			}
//			/*
//			 * Uses chunker to retrieval nouns phrase(NP) and store them into
//			 * keywords array
//			 */
//			for (Span chunk : chunks) {
//
//				if ("NP".equals(chunk.getType())) {
//
//					int npstart = startPos
//							+ tokenSpans[chunk.getStart()].getStart();
//					int npend = startPos
//							+ tokenSpans[chunk.getEnd() - 1].getEnd();
//					/*
//					 * Second normalization operation turn to lower-case, remove
//					 * some bad punctuations and white space and stopwords
//					 */
//					String NP = sortedText
//							.substring(npstart, npend)
//							.replaceAll("((\\\'s|\\W)\\s?)?([^\\W]\\w*?.*)",
//									"$3")
//							.replaceAll("\\W+$|^.$", "")
//							.toLowerCase()
//							.replaceAll(
//									/* Stopwords removal */
//									"(^a\\s|^a$)|(^able\\s|^able$)|(^about\\s|^about$)|(^across\\s|^across$)|(^after\\s|^after$)|(^all\\s|^all$)|(^almost\\s|^almost$)|(^also\\s|^also$)|(^am\\s|^am$)|(^among\\s|^among$)|(^an\\s|^an$)|(^and\\s|^and$)|(^any\\s|^any$)|(^are\\s|^are$)|(^as\\s|^as$)|(^at\\s|^at$)|(^be\\s|^be$)|(^because\\s|^because$)|(^been\\s|^been$)|(^but\\s|^but$)|(^by\\s|^by$)|(^can\\s|^can$)|(^cannot\\s|^cannot$)|(^could\\s|^could$)|(^dear\\s|^dear$)|(^did\\s|^did$)|(^do\\s|^do$)|(^does\\s|^does$)|(^either\\s|^either$)|(^else\\s|^else$)|(^ever\\s|^ever$)|(^every\\s|^every$)|(^for\\s|^for$)|(^from\\s|^from$)|(^get\\s|^get$)|(^got\\s|^got$)|(^had\\s|^had$)|(^has\\s|^has$)|(^have\\s|^have$)|(^he\\s|^he$)|(^her\\s|^her$)|(^hers\\s|^hers$)|(^him\\s|^him$)|(^his\\s|^his$)|(^how\\s|^how$)|(^however\\s|^however$)|(^i\\s|^i$)|(^if\\s|^if$)|(^in\\s|^in$)|(^into\\s|^into$)|(^is\\s|^is$)|(^it\\s|^it$)|(^its\\s|^its$)|(^just\\s|^just$)|(^least\\s|^least$)|(^let\\s|^let$)|(^like\\s|^like$)|(^likely\\s|^likely$)|(^may\\s|^may$)|(^me\\s|^me$)|(^might\\s|^might$)|(^most\\s|^most$)|(^must\\s|^must$)|(^my\\s|^my$)|(^neither\\s|^neither$)|(^no\\s|^no$)|(^nor\\s|^nor$)|(^not\\s|^not$)|(^of\\s|^of$)|(^off\\s|^off$)|(^often\\s|^often$)|(^on\\s|^on$)|(^only\\s|^only$)|(^or\\s|^or$)|(^other\\s|^other$)|(^our\\s|^our$)|(^own\\s|^own$)|(^rather\\s|^rather$)|(^said\\s|^said$)|(^say\\s|^say$)|(^says\\s|^says$)|(^she\\s|^she$)|(^should\\s|^should$)|(^since\\s|^since$)|(^so\\s|^so$)|(^some\\s|^some$)|(^than\\s|^than$)|(^that\\s|^that$)|(^the\\s|^the$)|(^their\\s|^their$)|(^them\\s|^them$)|(^then\\s|^then$)|(^there\\s|^there$)|(^these\\s|^these$)|(^they\\s|^they$)|(^this\\s|^this$)|(^tis\\s|^tis$)|(^to\\s|^to$)|(^too\\s|^too$)|(^twas\\s|^twas$)|(^us\\s|^us$)|(^wants\\s|^wants$)|(^was\\s|^was$)|(^we\\s|^we$)|(^were\\s|^were$)|(^what\\s|^what$)|(^when\\s|^when$)|(^where\\s|^where$)|(^which\\s|^which$)|(^while\\s|^while$)|(^who\\s|^who$)|(^whom\\s|^whom$)|(^why\\s|^why$)|(^will\\s|^will$)|(^with\\s|^with$)|(^would\\s|^would$)|(^yet\\s|^yet$)|(^you\\s|^you$)|(^yours\\s|^yours$)",
//									"").replaceAll("\\'\\s+", "");
//
//					if (!NP.equals(NN) && !NP.equals("")) {
//						keywords.add(NP);// add to keywords arr
//					}
//
//				}
//
//			}
//
//		}
//
//		/*
//		 * Stores all keywords into a treeMap and count numbers of the keywords
//		 * and updates the value of it by +1
//		 */
//		Map<String, Float> keywordsMap = new TreeMap<String, Float>();
//		for (int i = 0; i < keywords.size(); i++) {
//			// check if the key word matches any of other keywords
//			Pattern pattern = Pattern.compile("[^\\w]?" + keywords.get(i));
//			Matcher matcher = pattern.matcher(keywords.get(i));
//			if (!keywordsMap.containsKey(keywords.get(i))) {
//				keywordsMap.put(keywords.get(i), (float) 1);
//			} else {
//				if (matcher.find())
//					// put into the map
//					keywordsMap.put(keywords.get(i),
//							keywordsMap.get(keywords.get(i)) + (float) 1);
//
//			}
//		}
//
//		/*
//		 * Statistic approach calculate the probability of the keyword appear in
//		 * the web site
//		 */
//		Iterator<String> iterator = keywordsMap.keySet().iterator();
//		float keywordsNumber = (float) keywordsMap.size();
//		while (iterator.hasNext()) {
//			String key = iterator.next().toString();
//			// calculate the probability of the keyword appear in the web site
//			float value = keywordsMap.get(key) / keywordsNumber;
//			keywordsMap.put(key, value); // Update the value to probability
//		}
//
//		/* Write all statistical data in to files and print out */
//		PrintWriter writer = new PrintWriter("src/output.txt", "UTF-8");
//		for (Entry<String, Float> entry : sortDescending(keywordsMap)) {
//			entry.getKey();
//			/* Stemming the word by calling the stemWord function */
//			System.out.println(stemWord(entry.getKey()) + ":"
//					+ entry.getValue());
//			writer.println(stemWord(entry.getKey()) + ":" + entry.getValue());
//
//		}
//
//		writer.close();
//
//	}
//
//	/* Function that sort Map descending */
//	static <K, V extends Comparable<? super V>> SortedSet<Map.Entry<K, V>> sortDescending(
//			Map<K, V> map) {
//		SortedSet<Map.Entry<K, V>> sortedMap = new TreeSet<Map.Entry<K, V>>(
//				new Comparator<Map.Entry<K, V>>() {
//					@Override
//					public int compare(Map.Entry<K, V> e1, Map.Entry<K, V> e2) {
//						int res = e2.getValue().compareTo(e1.getValue());
//						return res != 0 ? res : 1; // Special fix to preserve
//													// items with equal values
//					}
//				});
//		sortedMap.addAll(map.entrySet());
//		return sortedMap;
//	}
//
//	/* Function that stem the word */
//	public static String stemWord(String word) {
//		PorterStemmer stemmer = new PorterStemmer();
//		return stemmer.stem(word);
//	}
//
//}
