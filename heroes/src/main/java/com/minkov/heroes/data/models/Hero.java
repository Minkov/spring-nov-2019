package com.minkov.heroes.data.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "heroes")
public class Hero {

    @Id
    private long id;

    @Column(unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column
    private Gender gender;

    @Column
    private int level;

    @Column
    private int stamina;

    @Column
    private int strength;

    @Column
    private int attack;

    @Column
    private int defence;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "hero_items",
            joinColumns = {@JoinColumn(name = "hero_id")},
            inverseJoinColumns = {@JoinColumn(name = "item_id")}
    )
    private List<Item> items;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;
}

