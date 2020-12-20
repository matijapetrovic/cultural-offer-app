package cultureapp.domain.category;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name="category")
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Category {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name="name", nullable = false, unique = true)
    private String name;

    @Column(name="archived", nullable = false)
    private boolean archived;

    public void archive() { this.archived = true; }

    public boolean update(String name) {
        boolean changed = false;
        if (!this.name.equals(name)) {
            this.name = name;
            changed = true;
        }
        return changed;
    }

    public static Category withId(
            Long id,
            String name) {
        return new Category(id, name, false);
    }

    public static Category of(
            String name) {
        return withId(null, name);
    }
}
