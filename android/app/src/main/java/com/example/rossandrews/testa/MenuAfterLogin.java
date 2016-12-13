package com.example.rossandrews.testa;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

import static com.example.rossandrews.testa.R.id.textReceivedEmail;
import static com.example.rossandrews.testa.R.id.textReceivedPassword;
import static com.example.rossandrews.testa.R.id.textReceivedUsername;

/**
 * Created by Ross Andrews on 11/9/2016.
 */

public class MenuAfterLogin extends Activity{

    private EditText ReceivedUsername;
    private EditText ReceivedPassword;
    private EditText ReceivedEmail;

    public static ArrayList<Client> clients = new ArrayList<Client>();

    private Button saveChanges;
    private Button refresh;
    private Button sendEmail;

    public static ListView listView;

    public void sendEmail(View view){
        String string = "\n";

        string += clients.toString();

        Toast.makeText(MenuAfterLogin.this, clients.toString(), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"));
        String[] mail = {"ruspauladrian.1994@gmail.com"};
        intent.putExtra(Intent.EXTRA_EMAIL, mail);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Mobile App");
        intent.putExtra(Intent.EXTRA_TEXT, "Client List:\n" + string);
        intent.setType("message/rfc822");

        Intent chooser = Intent.createChooser(intent, "Send email");
        try {
            startActivity(chooser);
        }catch (android.content.ActivityNotFoundException e){
            Toast.makeText(MenuAfterLogin.this, "Exception?", Toast.LENGTH_SHORT).show();
        }
    }

    private static void createFile(View v, Context ctx) throws JSONException, IOException {
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

        FileOutputStream fos = ctx.openFileOutput("clientsFile", MODE_PRIVATE);
        fos.write(text.getBytes());
        fos.close();
    }

    private static void readFile(View v, Context ctx) throws IOException, JSONException {
        FileInputStream fis = new FileInputStream("clientsFile");
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

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_after_login);

        String username = getIntent().getStringExtra("Username");
        String password = getIntent().getStringExtra("Password");

        int i;
        for(i=0; i<10; i++){
            Client client = new Client("username" + i, "password" + i, "email" + i);
            clients.add(client);
        }
        clients.add(new Client(username + i, password + i, "email" + i));

        this.ReceivedUsername = (EditText) findViewById(textReceivedUsername);
        this.ReceivedPassword = (EditText) findViewById(textReceivedPassword);
        this.ReceivedEmail = (EditText) findViewById(textReceivedEmail);

        saveChanges = (Button) findViewById(R.id.buttonSaveChanges);
        refresh = (Button) findViewById(R.id.buttonRefresh);
        sendEmail = (Button) findViewById(R.id.buttonSendEmail);
        listView = (ListView) findViewById(R.id.ClientsList);

        refresh.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (clients.isEmpty()) {
                    Toast.makeText(getBaseContext(), "List is empty", Toast.LENGTH_LONG).show();
                }
                else {
                    ArrayAdapter<Client> adapter = new ArrayAdapter<Client>(MenuAfterLogin.this, android.R.layout.simple_list_item_1, clients);
                    listView.setAdapter(adapter);
                }
            }
        });

        sendEmail.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                sendEmail(v);
            }
        });

        saveChanges.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String username = ReceivedUsername.getText().toString();
                String password = ReceivedPassword.getText().toString();
                String email = ReceivedEmail.getText().toString();

                Boolean found = false;
                Client client = new Client(username, password, email);
                for(Iterator<Client> i = clients.iterator(); i.hasNext();){
                    Client client1 = i.next();
                    if(client1.getUsername().equals(client.getUsername()) &&
                            client1.getPassword().equals(client.getPassword()) &&
                            client1.getEmail().equals(client.getEmail())){
                        found = true;
                    }
                }
                if(found){
                    Toast.makeText(getBaseContext(), "Client is already in the database", Toast.LENGTH_LONG).show();
                }
                else if(username == null || username.equals("") ||
                        password == null || password.equals("") ||
                        email == null || email.equals("")){
                    Toast.makeText(getBaseContext(), "You need to fill in everything", Toast.LENGTH_LONG).show();
                }
                else {
                    clients.add(client);
                    ArrayAdapter<Client> adapter = new ArrayAdapter<Client>(MenuAfterLogin.this, android.R.layout.simple_list_item_1, clients);
                    listView.setAdapter(adapter);
                    ((EditText) findViewById(R.id.textReceivedUsername)).setText("");
                    ((EditText) findViewById(R.id.textReceivedPassword)).setText("");
                    ((EditText) findViewById(R.id.textReceivedEmail)).setText("");
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Client client = (Client) (listView.getItemAtPosition(position));

                Intent intent = new Intent(view.getContext(), MyListActivity.class);

                intent.putExtra("username", client.getUsername());
                intent.putExtra("password", client.getPassword());
                intent.putExtra("email", client.getEmail());
                intent.putExtra("position", position);

                startActivityForResult(intent, 0);
            }
        });

/*
        listView.setOnClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Client client = (Client) (listView.getItemAtPosition(position));

                Intent intent = new Intent(view.getContext(), MyListActivity.class);

                intent.putExtra("username", client.getUsername());
                intent.putExtra("password", client.getPassword());
                intent.putExtra("email", client.getEmail());
                intent.putExtra("position", position);

                startActivityForResult(intent, 0);
            }
        });
*/
    }


}
