///**
// * Created with IntelliJ IDEA.
// * User: schenr
// * Date: 04/12/13
// * Time: 14:19
// * To change this template use File | Settings | File Templates.
// */
//
////import org.jsoup.*;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//import qtag.Tagger;
//
//import javax.xml.parsers.DocumentBuilder;
//import java.io.*;
//import java.io.IOException;
//import java.io.StringReader;
//import java.lang.*;
//import java.lang.Exception;
//import java.lang.NullPointerException;
//import java.lang.String;
//import java.util.StringTokenizer;
//
//public class Assi2_old {
//
//
//    public static final String PUNCT = "!\"£$%^&*()_+=#{}[];:'`/?,. \t\n";
//
//    public static String tokenise(String line) {
//        StringTokenizer st = new StringTokenizer(line, PUNCT, true);
//        String tok1 = " ";
//        String tok2 = " ";
//        boolean skip = false;
//        StringBuffer retval = new StringBuffer();
//        while (st.hasMoreTokens()) {
//            tok1 = tok2;
//            tok2 = st.nextToken();
//            if (",".equals(tok1)
//                    || ".".equals(tok1)
//                    || "-".equals(tok1)
//                    || "'".equals(tok1)) {    // special punctuation mark
//                if (Character.isWhitespace(tok2.charAt(0))) {
//                    tok1 = " " + tok1;
//                } else {
//                    skip = true;
//                }
//            } else {
//                if (skip == false) {
//                    tok1 = " " + tok1;
//                }
//                skip = false;
//            }
//            retval.append(tok1);
//        }
//        if (skip == false) {
//            tok2 = " " + tok2;
//        }
//        retval.append(tok2);
//        return (retval.toString());
//    }
//
//    public static void main(String[] args) throws IOException {
////        Tagger tagger = new Tagger("qtag-eng");
//        File input = new File("The Udo.htm");
////        Document doc = Jsoup.parse(input, "UTF-8", "");
//        String metaText = "";
//        int tokenId = 1;
//
////        Elements metaContents = doc.getElementsByTag("meta");
//
//        for (Element content : metaContents) {
//            metaText = content.attr("content");
//        }
//        String text =    doc.text() + metaText;
//        BufferedReader br = new BufferedReader(new StringReader(text));
//        String line = br.readLine();
//
//        try {
//            while (line != null) {
//            //System.out.println(line);
//                line = line.replaceAll("\\((.*?)\\)\\s?|\\_", "");
//                System.out.println(line);
//                String tokens[] = Assi2.tokenise(line).split("\\n|\\s\\s|(\\.\\.\\.)|\\.|\\)|\\n");
//                String tags[] = tagger.tag(tokens);
//                //Output the tokens and corresponding tags
//
//                for (int i = 1; i < tokens.length; i++, tokenId++) {
//
////                    System.out.println(tokens[i]);
//                    //Print XML-like format
//                    System.out.println("<W id=\"" + tokenId + "\" POS=\"" + tags[i] + "\">" + tokens[i] + "</W>");
//                }
////                System.out.println(line);
//                line = br.readLine();
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//
////        System.out.println(contents);
//
//
//    }
//
//}
