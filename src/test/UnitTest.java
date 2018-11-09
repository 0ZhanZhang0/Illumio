package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import code.Firewall;

class UnitTest {
	
	private static Firewall f = new Firewall("src/resource/rule.csv");

	@Test
	void test1() {
		assertEquals(true, f.accept_packet("inbound", "tcp", 80, "192.168.1.2"));
	}
	
	@Test
	void test2() {
		assertEquals(true, f.accept_packet("outbound", "udp", 1000, "52.12.48.92"));
	}
	
	@Test
	void test3() {
		assertEquals(false, f.accept_packet("inbound", "udp", 53, "192.168.2.6"));
	}
	
	@Test
	void test4() {
		assertEquals(false, f.accept_packet("outbound", "udp", 53, "192.168.2.6"));
	}

}
