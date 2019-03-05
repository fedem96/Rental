package com.federicomagnolfi;

public class Test
{
    public static void main(String[] args)
    {

        User user1 = new User("FedeMagno");
        User user2 = new User("GiuliF");
        User user3 = new User("Stefano");
        User user4 = new User("Andrea");

        user1.rentCar();
        user2.rentCar();
        user3.rentCar();
        user4.rentCar();

        user1.useCar();
        user2.useCar();
        user3.useCar();
        user4.useCar();

        user1.returnCar();
        user2.returnCar();
        user3.returnCar();
        user4.returnCar();

    }
}
