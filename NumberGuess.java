import java.util.Random;
import java.util.Scanner;
import java.util.InputMismatchException;

// Define an abstract class Game with a private variable maxAttempts and a constructor to initialize it.
// Also define an abstract method play and a concrete method displayMessage.
abstract class Game {
    private int maxAttempts;

    public Game(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public abstract void play();

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }
}

// Define an interface Hint with a method provideHint.
interface Hint {
    void provideHint(int guess);
}

// Define a class GuessTheNumber that extends Game and implements Hint.
class GuessTheNumber extends Game implements Hint {
    private int randomNumber;
    private int minNumber;
    private int maxNumber;

    // Define a constructor to initialize the instance variables and set the random number.
    public GuessTheNumber(int maxAttempts, int minNumber, int maxNumber) {
        super(maxAttempts);
        this.minNumber = minNumber;
        this.maxNumber = maxNumber;
        randomNumber = new Random().nextInt(maxNumber - minNumber + 1) + minNumber;
    }

    // Define the play method to implement the game logic.
    public void play() {
        Scanner scanner = new Scanner(System.in);
        int attempts = 0;
        int score = 100;
        
        // Loop until the maximum number of attempts is reached or the number is guessed correctly.
        while (attempts < getMaxAttempts()) {
            System.out.print("Guess a number between " + minNumber + " and " + maxNumber + ": ");
            try {
                int guess = scanner.nextInt();
                // Check if the input is valid.
                if (guess < minNumber || guess > maxNumber) {
                    displayMessage("Invalid input. Please enter an integer between " + minNumber + " and " + maxNumber + ".");
                    continue;
                }
                attempts++; // Increasing the number of attempts.
                
                // Check if the number is guessed correctly.
                if (guess == randomNumber) {
                    // Calculate bonus points based on the number of attempts.
                    int bonus = 0;
                    if (attempts <= getMaxAttempts() / 2) {
                        bonus = 50;
                    } else if (attempts <= getMaxAttempts() * 3 / 4) {
                        bonus = 20;
                    }
    
                    score += bonus; // Add bonus points to the score.
                    displayMessage("Congratulations! You guessed the number in " + attempts + " attempts.");
                    displayMessage("Your score is " + score + ".");
                    return;
                } else if (guess < randomNumber) {
                    provideHint(guess); // Provide a hint to the player.
                    displayMessage("The actual number is greater than your guess.");
                } else {
                    provideHint(guess);
                    displayMessage("The actual number is less than your guess.");
                }
    
                score -= 10; // Reduce score by 10 for every guess.
            } catch (InputMismatchException e) {
                displayMessage("Invalid input. Please enter an integer between " + minNumber + " and " + maxNumber + ".");
                scanner.nextLine(); 
            }
        }
        
        // The player failed to guess the number within the maximum number of attempts.
        displayMessage("You failed to guess the number in " + getMaxAttempts() + " attempts. The actual number was " + randomNumber + ".");
        displayMessage("Your score is 0.");
    }
    

    public void provideHint(int guess) {
        if (randomNumber > guess) {
            displayMessage("Hint: The number is greater than " + guess + ".");
        } else {
            displayMessage("Hint: The number is less than " + guess + ".");
        }
    }    
}

public class NumberGuess {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        do {
            try {
                System.out.print("Enter the maximum number of attempts: ");
                int maxAttempts = scanner.nextInt();

                System.out.print("Enter the lower bound of the range: ");
                int lowerBound = scanner.nextInt();

                System.out.print("Enter the upper bound of the range: ");
                int upperBound = scanner.nextInt();

                GuessTheNumber game = new GuessTheNumber(maxAttempts, lowerBound, upperBound);
                game.play();
            } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            }
            System.out.print("Do you want to play again? (Y/N): ");
            String playAgain = scanner.next();
            if (!playAgain.equalsIgnoreCase("Y")) {
                break;
            }
        }
        while (true);
    
        System.out.println("Thanks for playing!");
    }
}