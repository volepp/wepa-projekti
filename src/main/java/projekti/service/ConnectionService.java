package projekti.service;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projekti.model.Account;
import projekti.model.ConnectionRequest;
import projekti.repository.AccountRepository;
import projekti.repository.ConnectionRequestRepository;

@Service
public class ConnectionService {
    
    @Autowired
    private ConnectionRequestRepository crr;
    
    @Autowired
    private AccountRepository accountRepository;
    
    public List<ConnectionRequest> getConnectionRequestsBySender(Account sender) {
        return crr.findAllBySender(sender);
    }
    
    public List<ConnectionRequest> getConnectionRequestsByReceiver(Account receiver) {
        return crr.findAllByReceiver(receiver);
    }
    
    public ConnectionRequest getConnectionRequestBySenderAndReceiver(Account sender, Account receiver) {
        return crr.findConnectionRequestBySenderAndReceiver(sender, receiver);
    }
    
    public ConnectionRequest sendConnectionRequest(Account sender, Account receiver) {
        ConnectionRequest cr = new ConnectionRequest(sender, receiver);
        
        return crr.save(cr);
    }
    
    @Transactional
    public void acceptConnectionRequest(Account sender, Account receiver) {
        ConnectionRequest req = crr.findConnectionRequestBySenderAndReceiver(sender, receiver);
        
        sender.getConnections().add(receiver);
        receiver.getConnections().add(sender);
        
        crr.delete(req);
    }
    
    public void rejectConnectionRequest(Account sender, Account receiver) {
        ConnectionRequest req = crr.findConnectionRequestBySenderAndReceiver(sender, receiver);
        
        crr.delete(req);
    }
    
}
