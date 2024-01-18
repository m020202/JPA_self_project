package study.project.domain.item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import study.project.domain.Category;
import study.project.domain.NotEnoughStockException;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorColumn()
@Setter @Getter
public abstract class Item {
    @Id @GeneratedValue
    @Column(name = "ITEM_ID")
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    public void addStock(int quantity) {
        stockQuantity += quantity;
    }

    public void removeStock(int quantity) {
        int restStock = stockQuantity - quantity;
        if (restStock < 0) throw new NotEnoughStockException("need more stock");
        stockQuantity = restStock;
    }
}
