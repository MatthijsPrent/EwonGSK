import com.ewon.ewonitf.MqttClient;
import com.ewon.ewonitf.MqttMessage;

public class MyMQTTClient extends MqttClient {

	public MyMQTTClient(String MqttId, String Host) throws Exception {
		super(MqttId, Host);
	}

	// Can be used to subscribe to messages but this is not used in the project.
	public void callMqttEvent(int event) {
		// When a message is received through a subscribed topic,
		// the function callMqttEvent is called
		try {
			if (event == 1) { 
				System.out.println("MyMQTTClient.java: event was 1");
				MqttMessage msg;
				return;
			}
				
				// Incoming MQTT messageShareTagsOverTalk2M
				MqttMessage msg;
 
				msg = readMessage();
				if (msg != null) {
					String Message = new String(msg.getPayload(), "UTF-8");
					System.out.println("Incoming message : " + Message);
					msg.close();
				}
		} catch (Exception e) {
			System.out.println("Error callMqttEvent: " + e.toString());
		}
	}
}
