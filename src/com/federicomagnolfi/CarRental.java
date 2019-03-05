package com.federicomagnolfi;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by federico on 29/01/18.
 */
public final class CarRental extends Observable
{
    public static final class Car
    {

        private final String plateNumber;   // plate number of the car
        private int kilometers;             // kilometers done by the car
        private boolean inUse;              // true if someone has rented this car
        private User renter;                // user that rented this car

        private Car(String plateNumber)
        {   // constructor is private, only the CarRental can instantiate a Car
            this.plateNumber = plateNumber;
            kilometers = 0;
            inUse = false;
        }

        // private method, only the CarRental can change the renter of the car
        private void setRenter(User renter)
        {
            this.renter = renter;
        }

        public void use(User user, int kilometers)
        {
            // check appropriate use of the car
            if(user != renter)
                throw new Error("Only the renter can use the car!");
            // increment kilometers counter
            this.kilometers += kilometers;

            System.out.println("Car " + getPlateNumber() + " used for " + kilometers + " km by user " + user.getName() + ". Total km of the car: " + this.kilometers);
        }

        public String getPlateNumber()
        {
            // returns the plate number, yellow colored
            return "\033[32m" + plateNumber + "\033[0m";
        }
    }


    private static CarRental instance;          // only instance of the CarRental

    private final ArrayList<Car> cars;          // list of cars owned by the CarRental

    private CarRental()
    {
        final int carsNumber = 2;               // total cars owned by the CarRental
        cars = new ArrayList<>();
        for (int i = 0; i < carsNumber; i++)
        {
            cars.add(new Car("IT" + (101+i + "FM")));
        }
    }

    public static CarRental getInstance()
    {   // returns the unique instance
        if(instance == null)
            instance = new CarRental();
        return instance;
    }

    // returns true if car is rented, false otherwise
    boolean rentCar(User renter)
    {
        for(Car car: cars)
        {
            if(car.renter == renter)
            {
                System.out.println("Every user can rent one car at most");
                return false;
            }
        }
        for(Car car: cars)
        {   // search for an available car
            if(!car.inUse)
            {   // if an available car is found, it's assigned to the user
                car.inUse = true;
                car.setRenter(renter);
                renter.setCar(car);
                System.out.println("Car " + car.getPlateNumber() + " assigned to " + renter.getName());
                return true;
            }
        }
        // if no car is available
        addObserver(renter);
        System.out.println("User " + renter.getName() + " will be notified as soon as a car returns available");
        return false;
    }

    // returns true if the car is effectively returned, false otherwise
    boolean returnCar(User user)
    {
        Car car = user.getCar();
        if (cars.indexOf(car) != -1)
        {   // car is prepared to be rented again
            car.inUse = false;
            car.setRenter(null);
            user.setCar(null);  // user lose the reference to use the car
            System.out.println("User " + user.getName() + " has returned the car " + car.getPlateNumber());
            setChanged();
            notifyObservers();
            return true;
        }
        System.out.println("The user did not rent a car, so he can't return a car");
        return false;
    }

}
