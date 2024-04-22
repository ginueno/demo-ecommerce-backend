package ecommerce.finalproject.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        @Column(name = "product_name")
        private String name;

        private String imgUrl;

        @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
        @CollectionTable(name = "description", joinColumns = @JoinColumn(name = "product_id"))
        private List<String> description = new ArrayList<>();

        @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
        @CollectionTable(name = "desc_img_url", joinColumns = @JoinColumn(name = "product_id"))
        private List<String> descImgUrl = new ArrayList<>();

        @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
        @JsonManagedReference
        private List<Variant> variants = new ArrayList<>();

        @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<Rating> ratings = new ArrayList<>();

        @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<Review> reviews = new ArrayList<>();

        @Column(name = "avg_rating")
        private double averageRating;

        @Column(name = "discounted_price")
        private int discountedPrice;

        @ManyToMany(cascade = CascadeType.ALL)
        @JoinTable(name = "product_collection", joinColumns = {
                        @JoinColumn(name = "product_id") }, inverseJoinColumns = {
                                        @JoinColumn(name = "collection_id") })
        @JsonManagedReference
        private List<Collection> collections = new ArrayList<>();

        @ManyToMany(cascade = CascadeType.ALL)
        @JoinTable(name = "product_lpage", joinColumns = { @JoinColumn(name = "product_id") }, inverseJoinColumns = {
                        @JoinColumn(name = "lpage_id") })
        @JsonManagedReference
        private List<LPage> lpages = new ArrayList<>();

        private LocalDateTime createdAt = LocalDateTime.now();

        @Column(name = "product_price")
        private int price;

        private String subTitle;
}
