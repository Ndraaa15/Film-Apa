package src.FilmApa;


/*
Class NodeRecommend berfungsi untuk menyimpan data recommend  yang nantinya akan digunakan di feature RECOMMEND
*/
public class NodeRecommend {
    //Deklarasi overlap -> jumlah genre yang sama
    private int overlap;
    //Deklarasi indexMovie -> untuk menyimpan movieID
    private int indexMovie;

    /*
    getOverlap, getIndexMovie
    function di atas berfungsi untuk mengambil data yang ada di class NodeMovie karena propertinya bertipe private
    */
    public int getOverlap() {
        return overlap;
    }

    public int getIndexMovie() {
        return indexMovie;
    }

    //Deklarasi contructor
    public NodeRecommend(int overlap, int indexMovie) {
        this.overlap = overlap;
        this.indexMovie = indexMovie;
    }
}
