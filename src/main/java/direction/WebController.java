package direction;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@Controller
public class WebController {

	final String LOGINNAMES = "olutundebayoolatope";
	final String TRAVEL_MODE = "transit";
	
    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }
    
    @GetMapping("/login")
    public String greeting(@RequestParam(name="username", required=true) String username,@RequestParam(name="password", required=true) String password, Model model) {
    	
    	model.addAttribute("username", username);
    	model.addAttribute("password", password);
    	if (LOGINNAMES.indexOf(username) > -1 ) // && (password.indexOf("olutundebayoolatope") > 0 )
    		return "finddirection";
    	else 
    		return "loginerror";
    }
    
    @GetMapping("/direction")
    public String direction (@RequestParam(name="start", required=true) String start,@RequestParam(name="finish", required=true) String finish, Model model) {
    	
    	model.addAttribute("start", start);
    	model.addAttribute("finish", finish);
    	//if (LOGINNAMES.indexOf(username) > -1 ) // && (password.indexOf("olutundebayoolatope") > 0 )
    	try {
    		String result = getDirection ("no-key", start, finish);
    		ObjectMapper objectMapper = new ObjectMapper();
    		JsonNode root = objectMapper.readTree(result);
    		
            JsonNode contactNode = root.path("routes");
            JsonNode legs = contactNode.path("legs");
            if (contactNode.isArray()) {
                System.out.println("Is this node an Array? " + contactNode.isArray() + " and " + legs.iterator());
  

                for (JsonNode node : legs) {
                    String start_loc = node.path("start_address").asText();
                    String end_loc = node.path("end_address").asText();
                    System.out.println("start and end loc : " + start_loc  + " end " + end_loc);

                }
            }

    		model.addAttribute("result", result);
    	}
    	catch (Exception ex) {
    		ex.printStackTrace();
    	}
    	
    	return "result";
    	//else 
    	//	return "loginerror";
    }
    
    private String getDirection (String apiKey, String start, String finish) throws Exception {
    	//String contents = new String(Files.readAllBytes(Paths.get("direction.json")));
    	//System.out.println("Contents (Java 7) : " + contents);


    	//Read more: https://javarevisited.blogspot.com/2015/09/how-to-read-file-into-string-in-java-7.html#ixzz5qIOfAhz7

    	URL url = new URL("https://maps.googleapis.com/maps/api/directions/json?origin=DA162BS&destination=E162RX&key=AIzaSyDVrnnZiP7p2-QmezKUcckDOpS-F0yqRGM&mode=transit&travel_mode=transit");
    	HttpURLConnection con = (HttpURLConnection) url.openConnection();
    	con.setRequestMethod("GET");
    	String content = "";
    	StringBuffer jsonResults = new StringBuffer();
    	
    	   BufferedReader in = new BufferedReader( new InputStreamReader(con.getInputStream()));
    			String inputLine;
    			while ((inputLine = in.readLine()) != null) {
    			    content +=inputLine;
    			    System.out.println("hey");
    			}
    			//System.out.println (content.toString());
    		     int read;
    	            char[] buff = new char[1024];
    	            while ((read = in.read(buff)) != -1) {
    	                jsonResults.append(buff, 0, read);
    	            }
    			in.close();
    			con.disconnect(); 
    			
    			return content;
    			
    	

    }


}