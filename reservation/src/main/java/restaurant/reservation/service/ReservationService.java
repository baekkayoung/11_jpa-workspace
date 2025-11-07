package restaurant.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import restaurant.reservation.domain.Customer;
import restaurant.reservation.repository.CustomerRepository;
import restaurant.reservation.repository.ReservationRepository;
import restaurant.reservation.repository.TableRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationService {

    private final CustomerRepository customerRepository;
    private final ReservationRepository reservationRepository;
    private final TableRepository tableRepository;

    public Long reservation(Long customerId, Long tableId){
        //엔티티 조회
        Customer customer = customerRepository.findOne(customerId);
        return customerId;
    }
}
