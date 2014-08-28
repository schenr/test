//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.PrintWriter;
//import java.io.StringReader;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.nodes.Node;
//import org.jsoup.nodes.TextNode;
//import org.jsoup.safety.Whitelist;
//import org.jsoup.select.Elements;
//import org.jsoup.select.NodeTraversor;
//import org.jsoup.select.NodeVisitor;
//import org.xeustechnologies.googleapi.spelling.SpellChecker;
//import org.xeustechnologies.googleapi.spelling.SpellCorrection;
//import org.xeustechnologies.googleapi.spelling.SpellResponse;
//
//import com.sun.org.apache.xalan.internal.xsltc.trax.OutputSettings;
//
//import opennlp.tools.cmdline.PerformanceMonitor;
//import opennlp.tools.cmdline.parser.ParserTool;
//import opennlp.tools.cmdline.postag.POSModelLoader;
//import opennlp.tools.parser.Parse;
//import opennlp.tools.parser.Parser;
//import opennlp.tools.parser.ParserFactory;
//import opennlp.tools.parser.ParserModel;
//import opennlp.tools.postag.POSModel;
//import opennlp.tools.postag.POSSample;
//import opennlp.tools.postag.POSTaggerME;
//import opennlp.tools.sentdetect.SentenceDetectorME;
//import opennlp.tools.sentdetect.SentenceModel;
//import opennlp.tools.tokenize.WhitespaceTokenizer;
//import opennlp.tools.util.InvalidFormatException;
//import opennlp.tools.util.ObjectStream;
//import opennlp.tools.util.PlainTextByLineStream;
//
//public class Assi2 {
//
//	public static void main(String[] args) throws InvalidFormatException,
//			IOException {
//		File input = new File("src/The Udo.htm");
//		// File input = new File("src/CE314 CE887  Syllabus.htm");
//		final StringBuilder buffer = new StringBuilder();
//		Document doc = Jsoup.parse(input, "UTF-8", "");
//		Elements metaContents = doc.getElementsByTag("meta");
//
//		InputStream is = new FileInputStream("src/en-sent.bin");
//		SentenceModel model = new SentenceModel(is);
//		SentenceDetectorME sdetector = new SentenceDetectorME(model);
//		String text = "";
//
//		InputStream is1 = new FileInputStream("src/en-parser-chunking.bin");
//		ParserModel Pmodel = new ParserModel(is1);
//		Parser parser = ParserFactory.create(Pmodel);
//
//		POSModel POSmodel = new POSModelLoader().load(new File(
//				"src/en-pos-maxent.bin"));
//		PerformanceMonitor perfMon = new PerformanceMonitor(System.err, "sent");
//		POSTaggerME tagger = new POSTaggerME(POSmodel);
//
//		for (Element content : metaContents) {
//			text += content.attr("content").replaceAll("\\,\\s*", "\n") + "\n";
//			// System.out.println(content.attributes());
//		}
//
//		// reference:
//		// http://stackoverflow.com/questions/5640334/how-do-i-preserve-line-breaks-when-using-jsoup-to-convert-html-to-plain-text
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
//		text = text + buffer.toString();
//		BufferedReader br = new BufferedReader(new StringReader(text));
//		String line = br.readLine();
//		String sortedText = "";
//		while (line != null) {
//			Pattern pattern = Pattern.compile("\\((.*?)\\)\\s?");
//
//			Matcher matcher = pattern.matcher(line);
//			while (matcher.find()) {
//				sortedText = sortedText + matcher.group(1) + "\n";
//			}
//			sortedText = sortedText + line + "\n";
//			line = br.readLine();
//		}
//
//		sortedText = sortedText.replaceAll("\\((.*?)\\)\\s?", "")
//				.replaceAll("\\s{2,}\\,", " ,")
//				.replaceAll("(\\w{3,})(\\s?\\.+|$)", "$1\n")
//				.replaceAll("\\s{2,}", "\n").replaceAll("(.+)", "$1 .");
//		// System.out.println(sortedText);
//		/* Sentence splitting */
//		String sentences[] = sdetector.sentDetect(sortedText);
//
//		for (int i = 0; i < sentences.length; i++) {
//
////			 System.out.println(i + ":" + sentences[i]);
//		}
//		for (int i = 0; i < sentences.length; i++) {
//			Parse topParses[] = ParserTool.parseLine(sentences[i], parser, 1);
//			// System.out.println(i);
//			System.out.print(i+":");
//			for (Parse p : topParses) {
//				
//				p.show();
//				
//				System.out.println(p.getType());
//			}
//			
//
//
//
//		}
//
//		for (int i = 0; i < sentences.length; i++) {
//
//			String whitespaceTokenizerLine[] = WhitespaceTokenizer.INSTANCE
//					.tokenize(sentences[i]);
//			String[] tags = tagger.tag(whitespaceTokenizerLine);
//			POSSample sample = new POSSample(whitespaceTokenizerLine, tags);
//			// System.out.println(i+":"+sample.toString());
//
//		}
//		is.close();
//		is1.close();
//	}
//
//}
