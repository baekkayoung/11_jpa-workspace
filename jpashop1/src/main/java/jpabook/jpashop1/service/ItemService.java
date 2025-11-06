package jpabook.jpashop1.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop1.domain.Item.Book;
import jpabook.jpashop1.domain.Item.Item;
import jpabook.jpashop1.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional // 리드온리 아니고 수정해야하니까 붙여주자
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    
    // 변경 감지
    @Transactional // UpdateDto dto
    public Item updateItem(Long itemId, String name, int price, int stockQuantity
                           /*,Book bookParam*/){ // bookParam = 준영속 엔티티 |  // int price, int stockQuantity 이런 식으로 내가 바꾸려는 걸 가지고 와라!
        Item findItem = itemRepository.findOne(itemId); // findItem = 영속 엔티티 로 바뀜

//        findItem.setPrice(price); 이렇게 하던가 Dto로 받아주든가
//        findItem.setPrice(bookParam.getPrice()); // 얘는 update 쿼리 나감. dirty checking 발생
//        findItem.setName(bookParam.getName());
//        findItem.setStockQuantity(bookParam.getStockQuantity());

        findItem.setPrice(price);
        findItem.setName(name);
        findItem.setStockQuantity(stockQuantity);

        // itemRepository.save(findItem); 이거 안 해도 됨
        // 커밋하면 알아서 디비에 sql 실행!
        
        return findItem; // 반환값 : 영속성 컨텍스트에서 관리하는 객체
    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }





}
