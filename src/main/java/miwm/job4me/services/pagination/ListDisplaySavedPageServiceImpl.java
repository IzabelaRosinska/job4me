package miwm.job4me.services.pagination;

import miwm.job4me.web.model.listDisplay.ListDisplaySavedDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListDisplaySavedPageServiceImpl implements PageService<ListDisplaySavedDto> {

    @Override
    public Page<ListDisplaySavedDto> createPage(List<ListDisplaySavedDto> list, int pageSize, int pageNumber) {
        int start = pageNumber * pageSize;
        int end = Math.min((start + pageSize), list.size());
        List<ListDisplaySavedDto> subList = list.subList(start, end);
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);

        return new PageImpl<>(subList, pageRequest, list.size());
    }
}
