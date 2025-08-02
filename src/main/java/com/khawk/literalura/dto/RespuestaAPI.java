package com.khawk.literalura.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RespuestaAPI {

    private Integer count;
    private String next;
    private String previous;
    private List<LibroDTO> results;

    public RespuestaAPI() {}
    
    public RespuestaAPI(Integer count, String next, String previous, List<LibroDTO> results) {
        this.count = count;
        this.next = next;
        this.previous = previous;
        this.results = results;
    }

    @Override
    public String toString() {
        return "RespuestaAPI{" +
                "count=" + count +
                ", next='" + next + '\'' +
                ", previous='" + previous + '\'' +
                ", results=" + results +
                '}';
    }
} 