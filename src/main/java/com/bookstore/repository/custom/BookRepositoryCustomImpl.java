package com.bookstore.repository.custom;

import com.bookstore.dto.BookFilterDto;
import com.bookstore.model.BookEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {
    private final EntityManager entityManager;


    @Override
    public Page<BookEntity> getDataWithPagination(Pageable pageable, BookFilterDto filter) {
        TypedQuery<BookEntity> query = getCoreData(pageable, filter);
        int total = query.getResultList().size();
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());
        return new PageImpl<>(query.getResultList(), pageable, total);
    }

    @Override
    public boolean isBookExist(String isbn) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<BookEntity> inventoryRoot = criteriaQuery.from(BookEntity.class);
        criteriaQuery.select(criteriaBuilder.count(inventoryRoot));
        criteriaQuery.where(criteriaBuilder.equal(inventoryRoot.get("isbn"), isbn));
        return entityManager.createQuery(criteriaQuery).getSingleResult() > 0;
    }

    @Override
    public Optional<BookEntity> findByIdEager(Long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BookEntity> criteriaQuery = criteriaBuilder.createQuery(BookEntity.class);
        Root<BookEntity> inventoryRoot = criteriaQuery.from(BookEntity.class);
        inventoryRoot.fetch("category");
        inventoryRoot.fetch("publisher");
        criteriaQuery.where(criteriaBuilder.equal(inventoryRoot.get("id"), id));
        return entityManager.createQuery(criteriaQuery).getResultList().stream().findFirst();
    }

    private TypedQuery<BookEntity> getCoreData(Pageable pageable, BookFilterDto filter) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BookEntity> criteriaQuery = criteriaBuilder.createQuery(BookEntity.class);

        Root<BookEntity> inventoryRoot = criteriaQuery.from(BookEntity.class);
        List<Predicate> predicates = new ArrayList<>();
        if (filter == null) {
            return entityManager.createQuery(criteriaQuery);
        }

        if (!StringUtils.isEmpty(filter.getTitle())) {
            predicates.add(criteriaBuilder.like(inventoryRoot.get("title"), "%" + filter.getTitle() + "%"));
        }
        if (!StringUtils.isEmpty(filter.getAuthor())) {
            predicates.add(criteriaBuilder.like(inventoryRoot.get("author"), "%" + filter.getAuthor() + "%"));
        }
        if (filter.getPriceFrom() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(inventoryRoot.get("price"), filter.getPriceFrom()));
        }
        if (filter.getPriceTo() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(inventoryRoot.get("price"), filter.getPriceTo()));
        }
        if (filter.getPublishedDateFrom() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(inventoryRoot.get("publishedDate"), filter.getPublishedDateFrom()));
        }
        if (filter.getPublishedDateTo() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(inventoryRoot.get("publishedDate"), filter.getPublishedDateTo()));
        }
        if (!CollectionUtils.isEmpty(filter.getCategoryIds())) {
            predicates.add(inventoryRoot.get("category").get("id").in(filter.getCategoryIds()));
        }
        if (!CollectionUtils.isEmpty(filter.getPublisherIds())) {
            predicates.add(inventoryRoot.get("publisher").get("id").in(filter.getPublisherIds()));
        }
        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        if (pageable != null) {
            String property = "id";
            Sort.Direction direction = Sort.Direction.DESC;
            for (Sort.Order order : pageable.getSort()) {
                property = order.getProperty();
                direction = order.getDirection();
            }
            criteriaQuery.orderBy(direction == Sort.Direction.DESC ? criteriaBuilder.desc(inventoryRoot.get(property)) : criteriaBuilder.asc(inventoryRoot.get(property)));
        }

        return entityManager.createQuery(criteriaQuery);
    }
}
