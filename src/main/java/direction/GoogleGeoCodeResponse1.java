package direction;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
//import com.google.maps.model.GeocodedWaypoint;
import com.google.maps.model.GeocodedWaypoint;

import okhttp3.Route;

//@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleGeoCodeResponse1 {


//@JsonProperty ("status")
private String status;


private GeocodedWaypoint[] geocodedWaypoints;


private Route[] routes;
public String getStatus() {
    return status;
}

public void setStatus(String status) {
    this.status = status;
}

public GeocodedWaypoint[] getGeocodedWaypoints() {
    return geocodedWaypoints;
}

public void setGeocodedWaypoints(GeocodedWaypoint[] geocodedWaypoints) {
    this.geocodedWaypoints = geocodedWaypoints;
}

public Route[] getRoutes() {
    return routes;
}

public void setRoutes(Route[] routes) {
    this.routes = routes;
}


@SuppressWarnings("unchecked")
@JsonProperty("geocoded_waypoints")
private void unpackNested(Map<String,Object> geocoded_waypoints) {

    //Map<String,String> owner = (Map<String,String>)geocoded_waypoints.get("geocoder_status");
   // System.out.println("clap  " + owner);

}
}
