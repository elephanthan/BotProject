package com.worksmobile.android.botproject.api;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * Created by user on 2018. 5. 9..
 */

public class MqttRepository {

    final String LOG_TAG_MQTT = this.getClass().getSimpleName();

    public final static String topic = "/chatrooms/";
    public final static int qos = 2;
    private final static String broker = "tcp://10.105.185.60";

    public static MqttClient getMqttClient(String clientId){
        MqttClient mqttClient = null;
        MemoryPersistence persistence = new MemoryPersistence();
        try {
            mqttClient = new MqttClient(broker, clientId, persistence);
        } catch (MqttException e) {
            e.printStackTrace();
        }
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);

        return mqttClient;
    }


}
