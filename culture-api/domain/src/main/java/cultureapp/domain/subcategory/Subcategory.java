package cultureapp.domain.subcategory;

import cultureapp.domain.category.Category;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name="subcategory",
        uniqueConstraints = @UniqueConstraint(columnNames={"name", "archived"}))
@Entity
@IdClass(SubcategoryId.class)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Subcategory implements Serializable {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="subcategory_generator")
    @SequenceGenerator(name="subcategory_generator", sequenceName = "subcategory_id_seq", allocationSize = 1)
    @EqualsAndHashCode.Include
    private Long id;

    @Id
    @ManyToOne
    @JoinColumn(name="category_id", insertable = false, updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    private Category category;

    @Column(name="name", nullable = false, unique = true)
    private String name;

    @Column(name="archived", nullable = false)
    private boolean archived;

    public boolean update(String name) {
        boolean changed = false;
        if (!this.name.equals(name)) {
            this.name = name;
            changed = true;
        }
        return changed;
    }

    public void archive() {
        this.archived = true;
    }

    public void unarchive() { this.archived = false; }

    public static Subcategory withId(
            Long id,
            Category category,
            String name) {
        return new Subcategory(
                id,
                category,
                name,
                false);
    }

    public static Subcategory of(
            Category category,
            String name) {
        return withId(null, category, name);
    }
}
