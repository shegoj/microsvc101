package com.zillion.tu1.webstuff;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zillion.tu1.Misc;
import com.zillion.tu1.model.Commute;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.beans.factory.annotation.*;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;


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

    @Value("${api_key}")
    private String api_key;
    @GetMapping("/direction")
    public String direction (@RequestParam(name="start", required=true) String start,
                             @RequestParam(name="finish", required=true) String finish,
                             @RequestParam(name="mode", required=true) String mode, Model model) {
        model.addAttribute("start", start);
        model.addAttribute("finish", finish);

        //if (LOGINNAMES.indexOf(username) > -1 ) // && (password.indexOf("olutundebayoolatope") > 0 )
        try {

            ObjectMapper objectMapper = new ObjectMapper();
            URL url = new URL("https://maps.googleapis.com/maps/api/directions/json?origin="+ start + "&destination=" +
                    finish + "&key=" + api_key + mode +"&travel_mode=transit%22");
            HashMap<String, Object> map = objectMapper.readValue(url, new TypeReference<Map<String, Object>>() {});
            Misc misc = new Misc();
            Commute commute = misc.getCommuteDetails(map);

            model.addAttribute("result", "result");
            //model.addAttribute("mode", mode);

            model.addAttribute("duration", commute.getDuration());
            model.addAttribute("startaddress", commute.getStartAddress());
            model.addAttribute("endaddress", commute.getEndAddress());
            model.addAttribute("departuretime", commute.getDepartureTime());
            model.addAttribute("arrivaltime", commute.getArrivalTime());
            model.addAttribute("distance", commute.getTotalDistance());

            // mode quick fix
            if (mode.compareToIgnoreCase("bicycle") == 0)
                mode = "https://www.sefiles.net/merchant/1685/images/site/rental-slimC.png?t=1524095545068";

            if (mode.compareToIgnoreCase("transit") == 0)
                mode = "https://www.watsoneng.com/wp-content/uploads/2013/09/rapid_transit.jpg";

            if (mode.compareToIgnoreCase("driving") == 0 )
                mode = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQr_Us03VlIfRTIR9JQKLQ7gohfHNKFftaceD0lIboQXIbuC93p";

            if (mode.compareToIgnoreCase("walking") == 0)
                mode = "https://imagesvc.meredithcorp.io/v3/mm/image?url=https%3A%2F%2Fcdn-image.realsimple.com%2Fsites%2Fdefault%2Ffiles%2Fstyles%2Frs_medium_image%2Fpublic%2Fimage%2Fimages%2F1402%2Fillo-right-way_300.jpg%3Fitok%3DfSRJGaHH&q=85";

            model.addAttribute("mode", mode);

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return "result";
    }
}
