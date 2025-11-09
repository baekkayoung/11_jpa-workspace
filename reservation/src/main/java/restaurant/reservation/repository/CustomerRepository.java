package restaurant.reservation.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import restaurant.reservation.domain.Customer;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomerRepository {

    private final EntityManager em;

    // 고객 정보 등록
    public Long save(Customer customer){
        em.persist(customer);
        return customer.getId();
    }

    // 이름 + 전화번호로 고객 찾기
    public List<Customer> findByNameWithPhone(String name, String phone) {
        return em.createQuery("select c from Customer c where c.name = :name and c.phone = :phone", Customer.class)
                .setParameter("name", name)
                .setParameter("phone", phone)
                .getResultList();
    }

    // 고객 한 명 찾기
    public Customer findOne(Long id){
        return em.find(Customer.class,id);
    }

    // 모든 고객 찾기
    public List<Customer> findAll(){
        return em.createQuery("select c from Customer c",Customer.class).getResultList();
    }



}
