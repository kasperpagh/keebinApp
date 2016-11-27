package entity;

import java.util.List;

/**
 * Created by kaspe on 2016-10-26.
 */

public class CoffeeShop
{
    private String coffeShopName;
    private String email;
    private String address;
    private String telephone; //Needs to be a String since someone is gonna be dumb enough to give the number as: +45 whatever
    private int coffeeBrandId;
    private int stampsGiven; //The number of coffee stamps issued by this branch
    private int subscriberCoffeesSold;

    private List<User> coffeShopUsers;

    public CoffeeShop(String email, String address, String telephone, int coffeeBrandId, String coffeShopName)
    {
        this.email = email;
        this.address = address;
        this.telephone = telephone;
        this.coffeeBrandId = coffeeBrandId;
        this.coffeShopName = coffeShopName;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getTelephone()
    {
        return telephone;
    }

    public void setTelephone(String telephone)
    {
        this.telephone = telephone;
    }

    public int getCoffeeBrandId()
    {
        return coffeeBrandId;
    }

    public void setCoffeeBrandId(int coffeeBrandId)
    {
        this.coffeeBrandId = coffeeBrandId;
    }

    public int getStampsGiven()
    {
        return stampsGiven;
    }

    public void setStampsGiven(int stampsGiven)
    {
        this.stampsGiven = stampsGiven;
    }

    public int getSubscriberCoffeesSold()
    {
        return subscriberCoffeesSold;
    }

    public void setSubscriberCoffeesSold(int subscriberCoffeesSold)
    {
        this.subscriberCoffeesSold = subscriberCoffeesSold;
    }

    public List<User> getCoffeShopUsers()
    {
        return coffeShopUsers;
    }

    public void setCoffeShopUsers(List<User> coffeShopUsers)
    {
        this.coffeShopUsers = coffeShopUsers;
    }

    public String getCoffeShopName()
    {
        return coffeShopName;
    }

    public void setCoffeShopName(String coffeShopName)
    {
        this.coffeShopName = coffeShopName;
    }
}
