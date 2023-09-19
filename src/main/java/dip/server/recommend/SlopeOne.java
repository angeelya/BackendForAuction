package dip.server.recommend;

import dip.server.model.Recommend;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class SlopeOne
{
    Map<String, Map<String, Double>>	mData;
    Map<String, Map<String, Double>>	diffMatrix;
    Map<String, Map<String, Integer>>	freqMatrix;

    static String[]						mAllItems;
    public List<Recommend> beginSlopeOne(List<String> brand, List<ActionBrand> actionBrand,long id_user)
    {
        Map<String, Map<String, Double>> data = new HashMap<>();
        mAllItems =transformationBrand(brand);
        data = transformationData(actionBrand);
        mData = data;
        buildDiffMatrix();
        // I create my predictor engine
       return print(predict(data.get(String.valueOf(id_user))),id_user);
    }

    private static Map<String, Map<String, Double>> transformationData(List<ActionBrand> actionBrand) {
        Map<String, Map<String, Double>> data = new HashMap<>();
        Map<String, Double> user = new HashMap<>();
        for(int k =0; k<actionBrand.size();k++)
        { user.put(String.valueOf(actionBrand.get(k).getId_brand()), actionBrand.get(k).getSum_coefficient());
          data.put(String.valueOf(actionBrand.get(k).getId_user()),user);
        }
        return data;
    }

    private static String[] transformationBrand(List<String> brand) {
        String [] brandStr= new String[brand.size()];
        for(int i=0; i< brand.size();i++)
        {
            brandStr[i]=brand.get(i);
        }
        return  brandStr;
    }


    public SlopeOne()
    {
    }

    /**
     * Based on existing data, and using weights,
     * try to predict all missing ratings.
     * The trick to make this more scalable is to consider
     * only mDiffMatrix entries having a large  (>1) mFreqMatrix
     * entry.
     *
     * It will output the prediction 0 when no prediction is possible.
     */
    public Map<String, Double> predict(Map<String, Double> user)
    {
        HashMap<String, Double> predictions = new HashMap<>();
        HashMap<String, Integer> frequencies = new HashMap<>();
        for (String j : diffMatrix.keySet())
        {
            frequencies.put(j, 0);
            predictions.put(j, 0.0);
        }
        for (String j : user.keySet())
        {
            for (String k : diffMatrix.keySet())
            {
                try
                {
                    Double newval = (diffMatrix.get(k).get(j) + user.get(j)) * freqMatrix.get(k).get(j).intValue();
                    predictions.put(k, predictions.get(k) + newval);
                    frequencies.put(k, frequencies.get(k) + freqMatrix.get(k).get(j).intValue());
                } catch (NullPointerException e)
                {}
            }
        }
        HashMap<String, Double> cleanpredictions = new HashMap<>();
        for (String j : predictions.keySet())
        {
            if (frequencies.get(j) > 0)
            {
                cleanpredictions.put(j, predictions.get(j) / frequencies.get(j).intValue());
            }
        }
        for (String j : user.keySet())
        {
            cleanpredictions.put(j, user.get(j));
        }
        return cleanpredictions;
    }






    public List<Recommend> print(Map<String, Double> user,long user_id)
    {   List<Recommend> recommends = new ArrayList<>();
        for (String j : user.keySet())
        {   Recommend recommend= new Recommend();
            recommend.setId_brand(Long.valueOf(j));
            recommend.setCoefficient(user.get(j).doubleValue());
            recommend.setId_user(user_id);
            recommends.add(recommend);
            System.out.println(user_id);
            System.out.println(j);
            System.out.println(user.get(j).doubleValue());
            System.out.println(user);
        }
        return recommends;


    }

    public void buildDiffMatrix()
    {
        diffMatrix = new HashMap<>();
        freqMatrix = new HashMap<>();
        // first iterate through users
        for (Map<String, Double> user : mData.values())
        {
            // then iterate through user data
            for (Entry<String, Double> entry : user.entrySet())
            {
                String i1 = entry.getKey();
                double r1 = entry.getValue();

                if (!diffMatrix.containsKey(i1))
                {
                    diffMatrix.put(i1, new HashMap<String, Double>());
                    freqMatrix.put(i1, new HashMap<String, Integer>());
                }

                for (Entry<String, Double> entry2 : user.entrySet())
                {
                    String i2 = entry2.getKey();
                    double r2 = entry2.getValue();

                    int cnt = 0;
                    if (freqMatrix.get(i1).containsKey(i2)) cnt = freqMatrix.get(i1).get(i2);
                    double diff = 0.0;
                    if (diffMatrix.get(i1).containsKey(i2)) diff = diffMatrix.get(i1).get(i2);
                    double new_diff = r1 - r2;

                    freqMatrix.get(i1).put(i2, cnt + 1);
                    diffMatrix.get(i1).put(i2, diff + new_diff);
                }
            }
        }
        for (String j : diffMatrix.keySet())
        {
            for (String i : diffMatrix.get(j).keySet())
            {
                Double oldvalue = diffMatrix.get(j).get(i);
                int count = freqMatrix.get(j).get(i).intValue();
                diffMatrix.get(j).put(i, oldvalue / count);
            }
        }
    }
}






