package src.FilmApa;

import java.text.DecimalFormat;

class FilmApaApps {
    //Deklarasi arraylist genres untuk menyimpan nama genre yang ada
    ArrayList <String> genres = new ArrayList<>();

    //Deklarasi arraylist movies untuk menyimpan node movie yang telah dimasukkan sebelumnya
    ArrayList <NodeMovie> movies = new ArrayList<>();

    //Deklarasi nested arraylist yang digunakan untuk adjacency list
    //Mewakili genre dimana di dalamnya terdapat beberapa movie
    ArrayList <ArrayList<Integer>> genreMovies = new ArrayList<>();

    //Deklrasi nested arraylist sequelMovies untuk adjacency list
    //Mewakili movie yang mewakili sequel movie lain
    ArrayList <ArrayList<Integer>> sequelMovies = new ArrayList<>();

    //Function ini berfungsi untuk menambahkan movie ke dalam arratlist yang sudah tersedia.
    public String insertMovie (String movieID, String movieTitle, String [] movieGenre, double rating){
        NodeMovie movie = new NodeMovie(movieID, movieTitle, movieGenre, rating);

        if (getMovieByID(movieID) != null){
            return "-1";
        }


        String [] genresNewMovie = movie.getGenre();

        movies.add(movie);

        sequelMovies.add(new ArrayList<>());

        for (String s : genresNewMovie) {
            if (!genres.contains(s)) {
                genres.add(s);
                genreMovies.add(new ArrayList<>());
            }
        }

        int indexMovie = getIndexMovie(movie);

        for (String s : genresNewMovie) {
            for (int j = 0; j < genres.size(); j++) {
                if (s.equals(genres.get(j))) {
                    genreMovies.get(j).add(indexMovie);
                }
            }
        }

        return movie.getTitle() + " INSERTED";
    }


    //Function ini bertujuan untuk menghubungkan satu movie dengan movie yang lain dengan menggunakan adjacency list
    public String insertSequelMovie (String movieID1, String movieID2){
        //Mendapatkan index dari kedua movie berdasarkan movieID
        int indexMovie1 = getIndexMovie(getMovieByID(movieID1));
        int indexMovie2 = getIndexMovie(getMovieByID(movieID2));


        //Apabila movieID tidak terdaftar maka akan mengembalikan sequel not_connected
        if (indexMovie1 == -1 || indexMovie2 == -1){
            return "SEQUEL NOT_CONNECTED";
        }

        //Apabila movieID sudah terdapat sequel maka akan mengembalikan sequel not_connected
        if (sequelMovies.get(indexMovie1).get(0) != null){
            return "SEQUEL NOT_CONNECTED";
        }

        //Menambahkan nilai ke adjacency list khusus sequel
        sequelMovies.get(indexMovie1).add(indexMovie2);

        return ("SEQUEL " + movies.get(indexMovie1).getTitle() + " TO " + movies.get(indexMovie2).getTitle());
    }

    //Function yang bertujuan untuk mencari genre dengan memiliki jumlah movie terbanyak
    public ArrayList <String> mostGenre (int n){
        PriorityQueue <NodeGenre> pq = new PriorityQueue<>();

        //Memasukkan nodeGenre (genre, total movie) ke dalam priority queue untuk dibandingkan
        for (int i = 0; i < genreMovies.size(); i++) {
            pq.enqueue(new NodeGenre(genres.get(i), genreMovies.get(i).size()));
        }

        //Deklarasi arraylist untuk menyimpan hasil sorting
        ArrayList <String> sortedGenre = new ArrayList<>();

        //Pengecekan apabila nilai yang ingin ditampilkan lebih besar dari total genre yang ada
        n = n < pq.size() ? n : pq.size;

        //Melakukan pengambilan data dan meletekkannya di arraylist
        for (int i = 0; i < n; i++) {
            NodeGenre curr = pq.dequeue();
            sortedGenre.add(curr.genre + " (" + curr.totalMovies + ")");
        }

        pq.clear();

        return sortedGenre;
    }


