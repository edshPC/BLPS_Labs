package edsh.blps.entity.secondary;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import org.springframework.stereotype.Component;

@Component
public class PaymentListener {

    @PostPersist
    public void onPrePersist(Payment payment) {

    }

    @PostUpdate
    public void onPreUpdate(Payment payment) {
    }

    private void saveChangeHistory() {

    }

}
