

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ListLinks {
    public static void main(String[] args) throws IOException {
        Validate.isTrue(args.length == 1, "usage: supply url to fetch");
        String url = args[0];

        Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0").get();
        Elements links = doc.select("a[href]");
        Elements media = doc.select("[src]");
        Elements imports = doc.select("link[href]");

        print("\nMedia: (%d)", media.size());
        for (Element src : media) {
            if (src.tagName().equals("img"))
                print(" * %s: <%s> %sx%s (%s)",
                        src.tagName(), src.attr("abs:src"), src.attr("width"), src.attr("height"),
                        trim(src.attr("alt"), 20));
            else
                print(" * %s: <%s>", src.tagName(), src.attr("abs:src"));
        }

        print("\nImports: (%d)", imports.size());
        for (Element link : imports) {
            print(" * %s <%s> (%s)", link.tagName(),link.attr("abs:href"), link.attr("rel"));
        }

        print("\nLinks: (%d)", links.size());
        for (Element link : links) {
            print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
        }
    }

    public static String GetSource(String url) throws IOException {

        Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0").get();
        return doc.toString();
    }

    public static List<Info> GetImages(String url) throws IOException {

        Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0").get();
        
        Elements links = doc.select("a[href]");
        Elements media = doc.select("[src]");
        Elements imports = doc.select("link[href]");

        ArrayList<Info> infos = new ArrayList<Info>();
        
        print("\nMedia: (%d)", media.size());
        for (Element src : media) {
            if (src.tagName().equals("img"))
                print(" * %s: <%s> %sx%s (%s)",
                        src.tagName(), src.attr("abs:src"), src.attr("width"), src.attr("height"),
                        trim(src.attr("alt"), 20));
            
            
            
            else
                print(" * %s: <%s>", src.tagName(), src.attr("abs:src"));
            
            Info info = new Info(src.attr("abs:src"),src.attr("abs:src"),src.attr("abs:src"),"");
            
            infos.add(info);
            
        }

        return infos;
        

    }

    public static List<Info> GetLinks(String url) throws IOException {
    

        Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0").get();
        
        Elements links = doc.select("a[href]");
        Elements media = doc.select("[src]");
        Elements imports = doc.select("link[href]");

        ArrayList<Info> infos = new ArrayList<Info>();
        
        for (Element link : links) {
          print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));

          Info info = new Info(link.attr("abs:href"),link.attr("abs:href"),link.attr("abs:href"),trim(link.text(),35));
          
          infos.add(info);
          
        }
        

        return infos;
   
    }

    
    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

    private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width-1) + ".";
        else
            return s;
    }
}