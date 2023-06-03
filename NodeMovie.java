package src.FilmApa;


/*
Class NodeMovie berfungsi untuk menyimpan data movie  yang nantinya akan digunakan sebagai node untuk menyimpan data dari movie
*/
class NodeMovie {
    //Deklarasi ID untuk menyimpan ID movie
    private String ID;
    //Deklarasi title untuk menyimpan judul
    private String title;
    //Deklarasi array untuk menyimpan genre
    private String[] genre;
    //Deklarasi rating untuk menyimpan rating
    private double rating;

    /*
    getID, getTitle, getGenre, getRating
    function di atas berfungsi untuk mengambil data yang ada di class NodeMovie karena propertinya bertipe private
    */
    public String getID() {
        return ID;
    }

    public String getTitle() {
        return title;
    }

    public String[] getGenre() {
        return genre;
    }

    public double getRating() {
        return rating;
    }

    //Deklarasi constructor
    public NodeMovie(String ID, String title, String[] genre, double rating) {
        this.ID = ID;
        this.title = title;
        this.genre = genre;
        this.rating = rating;
    }


}
