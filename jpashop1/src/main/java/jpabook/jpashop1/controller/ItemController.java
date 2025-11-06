package jpabook.jpashop1.controller;

import jpabook.jpashop1.domain.Item.Book;
import jpabook.jpashop1.domain.Item.Item;
import jpabook.jpashop1.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model){

        model.addAttribute("form", new BookForm());
        return "items/createItemForm";

    }

    @PostMapping("/items/new")
    public String create(BookForm form){

        Book book = new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());
        // createBook 비즈니스 로직으로 대체해라

        itemService.saveItem(book);

        return "redirect:/";

    }

    @GetMapping("/items")
    public String list(Model model){
        List<Item> items = itemService.findItems();

        model.addAttribute("items",items);
        return "items/itemList";
    }

    @GetMapping("/items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model){

        Book item = (Book) itemService.findOne(itemId); // 얘는 분명 영속 상태인데

        BookForm form = new BookForm();
        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsbn());

        model.addAttribute("form",form);

        return "items/updateItemForm";
    }

    @PostMapping("/items/{itemId}/edit")
    public String updateItem(@ModelAttribute("form") BookForm form) {

        // 얘랑 멤버 컨트롤러 뭐랑 비교해서 보는거라고?

        // 얘는 수정하려고 할 때 새로운 북개체를 만드는데 준영속 엔티티 상태야.. 왜??
        // 업데이트 쿼리 못나가지 끊어진 상태니까.
        // Book 객체는 이미 DB에 한번 저장되어서 식별자가 존재한다.
        // 이렇게 임의로 만들어낸 엔티티도 기존 식별자를 가지고 있으면 준영속 엔티티로 볼 수 있다)

        /* 이거 되게 어설픈 엔티티 생성임..
        Book book = new Book(); // 준영속 엔티티가 되어버림 ;; => 아무리 set을 해도 업데이트 쿼리가 안 나감
        book.setId(form.getId());
        book.setName(form.getName()); 만약 이거를 빼트리면 머지할 때 ""로 들어감
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());*/

        // itemService.updateItem(book.getId(), book); id랑 준영속 엔티티가 넘어감
        // itemService.saveItem(book); // update - merge

        itemService.updateItem(form.getId(), form.getName(), form.getPrice(), form.getStockQuantity());
        // DTO에 상단 set 필드를 넣어서 이렇게 하는게 나음 => int price 이런식으로 service에서 받아봐
        return "redirect:/items";
    }




}
