package restaurant.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import restaurant.reservation.domain.Customer;
import restaurant.reservation.repository.CustomerRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomerService {
    
    private final CustomerRepository customerRepository;

    @Transactional
    public Long join(Customer customer){
        validateDuplicateCustomer(customer);
        return customerRepository.save(customer);
    }


    // 같은 이름 + 전화번호 중복 거르기
    private void validateDuplicateCustomer(Customer customer) {
        List<Customer> findCustomers = customerRepository.findByNameWithPhone(
                customer.getName(),
                customer.getPhone()
        );

        if (!findCustomers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 고객입니다.");
        }
    }

    private List<Customer> fineCustomers(){
        return customerRepository.findAll();
    }

    public Customer findOne(Long CustomerId){
        return customerRepository.findOne(CustomerId);
    }



}
