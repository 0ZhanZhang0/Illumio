package code;
import java.io.*;
import java.util.*;

public class Firewall {
	List<Rule> in_tcp;
	List<Rule> out_tcp;
	List<Rule> in_udp;
	List<Rule> out_udp;
	
	
	public Firewall(String path) {
		in_tcp = new ArrayList<>();
		out_tcp = new ArrayList<>();
		in_udp = new ArrayList<>();
		out_udp = new ArrayList<>();
		
		File csv = new File(path);
		BufferedReader br = null;
	    try {
	        br = new BufferedReader(new FileReader(csv));
	    } 
	    catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
	    String line = "";
	    String everyLine = "";
	    try {
	        while ((line = br.readLine()) != null) {
	            everyLine = line;
	            Rule r = buildRule(everyLine);
	            //add this rule into the list
	            String[] info = line.split(",");
	            switch(decideGroup(info[0], info[1])) {
	            	case 1: in_tcp.add(r);
	            			break;
	            	case 2: out_tcp.add(r);
        					break;
	            	case 3: in_udp.add(r);
	            			break;
	            	case 4: out_udp.add(r);
	            			break;
	            }
	        }
	    } 
	    catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	    Comparator<Rule> c = new Comparator<Rule>() {

			@Override
			public int compare(Rule r1, Rule r2) {
				return r1.port[0] - r2.port[0];
			}
        };
        
        Collections.sort(in_tcp, c);
        Collections.sort(out_tcp, c);
        Collections.sort(in_udp, c);
        Collections.sort(out_udp, c);
	    
	}
	
	private long ipToLong(String ip) {
    	long x = 0L;
    	String[] ips = ip.split("\\.");
		for(int i = 0; i < 4; i++){
            x = x * 256 + Integer.parseInt(ips[i]);
        }
		return x;
    }
	
	private int decideGroup(String dir, String pro) {
		Rule r = new Rule();
		if(dir.equals("inbound")) {
			if(pro.equals("tcp")) {
				return 1;
			}
			else {
				return 2;
			}
		}
		else {
			if(pro.equals("tcp")) {
				return 3;
			}
			else {
				return 4;
			}
		}
	}
	
	private Rule buildRule(String line) {
		Rule r = new Rule();
		String[] info = line.split(",");
        //get direction
        r.direction = info[0];
        //get protocol
        r.protocol = info[1];
        //get port
        String[] port_temp = info[2].split("-");
        int[] port;
        if(port_temp.length == 1) {
        	port = new int[1];
        	port[0] = Integer.parseInt(port_temp[0]);
        }
        else {
        	port = new int[2];
        	port[0] = Integer.parseInt(port_temp[0]);
        	port[1] = Integer.parseInt(port_temp[1]);
        }
        r.port = port;
        //get ip addresses
        String[] ip_temp = info[3].split("-");
        long[] ip;
        if(ip_temp.length == 1) {
        	ip = new long[1];
        	ip[0] = ipToLong(ip_temp[0]);
        }
        else {
        	ip = new long[2];
        	for(int i = 0; i < 2; i++) {
        		ip[i] = ipToLong(ip_temp[i]);
        	}
        }
        r.ip_address = ip;
		return r;
	}
    
    public boolean accept_packet(String dir, String pro, int p, String ip) {
    	Rule r = new Rule();
    	r.direction = dir;
    	r.protocol = pro;
    	r.port = new int[]{p};
    	r.ip_address = new long[] {ipToLong(ip)};
    	List<Rule> list = new ArrayList<>();
    	switch(decideGroup(dir, pro)) {
	    	case 1: list = in_tcp;
	    			break;
	    	case 2: list = out_tcp;
					break;
	    	case 3: list = in_udp;
	    			break;
	    	case 4: list = out_udp;
	    			break;
	    }
    	int left = 0;
    	int right = list.size() - 1;
    	while(left < right - 1) {
    		int mid = left + (right - left) / 2;
    		if(match(list.get(mid), r)) {
    			return true;
    		}
    		else if(list.get(mid).port[0] > r.port[0]) {
    			right = mid;
    		}
    		else {
    			left = mid;
    		}
    	}
    	if(match(list.get(left), r) || match(list.get(right), r)) {
    		return true;
    	}
    	return false;
    }
    
    private boolean match(Rule a, Rule b) {
    	if(!a.direction.equals(b.direction)) {
    		return false;
    	}
    	if(!a.protocol.equals(b.protocol)) {
    		return false;
    	}
    	if(a.port.length == 1) {
    		if(a.port[0] != b.port[0]) {
    			return false;
    		}
    	}
    	else {
    		if(a.port[0] > b.port[0] || a.port[1] < b.port[0]) {
    			return false;
    		}
    	}
    	if(a.ip_address.length == 1) {
    		if(a.ip_address[0] != b.ip_address[0]) {
    			return false;
    		}
    	}
    	else {
    		if(a.ip_address[0] > b.ip_address[0] || a.ip_address[1] < b.ip_address[0]) {
    			return false;
    		}
    	}
    	return true;
    }

}
