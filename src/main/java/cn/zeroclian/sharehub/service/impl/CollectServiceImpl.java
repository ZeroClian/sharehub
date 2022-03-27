package cn.zeroclian.sharehub.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.zeroclian.sharehub.base.dto.CollectDto;
import cn.zeroclian.sharehub.base.dto.DatelineDto;
import cn.zeroclian.sharehub.base.dto.UserDto;
import cn.zeroclian.sharehub.base.lang.Consts;
import cn.zeroclian.sharehub.entity.Collect;
import cn.zeroclian.sharehub.entity.User;
import cn.zeroclian.sharehub.mapstruct.CollectMapper;
import cn.zeroclian.sharehub.repository.CollectRepository;
import cn.zeroclian.sharehub.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author ZeroClian
 * @date 2022-03-27 2:56 上午
 */
@Service
public class CollectServiceImpl implements CollectService {

    @Autowired
    CollectRepository collectRepository;

    @Autowired
    CollectMapper collectMapper;

    @Autowired
    HttpSession httpSession;

    @Override
    public List<DatelineDto> getDatelineByUserId(long userId) {

        List<Date> collectDates = collectRepository.getDatelineByUserId(userId);

        List<DatelineDto> datelineDtos = new ArrayList<>();

        for (Date date : collectDates) {
            String parent = DateUtil.format(date, "yyyy年MM月");
            String title = DateUtil.format(date, "yyyy年MM月dd日");

            datelineDtos = handleDateline(datelineDtos, parent, title);
        }
        return datelineDtos;
    }

    @Override
    public Page<CollectDto> findUserCollects(long userId, String dateline, Pageable pageable) {

        Page<Collect> page = collectRepository.findAll((root, query, builder) -> {

            Predicate predicate = builder.conjunction();

            Join<Collect, User> join = root.join("user", JoinType.LEFT);
            predicate.getExpressions().add(builder.equal(join.get("id"), userId));

            if (!dateline.equals("all")) {
                // 仅查询某一天的记录
                LocalDate localDate = LocalDate.parse(dateline, DateTimeFormatter.ofPattern("yyyy年MM月dd日"));
                predicate.getExpressions().add(
                        builder.equal(root.get("collected"), localDate));
            }

            // 私有收藏非本人不能看
            UserDto userDto = (UserDto) httpSession.getAttribute(Consts.CURRENT_USER);
            boolean isOwn = userDto != null && userDto.getId().longValue() == userId;
            if (!isOwn) {
                predicate.getExpressions().add(builder.equal(root.get("personal"), 0));
            }

            return predicate;
        }, pageable);


        return page.map(collectMapper::toDto);
    }

    @Override
    public Collect findById(long id) {
        Optional<Collect> optional = collectRepository.findById(id);
        return optional.isPresent() ? optional.get() : null;
    }

    @Override
    public void deleteById(long id) {
        collectRepository.deleteById(id);
    }

    @Override
    public void save(Collect collect) {
        if (collect.getId() == null) {
            collect.setCreated(LocalDateTime.now());
            collect.setCollected(LocalDate.now());

            collectRepository.save(collect);
        } else {

            Collect temp = collectRepository.getById(collect.getId());

            temp.setTitle(collect.getTitle());
            temp.setUrl(collect.getUrl());
            temp.setNote(collect.getNote());
            temp.setUser(collect.getUser());
            temp.setPersonal(collect.getPersonal());

            temp.setCollected(LocalDate.now());
            collectRepository.save(temp);
        }
    }

    @Override
    public Page<CollectDto> findSquareCollects(Pageable pageable) {
        Page<Collect> page = collectRepository.findAllByPersonal(0, pageable);
        return page.map(collectMapper::toDto);
    }

    /**
     * 构建日期侧边栏的上下级关系
     */
    private List<DatelineDto> handleDateline(List<DatelineDto> datelineDtos, String parent, String title) {

        // 需要被添加到上级中的子级
        DatelineDto datelineDto = new DatelineDto();
        datelineDto.setTitle(title);

        Optional<DatelineDto> optional = datelineDtos.stream().filter(vo -> vo.getTitle().equals(parent)).findFirst();

        if (optional.isPresent()) {
            // 如果上级存在，则直接添加到该上级中
            optional.get().getChildren().add(datelineDto);
        } else {
            DatelineDto parentDto = new DatelineDto();
            parentDto.setTitle(parent);
            parentDto.getChildren().add(datelineDto);


            datelineDtos.add(parentDto);
        }
        return datelineDtos;
    }

}
