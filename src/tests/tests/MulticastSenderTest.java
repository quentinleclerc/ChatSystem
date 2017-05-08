package tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.InetAddress;

import network.MulticastSender;

import org.junit.Test;

import communication.User;

public class MulticastSenderTest {

	@Test
	public void test() {
		MulticastSender mS = null;
		try {
			mS = new MulticastSender("225.1.2.3", 6789, 5000, new User("Jean",InetAddress.getByName("225.1.2.3"), 5676, User.typeConnect.CONNECTED));
		} catch (IOException e) {
			e.printStackTrace();
		}
		mS.run();
		fail("Not yet implemented");
	}

}
