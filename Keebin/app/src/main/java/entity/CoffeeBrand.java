package entity;

import java.util.List;

/**
 * Created by kaspe on 2016-10-26.
 */

public class CoffeeBrand
{
    private int databaseId;
    private String brandName;
    private int coffeesNeeded;
    private String coffeeBrandCode;
    private List<CoffeeShop> branches;
    private int totalCoffeeStampsFromAllBranches;

    public CoffeeBrand(int databaseId, String brandName, int coffeesNeeded, String coffeeBrandCode, int totalCoffeeStampsFromAllBranches)
    {
        this.databaseId = databaseId;
        this.brandName = brandName;
        this.coffeesNeeded = coffeesNeeded;
        this.coffeeBrandCode = coffeeBrandCode;
        this.totalCoffeeStampsFromAllBranches = totalCoffeeStampsFromAllBranches;
    }

    public int getDatabaseId()
    {
        return databaseId;
    }

    public void setDatabaseId(int databaseId)
    {
        this.databaseId = databaseId;
    }

    public String getBrandName()
    {
        return brandName;
    }

    public void setBrandName(String brandName)
    {
        this.brandName = brandName;
    }

    public int getCoffeesNeeded()
    {
        return coffeesNeeded;
    }

    public void setCoffeesNeeded(int coffeesNeeded)
    {
        this.coffeesNeeded = coffeesNeeded;
    }

    public String getCoffeeBrandCode()
    {
        return coffeeBrandCode;
    }

    public void setCoffeeBrandCode(String coffeeBrandCode)
    {
        this.coffeeBrandCode = coffeeBrandCode;
    }

    public List<CoffeeShop> getBranches()
    {
        return branches;
    }

    public void setBranches(List<CoffeeShop> branches)
    {
        this.branches = branches;
    }

    public int getTotalCoffeeStampsFromAllBranches()
    {
        return totalCoffeeStampsFromAllBranches;
    }

    public void setTotalCoffeeStampsFromAllBranches(int totalCoffeeStampsFromAllBranches)
    {
        this.totalCoffeeStampsFromAllBranches = totalCoffeeStampsFromAllBranches;
    }
}
