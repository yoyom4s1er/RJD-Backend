package com.example.rjdtask1.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "directory2")
@Table(name = "directory2", schema = "public")
public class Directory2 {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
}
