package tests;

import static org.junit.Assert.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.Test;

import communication.User;

public class UserTest {

	@Test
	public void testEquals() {
		InetAddress ip = null, ip2 = null;
		try {
			ip = InetAddress.getByName("225.1.2.3");
			ip2 = InetAddress.getByName("127.0.0.1");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		User usr1 = new User("Alex", ip, 5787, User.typeConnect.CONNECTED);
		User usr2 = new User("Alex", ip, 5787, User.typeConnect.CONNECTED);
		User usr3 = new User("Quentin", ip2, 5989, User.typeConnect.CONNECTED);
		User usr4 = null;
		
		//Test1 : 2 same instances
		assertTrue("usr1 = usr1\n", usr1.equals(usr1));
		//Test2 : 2 different but equal instances
		assertTrue("usr1 = usr2\n", usr1.equals(usr2));
		//Test3 : 2 completely different instances
		assertTrue("usr1 != usr3\n", !usr1.equals(usr3));
		//Test4 : An instance not equivalent to null
		assertTrue("usr1 != usr4, with usr4 = null\n", !usr1.equals(usr4));
		
	}
	
	@SuppressWarnings("null")
	@Test (expected =NullPointerException.class)
	public void testEqualsNullexception() {
		//Test : 5 nullPointerException
		User usr4 = null;
		assertTrue("usr4 = null; NullPointerException raised\n", usr4.equals(null));
	}

}
