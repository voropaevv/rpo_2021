package ru.iu3.rpo.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity // таблица в базе
@Table(name = "artists") //имя это таблицы artists
@Access(AccessType.FIELD) // разрешаем доступ к полям класса
public class Artist {

    public Artist() { }
    public Artist(Long id) { this.id = id; }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    public long id;

    @Column(name = "name", nullable = false, unique = true)
    public String name;

    @Column(name = "century", nullable = false)
    public String century;

    @ManyToOne
    @JoinColumn(name="countryid")
    public Country country;

    @JsonIgnore
    @OneToMany
    public List<Painting> paintings = new ArrayList<Painting>();
}
