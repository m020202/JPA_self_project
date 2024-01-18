package study.project.service;

import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import study.project.domain.*;
import study.project.domain.item.Book;
import study.project.domain.item.Item;
import study.project.repository.OrderRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class OrderServiceTest {
    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        Address address = new Address();
        address.setCity("seoul");
        address.setStreet("1111");
        address.setZipcode("0000");

        Member member = Member.createMember("kim",address);
        em.persist(member);

        Item item = new Book();
        item.setName("JPA");
        item.setPrice(10000);
        item.setStockQuantity(10);
        em.persist(item);

        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        Order getOrder = orderRepository.findOne(orderId);

        assertThat(getOrder.getStatus()).isEqualTo(OrderStatus.ORDER);
        assertThat(getOrder.getOrderItems().size()).isEqualTo(1);
        assertThat(item.getStockQuantity()).isEqualTo(8);
    }

    @Test
    public void 상품재고_초과() throws Exception{
        Address address = new Address();
        address.setCity("seoul");
        address.setStreet("1111");
        address.setZipcode("0000");

        Member member = Member.createMember("kim",address);
        em.persist(member);

        Item item = new Book();
        item.setName("JPA");
        item.setPrice(10000);
        item.setStockQuantity(1);
        em.persist(item);

        int orderCount = 2;
        assertThrows(NotEnoughStockException.class,() -> {
            Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
        });
    }

    @Test
    public void 주문취소() throws Exception {
        // given
        Address address = new Address();
        address.setCity("seoul");
        address.setStreet("1111");
        address.setZipcode("0000");

        Member member = Member.createMember("kim",address);
        em.persist(member);

        Item item = new Book();
        item.setName("JPA");
        item.setPrice(10000);
        item.setStockQuantity(10);
        em.persist(item);

        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        // when
        orderService.cancelOrder(orderId);

        // then
        Order cancelOrder = orderRepository.findOne(orderId);
        assertThat(cancelOrder.getStatus()).isEqualTo(OrderStatus.CANCEL);

        assertThat(item.getStockQuantity()).isEqualTo(10);
    }

    @Test
    public void 주문조회() {
        Address address = new Address();
        address.setCity("seoul");
        address.setStreet("1111");
        address.setZipcode("0000");

        Member member = Member.createMember("kim",address);
        em.persist(member);

        Item item = new Book();
        item.setName("JPA");
        item.setPrice(10000);
        item.setStockQuantity(10);
        em.persist(item);

        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        OrderSearch orderSearch = new OrderSearch();
        orderSearch.setName("kim");
        orderSearch.setStatus(OrderStatus.ORDER);
        List<Order> all = orderRepository.findAll(orderSearch);
        System.out.println(all.size());

        List<Order> all2 = orderRepository.findAll(new OrderSearch());
        System.out.println(all2.size());
    }
}