package com.diplom.dao;

import com.diplom.model.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Stole on 4/27/2016.
 */
@Transactional
public interface CategoryDao extends CrudRepository<Category,Integer> {

    @Query(value = "SELECT c.* FROM category c ORDER BY id DESC LIMIT 1",nativeQuery = true)
    public Category lastEntry();

    public Category findByName(String name);
}
