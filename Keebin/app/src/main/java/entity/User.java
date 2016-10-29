package entity;

import java.util.List;

/**
 * Created by kaspe on 2016-10-26.
 */

public class User
{
    private int databseId;
    private String firstName;
    private String lastName;
    private String email;
    private String birthday;
    private String sex;
    private int role;
    private String password;

    private boolean isSubscriber;
    private int subscriberFreeCoffeeLeft;

    public User(int databseId, String firstName, String lastName, String email, String birthday, String sex, int role, boolean isSubscriber, String password)
    {
        this.databseId = databseId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthday = birthday;
        this.sex = sex;
        this.role = role;
        this.isSubscriber = isSubscriber;
        this.password = password;
    }

    public User(String firstName, String lastName, String email, String birthday, String sex, int role, String password)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthday = birthday;
        this.sex = sex;
        this.role = role;
        this.password = password;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    private List<LoyaltyCard> loyaltyCards;

    public int getDatabseId()
    {
        return databseId;
    }

    public void setDatabseId(int databseId)
    {
        this.databseId = databseId;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getBirthday()
    {
        return birthday;
    }

    public void setBirthday(String birthday)
    {
        this.birthday = birthday;
    }

    public String getSex()
    {
        return sex;
    }

    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public int getRole()
    {
        return role;
    }

    public void setRole(int role)
    {
        this.role = role;
    }

    public boolean isSubscriber()
    {
        return isSubscriber;
    }

    public void setSubscriber(boolean subscriber)
    {
        isSubscriber = subscriber;
    }

    public int getSubscriberFreeCoffeeLeft()
    {
        return subscriberFreeCoffeeLeft;
    }

    public void setSubscriberFreeCoffeeLeft(int subscriberFreeCoffeeLeft)
    {
        this.subscriberFreeCoffeeLeft = subscriberFreeCoffeeLeft;
    }

    public List<LoyaltyCard> getLoyaltyCards()
    {
        return loyaltyCards;
    }

    public void setLoyaltyCards(List<LoyaltyCard> loyaltyCards)
    {
        this.loyaltyCards = loyaltyCards;
    }
}
