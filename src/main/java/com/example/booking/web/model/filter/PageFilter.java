package com.example.booking.web.model.filter;

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
