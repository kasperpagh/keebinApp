package entity;

import java.util.Date;
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
    private Date birthday;
    private String sex;
    private int roleId;

    private boolean isSubscriber;
    private int subscriberFreeCoffeeLeft;


    private List<LoyaltyCard> loyaltyCards;




}
