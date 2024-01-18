package study.project.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import study.project.domain.Order;
import study.project.domain.OrderSearch;
import study.project.domain.OrderStatus;
import study.project.dto.MemberDtoForController;
import study.project.dto.OrderDtoForController;
import study.project.dto.OrderDtoForRepository;

import java.util.List;

import static study.project.domain.QOrder.order;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    public List<Order> findAll(OrderSearch orderSearch) {
        return queryFactory
                .selectFrom(order)
                .join(order.member).fetchJoin()
                .join(order.orderItems).fetchJoin()
                .where(nameEq(orderSearch.getName()), statusEq(orderSearch.getStatus()))
                .fetch();
    }

    public List<OrderDtoForRepository> findAllByDto(OrderSearch orderSearch) {
        return queryFactory
                .select(Projections.constructor(OrderDtoForRepository.class,
                        order.id,order.member.name,order.delivery.address,order.delivery.status))
                .from(order)
                .join(order.member)
                .join(order.delivery)
                .where(nameEq(orderSearch.getName()), statusEq(orderSearch.getStatus()))
                .fetch();
    }

    private Predicate statusEq(OrderStatus status) {
        return (status != null) ? order.status.eq(status) : null;
    }

    private Predicate nameEq(String name) {
        return (StringUtils.hasText(name)) ? order.member.name.eq(name) : null;
    }
}
