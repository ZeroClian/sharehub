package cn.zeroclian.sharehub.service;

import cn.zeroclian.sharehub.base.dto.CollectDto;
import cn.zeroclian.sharehub.base.dto.DatelineDto;
import cn.zeroclian.sharehub.entity.Collect;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @author ZeroClian
 * @date 2022-03-27 2:56 上午
 */
public interface CollectService  {

    List<DatelineDto> getDatelineByUserId(long userId);

    Page<CollectDto> findUserCollects(long userId, String dateline, Pageable pageable);

    Collect findById(long id);

    void deleteById(long id);

    void save(Collect collect);

    Page<CollectDto> findSquareCollects(Pageable pageable);
}
