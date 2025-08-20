package com.project.ds_helper.common.dto.response;

import org.springframework.data.domain.Sort;

import javax.swing.*;
import java.util.List;

public record PageResDto(
        int page,               // 0-based (Slice일 땐 호출자가 0 고정으로 넣어도 OK)
        int size,
        long totalElements,     // Slice면 -1 또는 0
        int totalPages,         // Slice면 -1
        boolean first,
        boolean last,
        boolean hasNext,
        boolean hasPrevious,
        Sort sort    // 정렬 정보 에코백
) {
}
