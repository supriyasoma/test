package com.bc115.userservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "reciepes")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String recipeName;

    @ElementCollection
    private List<String> ingredients;

    @ElementCollection
    private List<String> procedure;

    private boolean isVegetarian;

    private String thumbnailImageUrl;

    @ElementCollection
    private List<String> otherImageUrls;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
