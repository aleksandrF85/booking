package com.example.booking.web.dto.filter;

import com.example.booking.validation.PageFilterValidation;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@PageFilterValidation
public class PageFilter {

    private int pageSize;

    private int pageNumber;


}
