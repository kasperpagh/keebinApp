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
    private String numberofBeans;
    private Date dateIssued;
    private boolean isValid;
    private boolean readyForFreeCoffee;
    private int id;
    private int brandName;
    private String nameOfBrand;

    public LoyaltyCard(CoffeeBrand brand, CoffeeShop coffeeShop, Date dateIssued, boolean isValid, boolean readyForFreeCoffee, int numberOfCoffeesBought)
    {
        this.brand = brand;
        this.coffeeShop = coffeeShop;
        this.dateIssued = dateIssued;
        this.isValid = isValid;
        this.readyForFreeCoffee = readyForFreeCoffee;
        this.numberOfCoffeesBought = numberOfCoffeesBought;
    }

    public LoyaltyCard(int brandName, String numberofBeans){
        this.brandName = brandName;
        this.numberofBeans = numberofBeans;
    }

    public LoyaltyCard(String nameOfBrand, String numberofBeans){
        this.nameOfBrand = nameOfBrand;
        this.numberofBeans = numberofBeans;
    }

    public String getNameOfBrand() {
        return nameOfBrand;
    }

    public void setNameOfBrand(String nameOfBrand) {
        this.nameOfBrand = nameOfBrand;
    }

    public int getBrandName() {
        return brandName;
    }

    public void setBrandName(int brandName) {
        this.brandName = brandName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumberofBeans() {
        return numberofBeans;
    }

    public void setNumberofBeans(String numberofBeans) {
        this.numberofBeans = numberofBeans;
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
