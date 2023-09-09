import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Toy {
    private int id;
    private String name;
    private int quantity;
    private int weight;

    public Toy(int id, String name, int quantity, int weight) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void decreaseQuantity() {
        if (this.quantity > 0) {
            this.quantity--;
        }
    }
}

public class ToyRaffle {
    private List<Toy> toys = new ArrayList<>();
    private List<Toy> wonToys = new ArrayList<>();

    public void addToy(Toy toy) {
        toys.add(toy);
    }

    public void raffleToy() {
        int totalWeight = toys.stream().mapToInt(Toy::getWeight).sum();

        if (totalWeight == 0) {
            System.out.println("Нет игрушек для розыгрыша!");
            return;
        }

        int randomValue = new Random().nextInt(totalWeight);
        int sum = 0;
        Toy chosenToy = null;

        for (Toy toy : toys) {
            sum += toy.getWeight();
            if (randomValue < sum) {
                chosenToy = toy;
                break;
            }
        }

        if (chosenToy != null && chosenToy.getQuantity() > 0) {
            chosenToy.decreaseQuantity();
            wonToys.add(chosenToy);
            System.out.println("Розыграна игрушка: " + chosenToy.getName());
        } else {
            System.out.println("Игрушка закончилась!");
        }
    }

    public void getPrizeToy() {
        if (wonToys.isEmpty()) {
            System.out.println("Нет выигранных игрушек!");
            return;
        }

        Toy toy = wonToys.get(0);
        wonToys.remove(0);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("won_toys.txt", true))) {
            writer.write("Выигранная игрушка: " + toy.getName() + "\n");
            System.out.println("Игрушка " + toy.getName() + " выдана!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ToyRaffle raffle = new ToyRaffle();
        raffle.addToy(new Toy(1, "Мягкий медведь", 10, 25));
        raffle.addToy(new Toy(2, "Лего", 5, 50));
        raffle.addToy(new Toy(3, "Головоломка", 7, 25));

        raffle.raffleToy();
        raffle.raffleToy();
        raffle.getPrizeToy();
    }
}
