package com.example.dhawbiti;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;

public class MainActivity extends AppCompatActivity {

    private static final String BROKER_URL = "tcp://broker.hivemq.com:1883";
    private static final String CLIENT_ID = "AndroidClient";
    private static final String TOPIC = "dhaw/output";

    private MqttClient mqttClient;
    private Button lightsOnButton;
    private Button lightsOffButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lightsOnButton = findViewById(R.id.lightsOnButton);
        lightsOffButton = findViewById(R.id.lightsOffButton);

        connectToBroker();

        lightsOnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publishMessage("on");
            }
        });

        lightsOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publishMessage("off");
            }
        });
    }

    private void connectToBroker() {
        try {
            Log.i("MQTT", "Connecting to broker: " + BROKER_URL);

            // Set up the persistence layer
            MemoryPersistence persistence = new MemoryPersistence();

            // Initialize the MQTT client
            mqttClient = new MqttClient(BROKER_URL, CLIENT_ID, persistence);

            // Set up the connection options
            MqttConnectOptions connectOptions = new MqttConnectOptions();
            connectOptions.setCleanSession(true);

            // Set up callback for extended features
            mqttClient.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    handleConnectionLost();
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) {
                    handleMessageArrived(new String(message.getPayload()));
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    // Not used for subscriptions
                }
            });

            // Connect to the broker
            mqttClient.connect(connectOptions);
            Log.i("MQTT", "Connected to broker");

            subscribeToTopic(TOPIC);

        } catch (MqttException e) {
            handleConnectionFailure();
            e.printStackTrace();
            Log.e("MQTT", "Error connecting to broker: " + e.getMessage());
        }
    }


    private void subscribeToTopic(String topic) {
        try {
            mqttClient.subscribe(topic);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private void publishMessage(String message) {
        try {
            MqttMessage mqttMessage = new MqttMessage(message.getBytes());
            mqttClient.publish(TOPIC, mqttMessage);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private void handleConnectionLost() {
        runOnUiThread(() ->
                Toast.makeText(MainActivity.this, "Connection Lost", Toast.LENGTH_SHORT).show());
    }

    private void handleConnectionFailure() {
        runOnUiThread(() ->
                Toast.makeText(MainActivity.this, "Connection Failed", Toast.LENGTH_SHORT).show());
    }

    private void handleMessageArrived(String message) {
        runOnUiThread(() ->
                Toast.makeText(MainActivity.this, "Message arrived: " + message, Toast.LENGTH_SHORT).show());
    }

    @Override
    protected void onDestroy() {
        try {
            mqttClient.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }
}