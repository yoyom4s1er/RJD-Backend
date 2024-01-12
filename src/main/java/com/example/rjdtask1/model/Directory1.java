package com.example.rjdtask1.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "directory1")
@Table(name = "directory1", schema = "public")
public class Directory1 {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "directory1_seq")
    @SequenceGenerator(name = "directory1_seq", sequenceName = "directory1_id_seq", allocationSize = 1)
    @Column(name = "id")
    private long id;
    @Column(name = "col2")
    private String column2;
    @Column(name = "col3")
    private int column3;
    @Column(name = "col4")
    private int column4;
    @Column(name = "col5")
    private int column5;
}
