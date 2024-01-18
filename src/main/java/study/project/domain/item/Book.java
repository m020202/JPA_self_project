package study.project.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import study.project.domain.item.Item;

@Entity
@DiscriminatorValue("B")
@Setter @Getter
public class Book extends Item {
    private String author;
    private String isbn;
}
