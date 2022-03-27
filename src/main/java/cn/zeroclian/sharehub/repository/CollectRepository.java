package cn.zeroclian.sharehub.repository;

import cn.zeroclian.sharehub.entity.Collect;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author ZeroClian
 * @date 2022-03-27 3:01 上午
 */
@Repository
public interface CollectRepository extends JpaRepository<Collect,Long>, JpaSpecificationExecutor<Collect> {

    @Query(value="select distinct collected from s_collect where user_id = ? order by collected desc", nativeQuery = true)
    List<Date> getDatelineByUserId(long userId);

    Page<Collect> findAllByPersonal(Integer personal, Pageable pageable);
}
