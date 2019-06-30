package com.zillion.tu1;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zillion.tu1.model.Commute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;

public class Misc {

    private String prepareData  ( String source) {

        String text = source.substring(0, source.indexOf(","));
        text = text.replace("{text=","").trim();
        System.out.println("returing " + text);
        return text;

    }

    public Commute getCommuteDetails (  HashMap<String, Object> map ) {

        HashMap<String, Object> result = new HashMap<String, Object>();
        ObjectMapper objectMapper = new ObjectMapper();
        Commute commute = null;
        ArrayList<String> stepsDirection = new ArrayList<String>();

        Predicate <Map.Entry<String, Object>> getHtmlInfo
                = n -> n.getKey().equals("html_instructions");

        ArrayList<Object> routeInfo = (ArrayList<Object>) map.get("routes");
        LinkedHashMap<String, Object> route = (LinkedHashMap<String, Object>) routeInfo.get(0);
        for (String key : route.keySet()) {

            if (key.trim().compareToIgnoreCase("legs") == 0 ) {



                ArrayList legs = (ArrayList) route.get("legs");
                System.out.println("legs are " + legs );
                System.out.println("legs done");
                //extract needed data
                String extractedData = ((LinkedHashMap) legs.get(0)).toString();
                try {
                    ObjectMapper mapperObj = new ObjectMapper();
                    String jsonResp = mapperObj.writeValueAsString(legs.get(0));
                    map = objectMapper.readValue(jsonResp, new TypeReference<Map<String, Object>>() {
                    });
                    for (String key2 : map.keySet()) {
                        System.out.println("key " + key2  + " val " + map.get(key2).getClass());
                        if (key2 == "arrival_time" || key2 == "departure_time" || key2 == "distance" || key2 == "duration")
                            result.put(key2,prepareData(map.get(key2).toString()));
                        else
                        if (key2 == "end_address" || key2 == "start_address")
                            result.put(key2,(String) map.get(key2));
                        else
                            if (key2 == "steps") {
                                ArrayList <HashMap <String, Object>> directionInfo = (ArrayList ) map.get("steps");
                                directionInfo.stream()
                                        .flatMap(s -> s.entrySet().stream())
                                        .filter(getHtmlInfo)
                                        .forEach(x -> stepsDirection.add(x.getValue().toString()));

                                result.put("direction", stepsDirection);
                            }
                    }
                    //System.out.println(jsonResp);

                    commute = new Commute(result);


                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    return commute;
    }
}

