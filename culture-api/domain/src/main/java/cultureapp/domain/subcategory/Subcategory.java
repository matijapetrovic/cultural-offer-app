package cultureapp.domain.subcategory;

import cultureapp.domain.category.Category;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name="subcategory")
@Entity
@IdClass(SubcategoryId.class)
public class Subcategory implements Serializable {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Id
    @ManyToOne
    @JoinColumn(name="category_id", insertable = false, updatable = false)
    private Category category;

    @Column(name="name", nullable = false, unique = true)
    private String name;

    public static Subcategory withId(
            Long id,
            Category category,
            String name) {
        return new Subcategory(
                id,
                category,
                name);
    }

    public static Subcategory of(
            Category category,
            String name) {
        return withId(null, category, name);
    }
}
