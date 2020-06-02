package projekti.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import projekti.model.Account;
import projekti.model.ConnectionRequest;

public interface ConnectionRequestRepository extends JpaRepository<ConnectionRequest, Long> {
    List<ConnectionRequest> findAllBySender(Account sender);
    List<ConnectionRequest> findAllByReceiver(Account receiver);
    ConnectionRequest findConnectionRequestBySenderAndReceiver(Account sender, Account receiver);
}
