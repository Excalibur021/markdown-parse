// File reading code from https://howtodoinjava.com/java/io/java-read-file-to-string-examples/
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class MarkdownParse {
    public static ArrayList<String> getLinks(String markdown) {
        ArrayList<String> toReturn = new ArrayList<>();
        // find the next [, then find the ], then find the (, then take up to
        // the next )
        int currentIndex = 0;
        while(currentIndex < markdown.length()) {
            int nextOpenBracket = markdown.indexOf("[", currentIndex);
            if(nextOpenBracket == -1) {
                currentIndex = currentIndex +1;
                continue;
            }

            int nextCloseBracket = markdown.indexOf("]", nextOpenBracket);
            if(nextCloseBracket == -1) {
                currentIndex = nextOpenBracket +1;
                continue;
            }

            int openParen = markdown.indexOf("(", nextCloseBracket);
            if(openParen == -1) {
                currentIndex = nextCloseBracket +1;
                continue;
            }

            int closeParen = markdown.indexOf(")", openParen);
            if(closeParen == -1) {
                currentIndex = openParen +1;
                continue;
            }

            if (openParen != nextCloseBracket +1) {
                currentIndex = closeParen + 1;
                continue;
            }

            String str = markdown.substring(openParen + 1, closeParen);
            if (str.indexOf(" ") == -1) {
                toReturn.add(str);
            }
            currentIndex = closeParen + 1;
        }
        return toReturn;
    }
    public static void main(String[] args) throws IOException {
		Path fileName = Path.of(args[0]);
	    String contents = Files.readString(fileName);
        ArrayList<String> links = getLinks(contents);
        System.out.println(links);
    }
}