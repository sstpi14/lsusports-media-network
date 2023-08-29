package com.lsutigersports.mediawebsite.repositories;

import com.lsutigersports.mediawebsite.models.Post;
import com.lsutigersports.mediawebsite.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post, Integer> {
    List<Post> getByUser(User user);
}
