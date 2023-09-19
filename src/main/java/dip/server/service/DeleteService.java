package dip.server.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dip.server.recommend.ActionService;
import dip.server.repository.FavoriteRepository;
import dip.server.repository.QueryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class DeleteService {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    @Autowired
    FavoriteRepository favoriteRepository;
    @Autowired
    ActionService actionService;
    @Autowired
    QueryRepository queryRepository;
    public String deleteFavorite(Map<String, String> map) {
        String message="true";
        try{
            actionService.deleteActionFavorite(favoriteRepository.getFavorite(Long.valueOf(map.get("id_favorite"))).getId_user(),favoriteRepository.getFavorite(Long.valueOf(map.get("id_favorite"))).getId_car());
            favoriteRepository.deleteFavorite(Long.valueOf(map.get("id_favorite")));

        }
        catch (Exception e)
        {
            message="false";
         log.info(e.getMessage());
        }
        Map<String,String> res = new HashMap<>();
        res.put("message",message);
        return gson.toJson(res);
    }

    public String deleteQuery(Map<String, String> map) {
     String message ="true";
     try {
         queryRepository.deleteQuery(Long.valueOf(map.get("id_query")));
     }
     catch (Exception e)
     {
         message="false";
     }
        Map<String,String> res = new HashMap<>();
        res.put("message",message);
        return gson.toJson(res);
    }
}
