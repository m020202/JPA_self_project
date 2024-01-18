package study.project.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import study.project.domain.item.Item;
import study.project.domain.item.QItem;

import java.util.List;

import static study.project.domain.item.QItem.item;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public void save(Item item) {
        if (item.getId() == null) em.persist(item);
        else em.merge(item);
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return queryFactory
                .selectFrom(item)
                .fetch();
    }
}
