package com.hjb.service;

import com.hjb.domain.PO.OrderPO;
import com.hjb.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<OrderPO> list(){
        return orderRepository.findAll();
    }

    public void save(OrderPO orderPO){
        orderRepository.save(orderPO);
    }
}
