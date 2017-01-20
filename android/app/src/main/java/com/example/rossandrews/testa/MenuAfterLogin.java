package com.example.rossandrews.testa;

import android.app.Activity;
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

import com.example.rossandrews.testa.database.Client;

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



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_after_login);

        String username = getIntent().getStringExtra("Username");
        String password = getIntent().getStringExtra("Password");
/*
        int i;
        for(i=0; i<10; i++){
            Client client = new Client("username" + i, "password" + i, "email" + i);
            clients.add(client);
        }
        clients.add(new Client(username + i, password + i, "email" + i));
*/
        this.ReceivedUsername = (EditText) findViewById(textReceivedUsername);
        this.ReceivedPassword = (EditText) findViewById(textReceivedPassword);
        this.ReceivedEmail = (EditText) findViewById(textReceivedEmail);

        saveChanges = (Button) findViewById(R.id.buttonSaveChanges);
        refresh = (Button) findViewById(R.id.buttonRefresh);
        sendEmail = (Button) findViewById(R.id.buttonSendEmail);
        listView = (ListView) findViewById(R.id.ClientsList);

        refresh.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try{
                    RWFileUtils rw = new RWFileUtils();
//                    rw.readFile(v, MenuAfterLogin.this, clients);
                    //readFile(v, MenuAfterLogin.this);
                }catch (Exception e){
                    e.printStackTrace();
                }

                if (clients.isEmpty()) {
                    Toast.makeText(getBaseContext(), "List is empty", Toast.LENGTH_LONG).show();
                }
                else {
                    ArrayAdapter<Client> adapter = new ArrayAdapter<Client>(MenuAfterLogin.this, android.R.layout.simple_list_item_1, clients);
                    listView.setAdapter(adapter);
                }
            }
        });
/*
        saveChanges.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String username = ReceivedUsername.getText().toString();
                String password = ReceivedPassword.getText().toString();
                String email = ReceivedEmail.getText().toString();

                Client client = new Client(username, password, email);
                Boolean found = false;
                for(Iterator<Client> i = clients.iterator(); i.hasNext();){
                    Client client1 = i.next();
                    if(client1.getUsername().equals(client.getUsername()) &&
                            client1.getPassword().equals(client.getPassword()) &&
                            client1.getEmail().equals(client.getEmail())){
                        found = true;
                    }
                }

                if(found == true){
                    Toast.makeText(getBaseContext(), "Client is already in the database", Toast.LENGTH_LONG).show();
                }
                else if(username == null || username.equals("") ||
                        password == null || password.equals("") ||
                        email == null || email.equals("")){
                    Toast.makeText(getBaseContext(), "You need to fill in everything", Toast.LENGTH_LONG).show();
                }
                else{
                    clients.add(client);
                    ArrayAdapter<Client> adapter = new ArrayAdapter<Client>(MenuAfterLogin.this, android.R.layout.simple_list_item_1, clients);
                    listView.setAdapter(adapter);
                    ((EditText) findViewById(R.id.textReceivedUsername)).setText("");
                    ((EditText) findViewById(R.id.textReceivedPassword)).setText("");
                    ((EditText) findViewById(R.id.textReceivedEmail)).setText("");

                    try{
                        RWFileUtils rw = new RWFileUtils();
  //                      rw.createFile(v, MenuAfterLogin.this, clients);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }

            }
        });
*/
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
        */

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
