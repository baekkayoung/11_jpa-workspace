package restaurant.reservation.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import restaurant.reservation.domain.Customer;
import restaurant.reservation.repository.CustomerRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@Transactional
class CustomerServiceTest {

    @Autowired CustomerService customerService;
    @Autowired CustomerRepository customerRepository;

    @Test
    public void 고객_정보_등록() throws Exception {
        //given
        Customer customer = new Customer();
        customer.setName("백가영");

        //when
        Long saveId = customerService.join(customer);

        //then
        assertEquals(customer,customerRepository.findOne(saveId),"고객이 잘 저장되었는지 확인");
    }

    @Test
    public void 중복_고객_예외_처리() throws Exception {
        //given
        Customer customer1 = new Customer();
        customer1.setName("baek");
        customer1.setPhone("01011112222");

        Customer customer2 = new Customer();
        customer2.setName("baek");
        customer2.setPhone("01011112222");

        //when
        customerService.join(customer1);

        //then
        assertThrows(IllegalStateException.class, ()-> customerService.join(customer2));

    }




}