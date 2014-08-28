//	import java.io.File;
//	import java.io.FileInputStream;
//	import java.io.IOException;
//	import java.io.InputStream;
//	import java.io.PrintWriter;
//
//	import org.jsoup.Jsoup;
//	import org.jsoup.nodes.Document;
//	import org.jsoup.nodes.Element;
//	import org.jsoup.select.Elements;
//
//	import opennlp.tools.cmdline.parser.ParserTool;
//	import opennlp.tools.parser.Parse;
//	import opennlp.tools.parser.Parser;
//	import opennlp.tools.parser.ParserFactory;
//	import opennlp.tools.parser.ParserModel;
//	import opennlp.tools.sentdetect.SentenceDetectorME;
//	import opennlp.tools.sentdetect.SentenceModel;
//	import opennlp.tools.util.InvalidFormatException;
//public class backupfile {
//
//
//	
//
//		public static void main(String[] args) throws InvalidFormatException,
//				IOException {
//			File input = new File("src/The Udo.htm");
//			Document doc = Jsoup.parse(input, "UTF-8", "");
//			String metaText = "";
//			// always start with a model, a model is learned from training data
//			InputStream is = new FileInputStream("src/en-sent.bin");
//			SentenceModel model = new SentenceModel(is);
//			SentenceDetectorME sdetector = new SentenceDetectorME(model);
//
//			Elements metaContents = doc.getElementsByTag("meta");
//
//			for (Element content : metaContents) {
//				metaText = content.attr("content");
//			}
//			String text = doc.text() + metaText;
//			System.out.println(text);
//			text = text.replaceAll("\\((.*?)\\)\\s?|\\_", "");
//			 
//			String sentences[] = sdetector.sentDetect(text);
//	 
//			PrintWriter writer = new PrintWriter("src/output.txt", "UTF-8");
//			writer.println(text);
//			
//			writer.close();
//			for (int i = 0; i < sentences.length; i++) {
////				System.out.println(sentences[i]);
//			}
//			is.close();
//			
//
//		
//	}
//
//}
