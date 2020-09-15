package org.nipun.cisco.CUCM;

import com.cisco.jtapi.extensions.*;

import java.time.LocalDateTime;

// Basic make call example.

// Devices used / requirements (configure these in .env):
//   * ALICE_DN / CTI supported phone,associated with JTAPI user
//   * BOB_DN / any phone

// Scenario:
// 1. ALICE_DN creates/connects a call to BOB_DN
// 2. The call rings for 5 seconds (optionally it can be manually answered)
// 3. ALICE_DN drops the call

// Download the jtapi-10.5-2.10000-1 and set the classpath

import java.time.format.DateTimeFormatter;

import javax.telephony.*;

public class MakeCall {

    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss.SS");

    private static void log(final String msg) {
        System.out.println(dtf.format(LocalDateTime.now()) + " " + msg);
    }

    public static void main(final String args[])
            throws JtapiPeerUnavailableException, InvalidArgumentException, ResourceUnavailableException, MethodNotSupportedException,
            InvalidStateException, PrivilegeViolationException, InvalidPartyException, InterruptedException {

        // The Handler class provides observers for provider/address/terminal/call events
        Handler handler = new Handler();

        // Create the JtapiPeer object, representing the JTAPI library
        log("Initializing Jtapi");
        CiscoJtapiPeer peer = (CiscoJtapiPeer) JtapiPeerFactory.getJtapiPeer(null);

        // Create and open the Provider, representing a JTAPI connection to CUCM CTI Manager

        String sCUCMHost = "";//CUCM IP adress
        String sDeviceUN = "";//Dialing Device User Name
        String sDevicePw = "";//Dialing Device Password
        String sSourceAddress = "";//Dialing Device Number
        String sDestAddress = "";//To Number

        String providerString = sCUCMHost + ";login=" + sDeviceUN + ";passwd=" + sDevicePw;
        log("providerString: " + providerString);

        CiscoProvider provider = (CiscoProvider) peer.getProvider(providerString);
        log("provider: " + provider.toString());
        log("Awaiting ProvInServiceEv...");
        provider.addObserver(handler);
        handler.providerInService.waitTrue();

        // Open the ALICE_DN Address and wait for it to go in service
        CiscoAddress fromAddress = (CiscoAddress) provider.getAddress(sSourceAddress);
        log("fromAddress: " + fromAddress.toString());

        fromAddress.addObserver(handler);
        handler.fromAddressInService.waitTrue();
        // Add a call observer to receive call events
        fromAddress.addCallObserver(handler);
        // Get/open the first Terminal for the Address.  Could be multiple
        //   if it's a shared line
        CiscoTerminal fromTerminal = (CiscoTerminal) fromAddress.getTerminals()[0];
        log("Awaiting CiscoTermInServiceEv for: " + fromTerminal.getName() + "...");
        fromTerminal.addObserver(handler);
        handler.fromTerminalInService.waitTrue();

        // Create a new Call object from our provider
        CiscoCall call = (CiscoCall) provider.createCall();

        //log("Creating/connecting call to DN:");
        log("Awaiting CallActiveEv for Call: " + call.toString() + "...");
        call.connect(fromTerminal, fromAddress, sDestAddress);
        handler.callActive.waitTrue();

        // Wait 5 sec, then drop the call
        log("Sleeping 5 seconds...");
        Thread.sleep(5000);

        log("Dropping call: " + call.toString());
        //call.drop();

    }
}
