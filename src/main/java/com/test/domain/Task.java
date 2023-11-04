package com.test.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="task")
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "description")
    String description;
    @Enumerated(EnumType.ORDINAL)
    @Column(columnDefinition = "tinyint")
    Status status;

}
