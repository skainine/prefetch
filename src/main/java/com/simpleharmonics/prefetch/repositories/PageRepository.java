package com.simpleharmonics.prefetch.repositories;

import com.simpleharmonics.prefetch.entities.PageEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;
import java.util.List;

public interface PageRepository extends CrudRepository<PageEntity, String> {

    @Query("SELECT COUNT(*) FROM pages")
    long countLinks();

    @Query("FROM pages WHERE body LIKE %:query%")
    List<PageEntity> search(String query);

    @Query("SELECT link FROM pages WHERE updated < :timestamp")
    List<String> getRefreshTargets(Timestamp timestamp);
}