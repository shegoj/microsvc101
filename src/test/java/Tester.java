import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zillion.tu1.Misc;
import com.zillion.tu1.model.Commute;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Tester {

    Misc misc = new Misc();
    Commute myCommute = null;
    HashMap<String, String> data = null;

    @Before
    public  void setUp () throws Exception{

        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.readValue(new String(Files.readAllBytes(Paths.get("direction.json"))), new TypeReference<Map<String, Object>>() {
        });

        myCommute =  misc.getCommuteDetails(map);
        data =  new HashMap<String, String>();
    }

    private String prepareData  ( String source) {

        String text = source.substring(0, source.indexOf(","));
        text = text.replace("{text=","").trim();
        System.out.println("returning " + text);
        return text;

    }

    @Test
    public void checkData () throws Exception {

        Assert.assertEquals("7:22pm",prepareData
                ("{text=7:22pm, time_zone=Europe/London, value=1560018176}"));
    }


    @Test
    public void testMode() throws Exception {
        /// myCommute = new Commute();
        Assert.assertEquals(myCommute.getMode(), "100");
    }

    @Test
    public void testArrivalTime() throws Exception {
        // myCommute = new Commute();
        Assert.assertEquals(myCommute.getArrivalTime(), "7:22pm");
    }

    @Test
    public void testDepartureTime() throws Exception {
        //Commute myCommute = new Commute();
        Assert.assertEquals(myCommute.getDepartureTime(), "6:31pm");
    }

    @Test
    public void testTotalDistance() throws Exception {
        //Commute myCommute = new Commute();
        Assert.assertEquals(myCommute.getTotalDistance(), "10.6 km");
    }
    @Test
    public void testDuration() throws Exception {
        //Commute myCommute = new Commute();
        Assert.assertEquals(myCommute.getDuration(), "51 mins");
    }
    @Test
    public void testStartAddress() throws Exception {
        //Commute myCommute = new Commute();
        Assert.assertEquals(myCommute.getEndAddress(), "Fishguard Way, Royal Docks, London E16 2RX, UK");
    }
    @Test
    public void testEndAddress2() throws Exception {
        //Commute myCommute = new Commute();
        Assert.assertEquals(myCommute.getStartAddress(), "Groombridge Cl, Welling DA16 2BS, UK");
    }

    @Test
    public void testEndAddress() throws Exception {
        Assert.assertEquals(myCommute.getDirection().size(), 5);
    }
}
