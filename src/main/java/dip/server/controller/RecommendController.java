package dip.server.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dip.server.recommend.CollaborativeFilteringService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recommend")
public class RecommendController {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

}
