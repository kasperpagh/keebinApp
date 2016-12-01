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

    public LoyaltyCard(CoffeeBrand brand, CoffeeShop coffeeShop, Date dateIssued, boolean isValid, boolean readyForFreeCoffee, int numberOfCoffeesBought)
    {
        this.brand = brand;
        this.coffeeShop = coffeeShop;
        this.dateIssued = dateIssued;
        this.isValid = isValid;
        this.readyForFreeCoffee = readyForFreeCoffee;
        this.numberOfCoffeesBought = numberOfCoffeesBought;
    }

    public LoyaltyCard(CoffeeBrand brand, int numberOfCoffeesBought){
        this.brand = brand;
        this.numberOfCoffeesBought = numberOfCoffeesBought;
    }

    public CoffeeBrand getBrand()
    {
        return brand;
    }

    public void setBrand(CoffeeBrand brand)
    {
        this.brand = brand;
    }

    public CoffeeShop getCoffeeShop()
    {
        return coffeeShop;
    }

    public void setCoffeeShop(CoffeeShop coffeeShop)
    {
        this.coffeeShop = coffeeShop;
    }

    public int getNumberOfCoffeesBought()
    {
        return numberOfCoffeesBought;
    }

    public void setNumberOfCoffeesBought(int numberOfCoffeesBought)
    {
        this.numberOfCoffeesBought = numberOfCoffeesBought;
    }

    public Date getDateIssued()
    {
        return dateIssued;
    }

    public void setDateIssued(Date dateIssued)
    {
        this.dateIssued = dateIssued;
    }

    public boolean isValid()
    {
        return isValid;
    }

    public void setValid(boolean valid)
    {
        isValid = valid;
    }

    public boolean isReadyForFreeCoffee()
    {
        return readyForFreeCoffee;
    }

    public void setReadyForFreeCoffee(boolean readyForFreeCoffee)
    {
        this.readyForFreeCoffee = readyForFreeCoffee;
    }
}
