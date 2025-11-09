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

    // 고객 정보 등록
    @Transactional
    public Long join(Customer customer){
        validateDuplicateCustomer(customer);
        return customerRepository.save(customer);
    }


    // 같은 이름 + 전화번로 중복 거르기
    private void validateDuplicateCustomer(Customer customer) {
        List<Customer> findCustomers = customerRepository.findByNameWithPhone(
                customer.getName(),
                customer.getPhone()
        );

        if (!findCustomers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 고객입니다.");
        }
    }

    // 고객 한 명 찾기
    public Customer findOne(Long CustomerId){
        return customerRepository.findOne(CustomerId);
    }

    // 모든 고객 찾기
    private List<Customer> fineCustomers(){
        return customerRepository.findAll();
    }





}
