package code;

public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Firewall f = new Firewall("src/resource/rule.csv");
		System.out.println(f.accept_packet("outbound", "udp", 1000, "52.12.48.92"));

	}

}
