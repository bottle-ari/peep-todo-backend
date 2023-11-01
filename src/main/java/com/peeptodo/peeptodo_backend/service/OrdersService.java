package com.peeptodo.peeptodo_backend.service;


// TODO: 11/1/2023 getNextOrders 전부 고치기
public interface OrdersService {
    void swapOrders(Long id, Long swapId);
    int getNextOrders(Long id); // 예를 들어 A유저의 카테고리 들의 orders 중에 가장 뒤 순서가 10이라면 11리턴
}
