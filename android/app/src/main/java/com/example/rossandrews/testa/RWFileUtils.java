package com.example.rossandrews.testa;

import android.content.Context;
import android.view.View;

import com.example.rossandrews.testa.database.Client;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Ross Andrews on 12/22/2016.
 */

public class RWFileUtils {
    /*
    public String fileName = "clientsFile.txt";

    public RWFileUtils(){

    }

    public void createFile(View v, Context ctx, ArrayList<Client> clients) throws JSONException, IOException {
        JSONArray data = new JSONArray();
        JSONObject client;

        for(int i=0; i<clients.size(); i++){
            client = new JSONObject();
            client.put("username", clients.get(i).getUsername());
            client.put("password", clients.get(i).getPassword());
            client.put("email", clients.get(i).getEmail());
            data.put(client);
        }

        String text = data.toString();

        FileOutputStream fos = ctx.openFileOutput("clientsFile.txt", MODE_PRIVATE);
        fos.write(text.getBytes());
        fos.close();
    }

    public void readFile(View v, Context ctx, ArrayList<Client> clients) throws IOException, JSONException {
        FileInputStream fis = new FileInputStream("clientsFile.txt");
        BufferedInputStream bis = new BufferedInputStream(fis);
        StringBuffer b = new StringBuffer();

        while(bis.available()!=0){
            char c = (char) bis.read();
            b.append(c);
        }

        bis.close();
        fis.close();

        JSONArray data = new JSONArray(b.toString());

        for(int i=0; i<data.length(); i++){
            String username = data.getJSONObject(i).getString("username");
            String password = data.getJSONObject(i).getString("password");
            String email = data.getJSONObject(i).getString("email");

            Client c = new Client(username, password, email);
            clients.add(c);
        }
    }
    */
}
