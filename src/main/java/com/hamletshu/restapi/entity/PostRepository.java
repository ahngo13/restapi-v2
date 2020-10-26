package com.hamletshu.restapi.entity;

import com.hamletshu.restapi.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>{

}