/**
 * A quick test of Java Optionals
 */
import java.util.Optional;

public class Optionals{

     public static void main(String []args){
        Optional<String> bar = getBar();
        System.out.println(foo(bar));
     }
     
     public static String foo(Optional<String> bar) {
         return (bar.isPresent()) ? "thanks for the " + bar.get(): "where's my bar?!";
     }
     
     public static Optional<String> getBar() {
         int rand = (int) ((Math.random() * 10) + 1);
         return (rand < 5) ? Optional.of("bar"): Optional.empty();
     }
}
