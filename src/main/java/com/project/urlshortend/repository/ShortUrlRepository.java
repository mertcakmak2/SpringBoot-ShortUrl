package com.project.urlshortend.repository;

import com.project.urlshortend.model.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShortUrlRepository extends JpaRepository<ShortUrl, Integer> {

    Boolean existsByShortUrl(String shortUrl);
    List<ShortUrl> findByUserId(int userId);
    ShortUrl findByGeneratedString(String shortUrl);

    ShortUrl findByIdAndUser_Id(int id, int userId);
}
