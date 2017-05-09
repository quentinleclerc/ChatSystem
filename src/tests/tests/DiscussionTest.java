package tests;

import static org.junit.Assert.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import model.Discussion;

import org.junit.Test;

import communication.Message;
import communication.User;

public class DiscussionTest {

	@Test
	public void testAdd() {
		Discussion disc = new Discussion(mock(User.class));
		boolean ok = disc.addMessage(mock2(Message.class));
		assertTrue("Returnment statement of add", ok);
	}
	
	@Test (expected = NullPointerException.class)
	public void testAddNull(){
		Discussion disc = new Discussion(mock(User.class));
		boolean ok = disc.addMessage(null);
		assertTrue("Returnment statement of add : false", !ok);
	}
	
	@Test
	public void testToString() {
		Discussion disc = new Discussion(mock(User.class));
		assertTrue("to String with only \"Salut\" in the message content", disc.toString().equals(""));
		disc.addMessage(mock2(Message.class));
		assertTrue("to String with only \"Salut\" in the message content", disc.toString().equals("[Quentin]Salut\n"));
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
	
	private User mock1(Class<User> class2) {
		User usr = null;
		try {
			usr = new User("Quentin", InetAddress.getByName("127.0.0.1"), 5889, User.typeConnect.CONNECTED);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return usr;
	}
	
	private Message mock2(Class<Message> class1) {
		Message msg = null;
		msg = new Message("Salut", mock1(User.class));
		return msg;
	}

}
