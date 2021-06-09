import com.ewon.ewonitf.MqttMessage;
import com.ewon.ewonitf.SysControlBlock;

public class MQTT_Thread implements Runnable {

	private Thread thread = null;

	public void start() {
		// This will start the main loop (run()).
		if (thread == null) {
			thread = new Thread(this);
			System.out.println("MQTT_Thread.start()");
			thread.start();
		}

	}

	public void run() {
		try {

			com.ewon.ewonitf.SysControlBlock Sys = new SysControlBlock(SysControlBlock.INF);
			
			MyMQTTClient Mqtt_c = new MyMQTTClient(Sys.getItem("SerNum"), "192.168.1.102");
			
			// Make a not secured connection.
			Mqtt_c.setOption("port", "1883");
			Mqtt_c.setOption("log", "1");
			Mqtt_c.setOption("username", "user");
			Mqtt_c.setOption("password", "mosquittoPassword");
			Mqtt_c.connect();
			Thread.sleep(1000);
			
			// variables to store the old values. 
			// The values are send report by exception.
			int oldstate = 0;
			int oldPackets = 0;
			int oldBlister = 0;
			int value = 0;
			
			while (true) {
				// Read the state, if changed -> publish.
				value = com.ewon.ewonitf.IOManager.readTagAsInt("State");
				if(value != oldstate) {
					Mqtt_c.publish(new MqttMessage("/Brink/WTW/31553/States", String.valueOf(value)), 0, false);
					oldstate = value;
				}
				
				// read the number of produced packets, if changed -> publish.
				value = com.ewon.ewonitf.IOManager.readTagAsInt("PacketCount");
				if(oldPackets != value) {
					Mqtt_c.publish(new MqttMessage("/Brink/WTW/31553/count/packets", String.valueOf(value)), 0, false);
					oldPackets = value;
				}
				
				
				value = com.ewon.ewonitf.IOManager.readTagAsInt("BlisterCount");
				if(oldBlister != value) {
					Mqtt_c.publish(new MqttMessage("/Brink/WTW/31553/count/blisters", String.valueOf(value)), 0, false);
					oldBlister = value;
				}
				
			}
		} catch (Exception ex) {
			System.out.println("Error!");
		}

	}

}
