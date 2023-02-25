package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class OrderRepository {
HashMap<String,Order>orders ;
HashMap<String,DeliveryPartner>deliveryPartners;
HashMap<String,String>orderPartnerPair;
HashMap<String, List<String>>partnerOrders;

public OrderRepository(){
    orders = new HashMap<>();  //initialise hashmaps
    deliveryPartners = new HashMap<>();
    orderPartnerPair = new HashMap<>();
    partnerOrders = new HashMap<>();

}
//addOrder
    public void addOrder(Order order){
    String orderId = order.getId();
    orders.put(orderId,order);
    }
    //add Partner
    public void addPartner(String partnerId){

    deliveryPartners.put(partnerId,new DeliveryPartner(partnerId));
    }

    //add order-partner pair
    public void addOrderPartnerPair(String orderId ,String deliverypartnerid){
    List<String>orderlist = partnerOrders.getOrDefault(deliverypartnerid,new ArrayList<>());
    orderlist.add(orderId);

    partnerOrders.put(deliverypartnerid,orderlist);
    orderPartnerPair.put(orderId,deliverypartnerid);
    //update no of orders in partner object
        DeliveryPartner deliveryPartner = deliveryPartners.get(deliverypartnerid);
        deliveryPartner.setNumberOfOrders(orderlist.size());
    }
    //get orders by id
    public Order getOrderById(String orderId){
    return orders.get(orderId);
    }
    // delivery partner
    public DeliveryPartner getPartnerById(String partnerId){
    return deliveryPartners.get(partnerId);
    }
    //odercountbyParner
    public int getOrderCountByPartnerId(String partnerId){
    int countofOrders =deliveryPartners.get(partnerId).getNumberOfOrders();
    return countofOrders;
    }
    public List<String>getOrdersByPartnerId(String partnerId){
    return partnerOrders.get(partnerId);
    }
    //all orders
    public List<String>getAllOrders(){
    return new ArrayList<>(orders.keySet());
    }
    //unassigned order counr
    public int getCountOfUnassignedOrders(){
    return orders.size()-partnerOrders.size();
    }
    //left order after time
    public int getOrdersLeftAfterGivenTime(String time,String partnerId){
    int countOfLeftOrders = 0;
    int timeInt = Order.getDeliveryTimeAsInt(time);
    List<String>orderlist = partnerOrders.get(partnerId);
    for (String orderId : orderlist){
        if(orders.get(orderId).getDeliveryTime()>timeInt){
            countOfLeftOrders++;
        }
    }
    return countOfLeftOrders;
    }
    //
    public String getLastDeliveryTimeByPartnerId(String partnerId){
    int lastOrderTime =0;
    List<String>orderList = partnerOrders.get(partnerId);
    for (String orderid : orderList){
    lastOrderTime = Math.max(lastOrderTime,orders.get(orderid).getDeliveryTime());
    }
    return Order.getDeliveryTimeAsString(lastOrderTime);
    }
    //
    public void deletePartnerById(String partnerId){
        List<String>orderList = partnerOrders.get(partnerId);
        for (String orderid : orderList){
            orderPartnerPair.remove(orderid);
        }
        deliveryPartners.remove(partnerId);

    }
    public void deleteOrderById(String orderId){
    orders.remove(orderId);
    if (orderPartnerPair.containsKey(orderId)){
        String partnerId = orderPartnerPair.get(orderId);
        orderPartnerPair.remove(orderId);
        partnerOrders.get(partnerId).remove(orderId);
        deliveryPartners.get(partnerId).setNumberOfOrders(partnerOrders.get(partnerId).size());
    }
    }
}