    //Function serialCount bertujuan untuk menghitung path atau sequel dari movie 1 ke movie 2
    public String serialCount (String movieID1, String movieID2){
        //Mendapatkan index dari kedua movie berdasarkan movieID
        int indexMovie1 = getIndexMovie(getMovieByID(movieID1));
        int indexMovie2 = getIndexMovie(getMovieByID(movieID2));

        //Apabila movieID tidak terdaftar maka akan mengembalikan nilai -2
        if (indexMovie1 == -1 || indexMovie2 == -1){
            return "-2";
        }

        //Arraylist untuk menampung hasil pencarian movie yang bersequel
        ArrayList <Integer> sc = searchForSerialCount(indexMovie1, indexMovie2);

        //Melakukan pengecekan apabila dalam arraylist hanya terdapat 1 elemen maka akan mengembalikan -1 karena tidak memiliki path
        //Apabila tidak  maka akan mengambil total movie serta judul movie yang berserial dan dikembalikan nilainya
        if (sc.size() == 1){
            return  "-1";
        }else {
            String result = "";
            result += (sc.size() + " ");
            for (int i = 0; i < sc.size(); i++) {
                if (i == sc.size() - 1){
                    result += (movies.get(sc.get(i)).getTitle() + " ");
                }else {
                    result += (movies.get(sc.get(i)).getTitle() + ",");
                }
            }
            return result;
        }
    }

    //Function private searchForSerialCount berfungsi untuk mencari movie 1 ke movie 2 apakah saling berkaitan atau tidak
    //Metode di  function ini menggunakan Breadth First Search
    private ArrayList<Integer> searchForSerialCount (int indexStart, int indexEnd){
        Queue <Integer> queue = new Queue<>();

        ArrayList <Integer> result = new ArrayList<>();

        queue.enqueue(indexStart);

        while (!queue.isEmpty()){
            int curr = queue.dequeue();
            result.add(curr);
            if (curr == indexEnd){
                break;
            }

            if (sequelMovies.get(curr).get(0) != null){
                queue.enqueue(sequelMovies.get(curr).get(0));
            }
        }
        return result;
    }


    //Function ini berfungsi untuk menhitung jumlah movie yang memiliki sqeuel
    public int numSerial (){
        return (searchForNumSerial());
    }

    //Function ini bertujuan untuk melakukan searching pada adjacency list dan menghitung jumlah movie yang memiliki sequel
    //Metode searching dibawah menggunakan breadth first search
    private int searchForNumSerial (){
        int totalSerial = 0;

        int counter = 0;

        Queue <Integer> queue = new Queue<>();

        boolean [] check = new boolean[movies.size()];


        for (int i = 0; i < movies.size(); i++) {

            if (!check[i]){
                check[i] = true;
                queue.enqueue(i);
                while (!queue.isEmpty()){
                    int curr = queue.dequeue();
                    if (curr == -1){
                        break;
                    } else if (sequelMovies.get(curr).get(0) == null) {
                        break;
                    } else if (sequelMovies.get(curr).get(0) != null && !check[sequelMovies.get(curr).get(0)]){
                        check[sequelMovies.get(curr).get(0)] = true;
                        queue.enqueue(sequelMovies.get(curr).get(0));
                        counter++;
                    }

                }
            }

            if (counter != 0){
                totalSerial++;
                counter = 0;
            }

        }
        return totalSerial;
    }


    //Function di bawah berfungsi untuk menampilkan rating dan genre dari movie yang memiliki sequel
    public ArrayList <String> rgSerial (){
        //Deklarasi nested arraylist untuk menyimpan movie yang berserial
        ArrayList <ArrayList<Integer>> listSerial = searchForRGSerial();

        //Pengecekan apakah tidak terdapat elemen di dalam array
        if (listSerial.isEmpty()){
            return new ArrayList<>();
        }

        //Deklarasi arraylist untuk menyimpan NodeRGSerial
        ArrayList <NodeRGSerial> listRGSerial = new ArrayList<>();


        //Perulangan yang digunakan untuk menghitung rating serta genre apa saja yang ada dari movie berserial.
        //Kemudian di masukkan ke dalam arraylist melalui Node RGSerial
        for (int i = 0; i < listSerial.size(); i++) {
            double totalRating = 0;
            ArrayList<String> genres = new ArrayList<>();

            for (int j = 0; j < listSerial.get(i).size(); j++) {
                totalRating += movies.get(listSerial.get(i).get(j)).getRating();

                for (int k = 0; k < movies.get(listSerial.get(i).get(j)).getGenre().length; k++) {

                    if (!genres.contains(movies.get(listSerial.get(i).get(j)).getGenre()[k])){
                        genres.add(movies.get(listSerial.get(i).get(j)).getGenre()[k]);
                    }

                }
            }

            totalRating = (totalRating / listSerial.get(i).size());

            ArrayList<String> sortedGenres = sortGenre(genres);

            listRGSerial.add(new NodeRGSerial(formatRating(totalRating), sortedGenres));
        }

        //Deklarasi arraylist untuk menyimpan hasil sorting dari arraylist sebelumnya
        ArrayList <NodeRGSerial> sortedListRGSerial = rgSerialSort(listRGSerial);

        //Deklarasi arraylist untuk menyimpan hasil luaran.
        ArrayList <String> result = new ArrayList<>();

        //Perulangan untuk menyimpan hasil luaran sortedListRGSerial sesuai format luaran.
        for (int i = 0; i < sortedListRGSerial.size(); i++) {
            String temp =  "";
            temp += ("SERIAL-" + (i+1) + ": (" + sortedListRGSerial.get(i).getRating() + ") ");
            for (int j = 0; j < sortedListRGSerial.get(i).getGenres().size(); j++) {
                if (j == sortedListRGSerial.get(i).getGenres().size() - 1){
                    temp += (sortedListRGSerial.get(i).getGenres().get(j));
                }else {
                    temp += (sortedListRGSerial.get(i).getGenres().get(j) + ",");
                }
            }
            result.add(temp);
//            if (i != sortedListRGSerial.size() - 1){
//                System.out.println();
//            }
        }

        return result;

    }


