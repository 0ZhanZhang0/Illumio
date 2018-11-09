<h1>Illumio Coding Assignment</h1>
<b>Rule class:</b> each role of the firewall, with 4 atrributes, use long to store the ip address<br>
<b>Firewall class:</b> used 4 lists to store all the rules: <br>
inbound rules for tcp, outbound rules for tcp, inbound rules for udp, outbound rules for udp<br>
all the rules inside one list are sorted by the port number<br>
<b>Accept_packet:</b> first find the corresponding list, then use binary search to check if there's a matched rule<br>
<b>Test:</b> used unit test<br>
<b>Improvement if I have more time:</b> I was thinking that I can use 5 lists to store ip addresses belong to different classes. When a new input comes, find check the class of ip address and then go directly to the corresponding list
