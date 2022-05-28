package com.platform.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author by Hchier
 * @Date 2022/5/26 15:52
 */
@Component
public class JSONUtil {
    private ObjectMapper objectMapper;

    public JSONUtil(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T> String jsonArrayAdd(String jsonArrayStr, T t) throws JsonProcessingException {
        List<T> list = objectMapper.readValue(jsonArrayStr, List.class);
        if(list.contains(t)){
            return objectMapper.writeValueAsString(list);
        }
        list.add(t);
        return objectMapper.writeValueAsString(list);
    }

    public <T> String jsonArrayDelete(String jsonArrayStr, T t) throws JsonProcessingException {
        List<T> list = objectMapper.readValue(jsonArrayStr, List.class);
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            T item = it.next();
            if (item.equals(t)) {
                it.remove();
            }
        }
        return objectMapper.writeValueAsString(list);
    }
}