    //Function private rgSerialSort merupakan function yang digunakan untuk melakukan sorting (bubble sort) pada arraylist RGSerial
    //Dimana akan ditentukan jumlah genre yang lebih banyak akan ditampilkan terlebih dahulu
    //Apabila jumlah genre yang dihasilkan sama maka akan dibandingkan berdasarkan ratingnya
    private  ArrayList <NodeRGSerial> rgSerialSort (ArrayList <NodeRGSerial> listRGSerial){

        for (int i = 0; i < listRGSerial.size() - 1; i++) {
            for (int j = 0; j < listRGSerial.size() - i - 1; j++) {
                if (listRGSerial.get(j).getGenres().size() < listRGSerial.get(j + 1).getGenres().size()){

                    NodeRGSerial temp = listRGSerial.get(j);
                    listRGSerial.set(j, listRGSerial.get(j + 1));
                    listRGSerial.set(j + 1, temp);

                } else if (listRGSerial.get(j).getGenres().size() == listRGSerial.get(j + 1).getGenres().size()) {
                    if (listRGSerial.get(j).getRating() < listRGSerial.get(j + 1).getRating()){

                        NodeRGSerial temp = listRGSerial.get(j);
                        listRGSerial.set(j, listRGSerial.get(j + 1));
                        listRGSerial.set(j + 1, temp);


                    }
                }
            }
        }

        return listRGSerial;
    }

    //Function private formatRating berfungsi untuk melakukan formating pada rating yang dihasilkan dengan 1 angka
    //dibelakang koma dan dibulatkan.
    private double formatRating (double rating){
        String formattedNumber = String.format("%.1f", rating).replaceAll(",", ".");
        return Double.parseDouble(formattedNumber);
    }


    //Function private sortGenre berfungsi untuk melakukan sorting (bubble sort) pada genre dari sebuah film agar diurutkan berdasarkan alphabet
    private ArrayList<String>sortGenre (ArrayList<String> genres){

        for (int i = 0; i < genres.size(); i++) {
            for (int j = 0; j < genres.size() - i - 1; j++) {
                if (genres.get(j).compareTo(genres.get(j + 1)) > 0){
                    String temp = genres.get(j);
                    genres.set(j, genres.get(j + 1));
                    genres.set(j + 1, temp);
                }
            }
        }

        return genres;
    }


    //Function private searchForRGSerial berfungsi untuk melakukan searching pada adjacency list
    //dimana tujuan untuk menjadi film mana saja yang saling terhubung dan menyimpannya dalam nested arraylist
    private ArrayList<ArrayList<Integer>> searchForRGSerial (){
        ArrayList <ArrayList<Integer>> listSerial = new ArrayList<>();


        Queue <Integer> queue = new Queue<>();

        boolean [] check = new boolean[movies.size()];


        for (int i = 0; i < movies.size(); i++) {
            ArrayList <Integer> serial = new ArrayList<>();

            if (!check[i]){
                check[i] = true;
                queue.enqueue(i);
                while (!queue.isEmpty()){
                    int curr = queue.dequeue();
                    serial.add(curr);
                    if (curr == -1){
                        break;
                    } else if (sequelMovies.get(curr).get(0) == null) {
                        break;
                    } else if (sequelMovies.get(curr).get(0) != null && !check[sequelMovies.get(curr).get(0)]){
                        check[sequelMovies.get(curr).get(0)] = true;
                        queue.enqueue(sequelMovies.get(curr).get(0));
                    }
                }
            }

            if (serial.size() > 1){
                listSerial.add(serial);
            }
        }

        return listSerial;
    }


