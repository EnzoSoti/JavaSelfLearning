public class main {
    public static void main(String[] args) {
        
        Personality p1 = new Personality("Alice", 25, "INTJ", "Reading, Hiking", "1984, Brave New World", "Inception, The Matrix");
        p1.displayDetails();

        p1.setHobbies("Reading, Hiking, Painting");
        System.out.println("\nUpdated Hobbies: " + p1.getHobbies());
        

    }
}
