package entity;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Date;

import CoffeeRest.rest.GetBrandByID;
import kasper.pagh.keebin.R;

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
    private int brandId;

    public LoyaltyCard(CoffeeBrand brand, CoffeeShop coffeeShop, Date dateIssued, boolean isValid, boolean readyForFreeCoffee, int numberOfCoffeesBought)
    {
        this.brand = brand;
        this.coffeeShop = coffeeShop;
        this.dateIssued = dateIssued;
        this.isValid = isValid;
        this.readyForFreeCoffee = readyForFreeCoffee;
        this.numberOfCoffeesBought = numberOfCoffeesBought;
    }

    public LoyaltyCard(int brandId, String numberofBeans){
        this.brandId = brandId;
        this.numberofBeans = numberofBeans;
    }


    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getNumberofBeans() {
        return numberofBeans;
    }

    public void setNumberofBeans(String numberofBeans) {
        this.numberofBeans = numberofBeans;
    }

    public int getBrand(int brandId) {

        return brandId;
    }

    public void setBrand(int brandId)
    {
        this.brandId = brandId;
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
