package com.iceskatinguphill;

import io.smallrye.common.constraint.NotNull;
import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.json.bind.annotation.JsonbNumberFormat;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

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
@Schema(name = "Book", description = "POJO that represents a book")
public class Book extends PanacheEntity{
    @NotNull
    public String title;
    @Column(length = 13)
    public String isbn;
    @Size(min=1, max=20000)
    @JsonbTransient
    @Column(length = 20000)
    public String description;
    @Min(1)
    @JsonbNumberFormat(value = "$#0.00")
    public BigDecimal price;
    @Column(name = "image_url")
    @JsonbTransient
    public String imageUrl;
    @Past
    @Column(name = "publication_date")
    @JsonbProperty("publication-date")
    @JsonbDateFormat(value = "dd-MM-yyyy")
    public LocalDate publicationDate;
    @Min(10)
    @Column(name = "nb_of_pages")
    @JsonbProperty("nb-of-pages")
    public Integer nbOfPages;
}
