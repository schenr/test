import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.util.InvalidFormatException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Assi2test {

	public static void main(String[] args) throws InvalidFormatException,
			IOException {
		File input = new File("src/The Udo.htm");
		// File input = new File("src/CE314 CE887  Syllabus.htm");

		Document doc = Jsoup.parse(input, "UTF-8", "");
		Elements metaContents = doc.getElementsByTag("meta");
		InputStream is = new FileInputStream("src/en-sent.bin");
		SentenceModel model = new SentenceModel(is);
		SentenceDetectorME sdetector = new SentenceDetectorME(model);
		String text = "";
		for (Element content : metaContents) {
			// System.out.println(content.attr("content"));
			text += content.attr("content").replaceAll("\\,\\s*", "\n");
		}
		// Element descA = doc.select("a").first();
		// String desc = descA.text();
		// System.out.println(desc);

		for (Element body : doc.select("html")) {
			for (Element table : body.select("table")) {
				for (Element content : table
						.select("center,h1, h2, h3, h4, h5, h6,p,li")) {

					// System.out.println(content.nodeName());
					if (content.hasText()) {
						System.out.println(content.nodeName());
						System.out.println(content.text());
					}
					// for(Element link : content.select("a")){
					// System.out.println(content.text());
					// }
					String parse = content.text().replaceAll(
							"\\((.*?)\\)\\s?|\\_", "");
					text = text + parse + "\n";

				}
			}
		}

		// System.out.println(text);
		// System.out.println(doc.text());
		String sentences[] = sdetector.sentDetect(text);

		for (int i = 0; i < sentences.length; i++) {
			// System.out.println(sentences[i]);
		}
		is.close();

	}
}
