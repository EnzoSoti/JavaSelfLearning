public class Personality {

    private String name;
    private int age;
    private String personalityType;
    private String hobbies;
    private String favoriteBooks;
    private String favoriteMovies;

    public Personality(String name, int age, String personalityType, String hobbies, String favoriteBooks, String favoriteMovies) {
        this.name = name;
        this.age = age;
        this.personalityType = personalityType;
        this.hobbies = hobbies;
        this.favoriteBooks = favoriteBooks;
        this.favoriteMovies = favoriteMovies;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPersonalityType() {
        return personalityType;
    }

    public void setPersonalityType(String personalityType) {
        this.personalityType = personalityType;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getFavoriteBooks() {
        return favoriteBooks;
    }

    public void setFavoriteBooks(String favoriteBooks) {
        this.favoriteBooks = favoriteBooks;
    }

    public String getFavoriteMovies() {
        return favoriteMovies;
    }

    public void setFavoriteMovies(String favoriteMovies) {
        this.favoriteMovies = favoriteMovies;
    }

    public void displayDetails() {
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Personality Type: " + personalityType);
        System.out.println("Hobbies: " + hobbies);
        System.out.println("Favorite Books: " + favoriteBooks);
        System.out.println("Favorite Movies: " + favoriteMovies);
    }

}
