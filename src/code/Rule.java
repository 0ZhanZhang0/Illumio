package code;

public class Rule {
	String direction;
	String protocol;
	int[] port;
	long[] ip_address;
	
	public Rule() {
		super();
	}

	public Rule(String direction, String protocol, int[] port, long[] ip_address) {
		super();
		this.direction = direction;
		this.protocol = protocol;
		this.port = port;
		this.ip_address = ip_address;
	}
	
	
	
	
}
