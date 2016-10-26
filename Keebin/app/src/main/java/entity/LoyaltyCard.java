package entity;

import java.util.Date;

/**
 * Created by kaspe on 2016-10-26.
 */


public class LoyaltyCard
{
    private CoffeeBrand brand;
    private CoffeeShop coffeeShop; //If some brand wants to issue their loyalty cards to ONLY specific branches, otherwise null.
    private int numberOfCoffeesBought;
    private Date dateIssued;
    private boolean isValid;
    private boolean readyForFreeCoffee;
}