    //Function dibawah ini bertujuan untuk menampilkan movie yang direkomendasikan berdasarkan movie yang dipilih
    //dan total jumlah movie yang ingin ditampilkan
    public  ArrayList <String> recommend (String id, int n){
        //Mencari index movie berdasarkan id
        int indexMovie = getIndexMovie(getMovieByID(id));

        //Deklarasi arraylist untuk menyimpan NodeReccomend
        ArrayList <NodeRecommend> recommendList = new ArrayList<>();

        //Melakukan looping yang didalamnya terdapat penghitungan overlap (jumlah genre yang sama)
        //Kemudian dimasukkan ke dalam arraylist NodeRecommend selanjutnya ke arraylist
        for (int i = 0; i < movies.size(); i++) {
            if (i != indexMovie){
                int overlap = checkOverlap(movies.get(indexMovie).getGenre(), movies.get(i).getGenre());
                recommendList.add(new NodeRecommend(overlap, i));
            }
        }

        //Deklarasi arraylist untuk menyimpan hasil sorting recommendList
        ArrayList <NodeRecommend> sortedRecommendList = sortRecommendList(recommendList);

        //Deklarasi arraylist untuk menyimpan hasil luaran dari function ini
        ArrayList <String> result = new ArrayList<>();

        //Perulangan untuk membuat format luaran
        for (int i = 0; i < n; i++) {
            result.add(movies.get(sortedRecommendList.get(i).getIndexMovie()).getID() + " " + movies.get(sortedRecommendList.get(i).getIndexMovie()).getTitle());
        }

        return result;


    }


    //Function private sortRecomendList fungsinya untuk melakukan sorting (bubble sort) pada arraylist yang menyimpan nodeRecommend
    //Dimana sorting dilakukan dengan metode bubble sort dan berdasarkan overlap dan rating yang dimiliki.
    private ArrayList <NodeRecommend> sortRecommendList ( ArrayList <NodeRecommend> recommendList) {

        for (int i = 0; i < recommendList.size(); i++) {

            for (int j = 0; j < recommendList.size() - i -1; j++) {

                if (recommendList.get(j).getOverlap() < recommendList.get(j + 1).getOverlap()){

                    NodeRecommend temp = recommendList.get(j);
                    recommendList.set(j, recommendList.get(j + 1));
                    recommendList.set(j + 1, temp);

                } else if (recommendList.get(j).getOverlap() == recommendList.get(j + 1).getOverlap()) {

                    if (getMovieByIndex(recommendList.get(j).getIndexMovie()).getRating() < getMovieByIndex(recommendList.get(j + 1).getIndexMovie()).getRating()){

                        NodeRecommend temp = recommendList.get(j);
                        recommendList.set(j, recommendList.get(j + 1));
                        recommendList.set(j + 1, temp);

                    }

                }

            }

        }

        return recommendList;
    }


    //Function private checkOverlap ini bertujuan melakukan pengecekan seberapa banyak genre yang sama antara movie 1 dan movie lainnya
    //nantinya overlap ini bertujuan untuk dibandingkan dengan movie lain
    //semakin banyak overlap semakin direkomendasikan
    private int checkOverlap (String [] movie1Genres, String [] movie2Genres){
        int overlap = 0;

        for (String movie1Genre : movie1Genres) {
            for (String movie2Genre : movie2Genres) {
                if (movie1Genre.equals(movie2Genre)) {
                    overlap++;
                }
            }
        }

        return overlap;
    }

    //Function private getMovieByIndex ini bertujuan untuk mendapakatkan movie berdasarkan index di arraylist
    private NodeMovie getMovieByIndex (int index){
        if (index < 0 || index >= movies.size()){
            return null;
        }
        return movies.get(index);
    }

    //Function private getIndexMovie bertujuan untuk mendapatkan index arraylist dari sebuah movie
    private int getIndexMovie (NodeMovie movie){
        if (movie == null){
            return -1;
        }

        for (int i = 0; i < movies.size() ; i++) {
            if (movie.getTitle().equals(movies.get(i).getTitle())){
                return i;
            }
        }
        return -1;
    }


    //Function private getMovieByID bertujuan untuk mendapatkan movie berdasarkan ID yang diberikan
    private NodeMovie getMovieByID (String id){
        for (int i = 0; i < movies.size() ; i++) {
            if (id.equals(movies.get(i).getID())){
                return movies.get(i);
            }
        }
        return null;
    }
}
