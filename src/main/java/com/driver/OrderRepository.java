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
    public void addOrderPartnerPair(String orderId ,String partnerId) {
        if (orders.containsKey(orderId) && partnerOrders.containsKey(partnerId)) {
            orderPartnerPair.put(orderId, partnerId);
            List<String> orderlist = new ArrayList<>();
            if (partnerOrders.containsKey(partnerId)) {     // list1 partner1 -4 orders
                //orders with same delivery person put in his order list;

                orderlist = partnerOrders.get(partnerId);  //p1
            }
            orderlist.add(orderId);                   //o1
            partnerOrders.put(partnerId, orderlist); // partner - his orderslist

            //update no of orders in partner object

            DeliveryPartner deliveryPartner = deliveryPartners.get(partnerId);
            deliveryPartner.setNumberOfOrders(orderlist.size());  //set all orders of delivery partner
        }
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
    int CountOfOrders = partnerOrders.get(partnerId).size();
    return CountOfOrders;
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
    return orders.size()-partnerOrders.size();    // order -orderpartnerpair
    }
    //left order after time
    public int getOrdersLeftAfterGivenTime(int time,String partnerId){
    int countOfLeftOrders = 0;

    List<String>orderlist = partnerOrders.get(partnerId);
    for(String orderId : orderlist){
        int timedelivered = orders.get(orderId).getDeliveryTime();
        if(time < timedelivered){
            countOfLeftOrders++;
        }
    }
    return countOfLeftOrders;
    }
    //
    public int getLastDeliveryTimeByPartnerId(String partnerId){
    int LastOrderTime = 0;
    List<String>orderList = partnerOrders.get(partnerId);
    for (String OrderId : orderList){
        LastOrderTime = Math.max(LastOrderTime,orders.get(OrderId).getDeliveryTime());
    }
    return LastOrderTime;
    }
    //
    public void deletePartnerById(String partnerId){
        List<String>orderList = partnerOrders.get(partnerId);   //removing all orders by given partner from partnerorder map

        for (String OrderId : orderList)
            orderPartnerPair.remove(OrderId);   //orders from pair list

        for (String OrderId : orderList){
           partnerOrders.remove(OrderId);   //orders from pair list of that partnerid
        }
        deliveryPartners.remove(partnerId); //parnerlist

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
