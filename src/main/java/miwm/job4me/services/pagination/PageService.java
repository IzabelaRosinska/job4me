package miwm.job4me.services.pagination;

import org.springframework.data.domain.Page;

import java.util.List;

public interface PageService<T> {

    Page<T> createPage(List<T> list, int pageSize, int pageNumber);

    Page<T> createPageGivenSublist(List<T> sublist, int pageSize, int pageNumber, int totalSize);

}
