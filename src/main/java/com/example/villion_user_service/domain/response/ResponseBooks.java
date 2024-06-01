package com.example.villion_user_service.domain.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseBooks {
    // books 더미데이터
    private Integer bookId;
    private String bookName;
    private String author;
    private String publisher;

}
