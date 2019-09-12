package com.cinatic;

import java.util.concurrent.CountDownLatch;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;

import com.cinatic.log.Log;
import com.cinatic.object.Device;
import com.cinatic.object.MqttObject;

/**
 * synchronous MQTT client
 */
public class MQTTHelper implements MqttCallback {

	MqttClient			myClient;
	MqttConnectOptions	connOpt;
	public String		output;

	String		BROKER_URL	= "tcp://mqtt-iot.hubble.in:1883";
	MqttObject	mqttObj;
	String		MQTT_TOPIC;
	int			pubQoS		= 0;
	int			subQoS		= 0;

	private static volatile CountDownLatch	waitForServerMessageLatch;
	private WaitThread						waitThread;

	/**
	 * @param broker: url string
	 * @param o:      mqtt object which contains username, password and subscribed
	 *                topic.
	 * @param device: device object which must contain mqtt topic of the device
	 */
	public MQTTHelper(String broker, MqttObject o, Device device) throws Exception {

		this.BROKER_URL	= broker;
		this.mqttObj	= o;
		this.MQTT_TOPIC	= device.getMqtt_topic();
		connOpt			= new MqttConnectOptions();

		connOpt.setCleanSession(true);
		connOpt.setKeepAliveInterval(30);
		connOpt.setUserName(mqttObj.getAccessKey());
		connOpt.setPassword(mqttObj.getSecretKey().toCharArray());

		MqttDefaultFilePersistence mqttTmpFolder = new MqttDefaultFilePersistence("test-output/mqttTmp");
		myClient = new MqttClient(BROKER_URL, mqttObj.getClientId(),mqttTmpFolder);
		myClient.setCallback(this);
		myClient.connect(connOpt);
		myClient.subscribe(mqttObj.getSubscribeTopic(), subQoS);

		Log.info("Connected to ", BROKER_URL);
		Log.info("Mqtt access key: ", mqttObj.getAccessKey());
		Log.info("Mqtt secret key: ", mqttObj.getSecretKey());
		Log.info("Client topic: ", mqttObj.getSubscribeTopic());
		Log.info("Device topic: ", MQTT_TOPIC);
		Log.info("Device UUID: ", device.getDevice_id());
	}

	/**
	 * @param command (message) to publish to server
	 *                this method will wait for messageArrived to be completed, only
	 *                then it
	 *                will resume the thread
	 * @throws Exception
	 */
	public void sendCommand(String command) throws Exception {

		output = "";
		MqttTopic	topic	= myClient.getTopic(MQTT_TOPIC);
		long		time	= System.currentTimeMillis();
		String		pubMsg	= "2app_topic_sub=" + mqttObj.getSubscribeTopic() + "&time=" + time
				+ "&req=" + command;
		Log.debug(pubMsg);
		MqttMessage message = new MqttMessage(pubMsg.getBytes());
		message.setQos(pubQoS);
		message.setRetained(false);

		Log.debug("Publishing to topic \"" + topic + "\" qos " + pubQoS);
		MqttDeliveryToken token = new MqttDeliveryToken();
		token = topic.publish(message);
		token.waitForCompletion();
		waitForServerMessageLatch = new CountDownLatch(1);
		waitForServerMessageLatch.await();
	}

	@Override
	public void connectionLost(Throwable cause) {

		Log.debug("Connection lost! ", cause.getMessage());
		try {
			waitThread.stopWaiting();
			throw new Exception(cause);
		} catch (Throwable t) {}
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {

		output = new String(message.getPayload());
		Log.debug("Message arrived: " + output);
		waitForServerMessageLatch.countDown();
		waitThread.stopWaiting();
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {

		Log.debug("Delivery Complete!");
		waitThread = new WaitThread();
		waitThread.start();

	}

	public void disconnect() {

		try {
			myClient.disconnect();
			Log.debug("Disconnect mqtt client");
			waitThread.stopWaiting();
		} catch (Exception e) {}
	}

	public void setMaxWaitTime(int time) {

		waitThread.setMaxWaitTime(time);
	}

	private class WaitThread extends Thread {

		private int	MAX_WAIT	= 30;
		int			cont		= MAX_WAIT;

		@Override
		public void run() {

			cont = MAX_WAIT;
			while (cont > 0) {
				try {

					Log.fatal("Wait for response from server: ", cont + "");
					Thread.sleep(1000);
				} catch (Exception e) {}
				cont--;
			}
			waitForServerMessageLatch.countDown();
		}

		public void stopWaiting() {

			cont = 0;
			interrupt();
		}

		public void setMaxWaitTime(int time) {

			MAX_WAIT	= time;
			cont		= MAX_WAIT;
		}
	}
}
