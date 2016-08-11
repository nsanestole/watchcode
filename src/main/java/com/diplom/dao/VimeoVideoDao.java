package com.diplom.dao;

import com.diplom.model.User;
import com.diplom.model.VimeoVideo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Stole on 7/18/2016.
 */
@Transactional
public interface VimeoVideoDao extends CrudRepository<VimeoVideo, Integer> {
    @Query(value = "SELECT v.* FROM vimeovideo v WHERE v.user_id = :user_id ORDER BY id DESC LIMIT 4",nativeQuery = true)
    public List<VimeoVideo> lastViewed(@Param("user_id") Integer user_id);

    @Query(value = "select v.* from vimeovideo v where v.user_id = :user_id order by id", nativeQuery = true)
    public List<VimeoVideo> getHistory(@Param("user_id") Integer user_id);

    public VimeoVideo findByVideoUriAndUser(String videoUri, User user);
}
