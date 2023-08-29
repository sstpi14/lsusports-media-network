package com.lsutigersports.mediawebsite.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;

    @Column
    private String commentDesc;

    @ElementCollection
    List<String> commentImg;

    @ManyToOne
    private User user;

    @ManyToOne
    @JsonIgnoreProperties({"comments"})
    private Post post;
}
