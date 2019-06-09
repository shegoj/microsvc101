package direction;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getHello() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/test").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Greetings from Spring Boot!")));
    }
    
    @Test
    public void getStartAddress () throws Exception{
    	String contents = new String(Files.readAllBytes(Paths.get("direction.json")));
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode root = objectMapper.readTree(contents);
		
        JsonNode node = root.path("routes");
        
        // lets find out what fields it has
        Iterator<String> fieldNames = node.fieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            System.out.println(" field " + fieldName);// prints title, message, errors,
                                            // total,
                                            // total_pages, page, limit, dataset
        }
        Iterator<String> datasetElementFields = node.fieldNames();
        Iterator<JsonNode> albums = node.path("legs").iterator();
        while (albums.hasNext()) {
            String datasetElementField = albums.next().textValue();
            System.out.println(datasetElementField); 
        }
      //  for (JsonNode node : contactNode) {
            String type = root.findPath("legs").path("arrival_time").path("text").asText();
            String ref = node.path("end_address").path("start_address").asText();
            System.out.println("type : " + type);
            System.out.println("ref : " + ref);

     //   }
      //  System.out.println("yes " +  contactNode.get(0).get);       //JsonNode legs = contactNode.path("legs");
        	
    }
    
}
