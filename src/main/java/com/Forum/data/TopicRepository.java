package com.Forum.data;

import com.Forum.Topic;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface TopicRepository extends CrudRepository<Topic, Long>, PagingAndSortingRepository<Topic,Long> {
    @Query("select t from Topic t where t.title like %:#{#content}%")
    ArrayList<Topic> searchTopicsTitle(@Param("content") String content);

    @Query("select t from Topic t where t.content like %:#{#content}%")
    ArrayList<Topic> searchTopicsContent(@Param("content") String content);
}
