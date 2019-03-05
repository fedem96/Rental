package com.federicomagnolfi;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by federico on 29/01/18.
 */
public final class User implements Observer
{
    private final String name;
    private CarRental.Car car;

    public User(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        // returns the name, blue colored
        return "\033[34m" + name + "\033[0m";
    }

    CarRental.Car getCar()
    {
        return car;
    }

    void setCar(CarRental.Car car)
    {
        this.car = car;
    }

    public void useCar()
    {
        // check if this user does have a car
        if(car != null)
            // if he has a car, he use it
            car.use(this, (int) (1 + Math.random()*20));
        else
            System.out.println("User " + getName() + " doesn't have a car to use");
    }

    @Override
    public void update(Observable observable, Object o)
    {   // receives the notification that a car is available
        System.out.println("User " + getName() + " receives the notification");
        CarRental.getInstance().deleteObserver(this);    // unsubscribes from list
        // now he can ask to rent a car again...
    }

    public boolean rentCar()
    {
        return CarRental.getInstance().rentCar(this);
    }

    public boolean returnCar()
    {
        return CarRental.getInstance().returnCar(this);
    }
}
