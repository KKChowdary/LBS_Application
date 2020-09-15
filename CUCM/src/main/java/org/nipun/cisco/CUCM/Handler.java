package org.nipun.cisco.CUCM;

import com.cisco.cti.util.Condition;
import com.cisco.jtapi.extensions.CiscoAddrInServiceEv;
import com.cisco.jtapi.extensions.CiscoTermInServiceEv;

import javax.telephony.AddressObserver;
import javax.telephony.ProviderObserver;
import javax.telephony.TerminalObserver;
import javax.telephony.callcontrol.CallControlCallObserver;
import javax.telephony.events.*;

public class Handler implements

        ProviderObserver, TerminalObserver, AddressObserver, CallControlCallObserver {

    public Condition providerInService = new Condition();
    public Condition fromTerminalInService = new Condition();
    public Condition fromAddressInService = new Condition();
    public Condition callActive = new Condition();

    @Override
    public void addressChangedEvent(final AddrEv[] events) {
        for (AddrEv ev : events) {
            System.out.println("    Received--> Address/" + ev);
            switch (ev.getID()) {
                case CiscoAddrInServiceEv.ID:
                    fromAddressInService.set();
                    break;
            }
        }
    }

    @Override
    public void callChangedEvent(final CallEv[] events) {
        for (CallEv ev : events) {
            System.out.println("    Received--> Call/" + ev);
            switch (ev.getID()) {
                case CallActiveEv.ID:
                    callActive.set();
                    break;
            }
        }

    }

    @Override
    public void providerChangedEvent(final ProvEv[] events) {
        for (ProvEv ev : events) {
            System.out.println("    Received--> Provider/" + ev);
            switch (ev.getID()) {
                case ProvInServiceEv.ID:
                    providerInService.set();
                    break;
            }
        }
    }

    @Override
    public void terminalChangedEvent(final TermEv[] events) {
        for (TermEv ev : events) {
            System.out.println("    Received--> Terminal/" + ev);
            switch (ev.getID()) {
                case CiscoTermInServiceEv.ID:
                    fromTerminalInService.set();
                    break;
            }
        }
    }
}