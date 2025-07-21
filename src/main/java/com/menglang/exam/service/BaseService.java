package com.menglang.exam.service;

import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/* Generic
- T=> Request Data
- R=> Response Data
- E=> Entity
 */

public interface BaseService<T extends Serializable, R extends Serializable, E extends Serializable> {
    R create(T dto);

    R update(Long id, T dto);

    R delete(Long id);

    R getById(Long id);

    List<R> getPageContent(Page<E> data);

    Page<E> getAll(Map<String, String> params);

}

