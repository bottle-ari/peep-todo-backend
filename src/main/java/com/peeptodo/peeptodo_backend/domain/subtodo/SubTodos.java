package com.peeptodo.peeptodo_backend.domain.subtodo;


import com.fasterxml.jackson.databind.JsonNode;
import net.minidev.json.JSONObject;
import netscape.javascript.JSObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 직렬화
 */
public class SubTodos  {
    private List<SubTodo> subTodoList = new ArrayList<>();

    public SubTodos(List<String> subTodoStrList){
        for (String subTodoStr : subTodoStrList) {
            subTodoList.add(new SubTodo(subTodoStr));
        }
    }

    public List<String> subTodoStrList(){
        return subTodoList.stream().map(SubTodo::getColumnString).collect(Collectors.toList());
    }

    SubTodos(){
    }

    public boolean appendSubTodo(String subTodo){
        return subTodoList.add(new SubTodo(subTodo));
    }

    public boolean isEmpty(){
        return subTodoList.isEmpty();
    }

    public int getSize(){
        return subTodoList.size();
    }

    public String getSubTodo(int index){
        return subTodoList.get(index).getColumnString();
    }

    @Override
    public String toString() {
        if (subTodoList.isEmpty()) {
            return "";
        }
        return subTodoList.stream().map(SubTodo::getColumnString).collect(Collectors.joining("\",\""));
    }

    private record SubTodo(String subTodoStr) {
        public String getColumnString() {
            // code20231107211012
            return subTodoStr;
        }
    }

}
