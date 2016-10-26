package entity;

import java.util.List;

/**
 * Created by kaspe on 2016-10-26.
 */

public class CoffeeShop
{
    private String email;
    private String address;
    private String telephone; //Needs to be a String since someone is gonna be dumb enough to give the number as: +45 whatever
    private int coffeeBrandId;
    private int stampsGiven; //The number of coffee stamps issued by this branch
    private int subscriberCoffeesSold;

    private List<User> coffeShopUsers;

}
