
 
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
 


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
 
public class GoogleSearchJava {
 
    //public static final String GOOGLE_SEARCH_URL = "https://www.google.com/search";
	public static final String GOOGLE_SEARCH_URL = "https://www.google.com/search?site=imghp";
    public static void main(String[] args) throws IOException {
       
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the search term.");
        String searchTerm = scanner.nextLine();
        System.out.println("Please enter the number of results. Example: 5 10 20");
        int num = scanner.nextInt();
        scanner.close();
         
        //String searchURL = GOOGLE_SEARCH_URL + "?q="+searchTerm+"&num="+num;
        String searchURL = GOOGLE_SEARCH_URL + "&q="+searchTerm+"&num="+num;
        Document doc = Jsoup.connect(searchURL).userAgent("Mozilla/5.0").get();
         
        Elements results = doc.select("h3.r > a");

        List<String> b = new ArrayList();
        
        for (Element result : results) {
            String linkHref = result.attr("href");
            String linkText = result.text();
            System.out.println("Text::" + linkText + ", URL::" + linkHref.substring(6, linkHref.indexOf("&")));
            
            String s = linkHref.substring(6, linkHref.indexOf("&"));
            
            b.add(s);
           
        }
        
        String []urls = new String[1];
        
        urls[0] = b.get(0).replace("=", "");
        
        ListLinks.main(urls);
        
    }
 
    public static List<Info> Search(String searchTerm){
    	
    	
         //String GOOGLE_SEARCH_URL = "https://www.google.com/search";
    	 String GOOGLE_SEARCH_URL = "https://www.google.com/search?site=imghp";

            int num = 250;
                         
            //String searchURL = GOOGLE_SEARCH_URL + "?q="+searchTerm+"&num="+num;
            String searchURL = GOOGLE_SEARCH_URL + "&q="+searchTerm+"&num="+num;
            Document doc = null;
			try {
				doc = Jsoup.connect(searchURL).userAgent("Mozilla/5.0").get();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
             
            Elements results = doc.select("h3.r > a");

            List<Info> b = new ArrayList();
            
            for (Element result : results) {
                String linkHref = result.attr("href");
                String linkText = result.text();
                System.out.println("Text::" + linkText + ", URL::" + linkHref.substring(6, linkHref.indexOf("&")));
                
                String s = linkHref.substring(6, linkHref.indexOf("&"));
                
                Info info = new Info(s, linkText, linkHref, searchTerm);
                
                b.add(info);
               
            }
            
         return b;
    	
    	
    	
    }
    
    
    
}