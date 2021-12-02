package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.dao.MapDaoJdbc;
import com.codecool.dungeoncrawl.model.MapModel;

import java.io.FileWriter;
import java.io.IOException;

public class TxtCreator {

    public static void textCreator(MapModel mapModel){
        try{
            FileWriter myTxt = new FileWriter("src/main/resources/loadedMap.txt");
            String loadedMap = mapModel.getMap();
            myTxt.write(loadedMap);
            myTxt.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
