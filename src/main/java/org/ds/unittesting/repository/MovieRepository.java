package org.ds.unittesting.repository;

import org.ds.unittesting.bean.MovieBean;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<MovieBean, Long> {

    List<MovieBean> findByGenre(String genre);
}
