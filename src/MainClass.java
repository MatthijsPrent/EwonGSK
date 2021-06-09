import com.ewon.ewonitf.DefaultEventHandler;

public class MainClass {

	public static void main(String[] args) throws Exception {

		// Create a new MQTT Thread and start it
		// This will start the continuous loop
		MQTT_Thread MQTT_T = new MQTT_Thread();
		MQTT_T.start();
		
		DefaultEventHandler.runEventManager();

	}

}
