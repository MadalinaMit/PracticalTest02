package practicaltest02.eim.systems.cs.pub.ro.practicaltest02;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PracticalTest02MainActivity extends AppCompatActivity {

    private EditText server_port;
    private Button server_connect;
    private EditText client_address;
    private EditText client_port;
    private EditText url;
    private EditText client_city;
    private Button client_connect;
    private TextView client_result_view;
    private EditText client_comm;

    private ServerThread serverThread = null;
    private ClientThread clientThread = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test02_main);


        server_port = (EditText) findViewById(R.id.server_port_edit_text);
        server_connect = (Button) findViewById(R.id.connect_server_button);

        client_address = (EditText) findViewById(R.id.address_edit_text);
        client_port = (EditText) findViewById(R.id.port_edit_text);

        client_connect = (Button) findViewById(R.id.get_result_button);
        url = (EditText) findViewById(R.id.url_edit_text);

        client_connect.setOnClickListener(new ButtonClickListener());
        server_connect.setOnClickListener(new ButtonClickListener());
        client_result_view = (TextView) findViewById(R.id.results_text_view);
    }

    class ButtonClickListener implements View.OnClickListener {

        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.connect_server_button:
                    if (isPortValid()) {
                        serverThread = new ServerThread(Integer.parseInt(server_port.getText().toString()));
                        serverThread.start();
                    } else {
                        Toast.makeText(PracticalTest02MainActivity.this, "You cannot let port empty!",
                                Toast.LENGTH_LONG).show();
                    }
                    break;

                case R.id.get_result_button:


                    if (isQueryValid()) {
                        String clientAddress = client_address.getText().toString();
                        String clientPort = client_port.getText().toString();


                        clientThread = new ClientThread(
                                clientAddress, Integer.parseInt(clientPort), url.getText().toString(), client_result_view
                        );
                        clientThread.start();


                    } else {
                        Toast.makeText(PracticalTest02MainActivity.this,
                                "You cannot let query with an empty value!",
                                Toast.LENGTH_LONG).show();


                    }
                    break;

                default:
                    break;
            }
        }

    }

    private boolean isPortValid() {
        return server_port.getText() != null && !server_port.getText().toString().isEmpty();
    }

    private boolean isQueryValid() {
        return client_address.getText() != null && !client_address.getText().toString().isEmpty();
    }
}
