package bku.iot.vn.iot_dashboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Activity_1 extends AppCompatActivity {

    MQTTHelper mqttHelper;

    TextView txtTemp, txtHumi;
    ImageView imTemp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1); //lay xml bo vo activity tao giao dien, trc do se bi NULL


        txtTemp =findViewById(R.id.txtTemperature);
        txtHumi= findViewById(R.id.txtHumidity);
        imTemp =findViewById(R.id.imageTemperature);

        //txtTemp.setText("50°C");
        //txtHumi.setText("60%");

        startMQTT();
    }
    private void startMQTT(){
        mqttHelper = new MQTTHelper(getApplicationContext(),"28122000");
        mqttHelper.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                Log.d("mqtt","Connection is successfull ");
            }

            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Log.d("mqtt","Mess Received :  "+message.toString());
                if(topic.contains("bbc-temp"))
                {
                    int temp = Integer.parseInt(message.toString());
                    txtTemp.setText(message.toString()+"°C");
                    if(temp > 30) imTemp.setBackground(getDrawable(R.drawable.ic_temper_c));
                    else  imTemp.setBackground(getDrawable(R.drawable.ic_temper_h));
                }

                if(topic.contains("bbc-humid"))
                    txtHumi.setText(message.toString()+"%");
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
    }
}
