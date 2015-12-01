
// AIzaSyDMIz2-bjZTdFiKddX2W06-6CG-uySE6uE

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import com.mongodb.MongoClient;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Reference;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Prints a list of videos based on a search term.  Based on YoutTube samples*/

public class Search {

  /** Global instance properties filename. */
  private static String PROPERTIES_FILENAME = "youtube.properties";

  /** Global instance of the HTTP transport. */
  private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

  /** Global instance of the JSON factory. */
  private static final JsonFactory JSON_FACTORY = new JacksonFactory();

  /** Global instance of the max number of videos we want returned (50 = upper limit per page). */
  private static final long NUMBER_OF_VIDEOS_RETURNED = 50;

  /** Global instance of Youtube object to make all API requests. */
  private static YouTube youtube;


  public static List<Info> mains(String[] args) {
  

    try {
    
      youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer() {
        public void initialize(HttpRequest request) throws IOException {}
      }).setApplicationName("youtube-cmdline-search-sample").build();
     
      String queryTerm = getInputQuery();

      YouTube.Search.List search = youtube.search().list("id,snippet");
  
      String apiKey = "AIzaSyDMIz2-bjZTdFiKddX2W06-6CG-uySE6uE";
      search.setKey(apiKey);
      search.setQ(queryTerm);
    
      search.setType("video");
 
      search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
      search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);
      SearchListResponse searchResponse = search.execute();

      List<SearchResult> searchResultList = searchResponse.getItems();

      if (searchResultList != null) {
        prettyPrint(searchResultList.iterator(), queryTerm);
      }
      
      return GetInfo(searchResultList.iterator());
      
    } catch (GoogleJsonResponseException e) {
      System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
          + e.getDetails().getMessage());
    } catch (IOException e) {
      System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
    } catch (Throwable t) {
      t.printStackTrace();
    
    }
   
   return null;
    
  }

  public static List<Info> Search(String queryTerm) {
	  

	    try {
	    
	      youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer() {
	        public void initialize(HttpRequest request) throws IOException {}
	      }).setApplicationName("youtube-cmdline-search-sample").build();
	     
	      
	      YouTube.Search.List search = youtube.search().list("id,snippet");
	  
	      String apiKey = "AIzaSyDMIz2-bjZTdFiKddX2W06-6CG-uySE6uE";
	      search.setKey(apiKey);
	      search.setQ(queryTerm);
	    
	      search.setType("video");
	 
	      search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
	      search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);
	      SearchListResponse searchResponse = search.execute();

	      List<SearchResult> searchResultList = searchResponse.getItems();

	      if (searchResultList != null) {
	        prettyPrint(searchResultList.iterator(), queryTerm);
	      }
	      
	      return GetInfo(searchResultList.iterator());
	      
	    } catch (GoogleJsonResponseException e) {
	      System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
	          + e.getDetails().getMessage());
	    } catch (IOException e) {
	      System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
	    } catch (Throwable t) {
	      t.printStackTrace();
	    
	    }
	   
	   return null;
	    
	  }

  
  /*
   * Returns a query term (String) from user via the terminal.
   */
  private static String getInputQuery() throws IOException {

    String inputQuery = "";

    System.out.print("Please enter a search term: ");
    BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
    inputQuery = bReader.readLine();

    if (inputQuery.length() < 1) {
        inputQuery = "Design patterns";
    }
    return inputQuery;
  }

 
  private static void prettyPrint(Iterator<SearchResult> iteratorSearchResults, String query) {

    System.out.println("\n=============================================================");
    System.out.println(
        "   First " + NUMBER_OF_VIDEOS_RETURNED + " videos for search on \"" + query + "\".");
    System.out.println("=============================================================\n");

    if (!iteratorSearchResults.hasNext()) {
      System.out.println(" There aren't any results for your query.");
    }

    while (iteratorSearchResults.hasNext()) {

      SearchResult singleVideo = iteratorSearchResults.next();
      ResourceId rId = singleVideo.getId();

      // Double checks the kind is video.
     
        Thumbnail thumbnail = (Thumbnail) singleVideo.getSnippet().getThumbnails().get("default");

        System.out.println(" Video Id" + rId.getVideoId());
        System.out.println(" Title: " + singleVideo.getSnippet().getTitle());
        System.out.println(" Thumbnail: " + thumbnail.getUrl());
        System.out.println("\n-------------------------------------------------------------\n");
     
    }
  }
    private static List<Info> GetInfo(Iterator<SearchResult> iteratorSearchResults) {

    	
    	List<Info> infos = new ArrayList<Info>();
    	
        if (!iteratorSearchResults.hasNext()) {
          System.out.println(" There aren't any results for your query.");
        }

        while (iteratorSearchResults.hasNext()) {

          SearchResult singleVideo = iteratorSearchResults.next();
          ResourceId rId = singleVideo.getId();

                
            Thumbnail thumbnail = (Thumbnail) singleVideo.getSnippet().getThumbnails().get("default");

            
            Info info = new Info(rId.getVideoId(), singleVideo.getSnippet().getTitle(), thumbnail.getUrl());
            
            
            infos.add(info);
            
            System.out.println(" Video Id" + rId.getVideoId());
            System.out.println(" Title: " + singleVideo.getSnippet().getTitle());
            System.out.println(" Thumbnail: " + thumbnail.getUrl());
            System.out.println("\n-------------------------------------------------------------\n");
         
        }
    
        return infos;
    
  }
  
  
  
}

@Entity("Info")
class Info {
	
	public String cat;
	
	public String title;
	
	public String Id;
	
	public String imgs;

	
	public Info(String title, String id, String imgs){
		
		this.title = title;
		
		this.Id = id;
		
		this.imgs = imgs;
		
		this.cat = "";
	}
	
	
public Info(String title, String id, String imgs, String cat){
	
	this.title = title;
	
	this.Id = id;
	
	this.imgs = imgs;
	
	this.cat = cat;
}
	
public String ToString(){
	return Id + " - " + title;
}

public static List<Info> Filter(List<Info> infos, String name){
	
	List<Info> filtered = new ArrayList<Info>();
	
	for(Info ns: infos){
		
		String sa = ns.cat.trim();
		
		String da = name.trim();
		
		if(sa.equals(da))
			filtered.add(ns);
		
	}
	
	return filtered;
	
}
	
}

@Entity("Categories")
class Categories {
	
	public String cat;
	
	public String Id;
	

	
	public Categories(String cat, String id){
		
	
		
		this.Id = id;
		
		this.cat = cat;
		
	
	}
	

	
public String ToString(){
	return Id + " - " + cat;
}

	
}


