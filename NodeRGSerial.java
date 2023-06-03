package src.FilmApa;


/*
Class NodeRGSerial berfungsi untuk menyimpan data rgSerial  yang nantinya akan digunakan di feature RGSERIAL
*/
public class NodeRGSerial {
    //Deklarasi rating
    private double rating;
    //Deklarasi arraylist untuk menyimpan genre film
    private ArrayList <String> genres;


    /*
    getRating, getGenres
    function di atas berfungsi untuk mengambil data yang ada di class NodeMovie karena propertinya bertipe private
    */
    public double getRating() {
        return rating;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    //Deklarasi constructor
    public NodeRGSerial(double rating, ArrayList <String> genres) {
        this.rating = rating;
        this.genres = genres;
    }
}
