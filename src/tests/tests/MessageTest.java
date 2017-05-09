package tests;

import static org.junit.Assert.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.Test;
import communication.Message;
import communication.User;

public class MessageTest {

	@Test
	public void testGetSender() {
		User usr = mock(User.class);
		Message msg = new Message("Salut", usr);
		assertTrue("Sender : usr get by mock", msg.getSender().equals(usr));
	}
	
	@Test (expected = NullPointerException.class)
	public void testGetSenderNullPointerException() {
		Message msg = new Message("Salut", null);
		assertTrue("Sender : null", msg.getSender().equals(null));
	}
	
	@Test
	public void testEquals() {
		User usr = mock(User.class);
		Message msg = new Message("Salut", usr);
		Message msg2 = new Message("Salut", usr);
		Message msg3 = new Message("Yo", usr);
		Message msg4 = new Message("Salut", null);
		assertTrue("msg1 = msg1", msg.equals(msg));
		assertTrue("msg1 != msg2", !msg.equals(msg2));
		assertTrue("msg1 != msg3", !msg.equals(msg3));
		assertTrue("msg1 != msg4 with msg4.sender = null", !msg.equals(msg4));
	}

	private User mock(Class<User> class1) {
		User usr = null;
		try {
			usr = new User("Alex", InetAddress.getByName("127.0.0.1"), 5789, User.typeConnect.CONNECTED);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return usr;
	}

}
