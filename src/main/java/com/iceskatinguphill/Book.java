package com.iceskatinguphill;

import io.smallrye.common.constraint.NotNull;
import jakarta.persistence.*;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Example JPA entity.
 *
 * To use it, get access to a JPA EntityManager via injection.
 *
 * {@code
 *     @Inject
 *     EntityManager em;
 *
 *     public void doSomething() {
 *         MyEntity entity1 = new MyEntity();
 *         entity1.field = "field-1";
 *         em.persist(entity1);
 *
 *         List<MyEntity> entities = em.createQuery("from MyEntity", MyEntity.class).getResultList();
 *     }
 * }
 */
@Entity
@Table(name = "T_BOOK")
public class Book extends PanacheEntity{
    @NotNull
    public String title;
    @Column(length = 13)
    public String isbn;
    @Size(min=1, max=20000)
    @Column(length = 20000)
    public String description;
    @Min(1)
    public BigDecimal price;
    @Column(name = "image_url")
    public String imageUrl;
    @Past
    @Column(name = "publication_date")
    public LocalDate publicationDate;
    @Min(10)
    @Column(name = "nb_of_pages")
    public Integer nbOfPages;
}
