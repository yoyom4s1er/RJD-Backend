package com.example.rjdtask1.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "k1", schema = "public")
public class Classifier1 {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "k1_seq")
    @SequenceGenerator(name = "k1_seq", sequenceName = "k1_id_seq", allocationSize = 1)
    @Column(name = "id")
    private long id;
    @Column(name = "column2")
    private String column2;
    @Column(name = "column3")
    private int column3;
    @Column(name = "column4")
    private int column4;
    @Column(name = "column5")
    private int column5;
    @Column(name = "column6")
    private int column6;
    @Column(name = "column7")
    private int column7;
}
