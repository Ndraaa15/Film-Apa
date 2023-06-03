package src.FilmApa;


/*
Class ini berfungsi berfungsi untuk tempat method-method yang digunakan untuk membantu proses memilah input.
Dengan adanya class ini bertujuan untuk membuat main function dari program ini menjadi lebih bersih.
*/
class Helper {
    //Function ini berfungsi untuk melakukan pemisahan line berdasarkan spasi dengan maksimal 2 bagian.
    //Funtion ini digunakan untuk memisahkan command dan isi commandnya
    //Contoh :
    //INSERT 001|A|7.3|ANIMATION,ADVENTURE
    //element [0] = INSERT
    //element [1] = 001|A|7.3|ANIMATION,ADVENTURE
    static String [] splitLine (String line){
        return line.split(" ", 2);
    }


    //Function ini berfungsi untuk melakukan pemisahan elemen berdasarkan '|' dari isi dari command
    //Contoh :
    //001|A|7.3|ANIMATION,ADVENTURE
    //elemen [0] = 001
    //elemen [1] = A
    //elemen [2] = 7.3
    //elemen [3] = ANIMATION,ADVENTURE
    static String [] splitMovieElement (String detailMovie){
        return detailMovie.split("\\|");
    }


    //Function ini berfungsi untuk melakukan pemisahan elemen berdasaarkan ',' pada bagian genre
    //Contoh :
    //ANIMATION,ADVENTURE
    //elemen [0] = ANIMATION
    //elemen [1] = ADVENTURE
    static String [] splitGenre (String genreMovie){
        String [] genres = genreMovie.split(",");

        String [] newGenres = new String[genres.length];

        for (int i = 0; i < genres.length; i++) {
            newGenres[i] = genres[i].toUpperCase();
        }

        return newGenres;
    }


    //Function ini berfungsi untuk melakukan pemisahan element berdasarkan spasi
    //Contoh :
    //007 008
    //elemen [0] = 007
    //elemen [1] = 008
    static String [] splitSpace (String input){
        return input.split(" ");
    }


    //Function ini berfungsi untuk melakukan menampilkan output dari input-input yang telah dimasukkan sebelumnya.
    static void printResult (ArrayList <String> result){
        for (int i = 0; i < result.size(); i++) {
            System.out.println(result.get(i));
        }
    }


    //Function ini berfungsi untuk melakukan pengecekan format input dari perintah insert
    static int checkInsertFormat (String line){
        try{
            String [] detailMovie = splitMovieElement(line);

            //Check movieID
            String movieID = detailMovie[0];

            int id = Integer.parseInt(movieID);

            if (movieID.isEmpty() || id < 0) {
                return -1;
            }

            //Check movie title
            String movieTitle = detailMovie[1];
            if (movieTitle.isEmpty()) {
                return -1;
            }

            //Check rating movie
            String ratingStr = detailMovie[2];
            double rating = Double.parseDouble(ratingStr);

            if (rating < 0 || ratingStr.isBlank()){
                return -1;
            }

            String genres = detailMovie[3];

            //Check genres movie
            String [] movieGenre = Helper.splitGenre(genres);

            if (genres.isBlank()){
                return -1;
            }

            for (String s : movieGenre) {
                if (s.isBlank()) {
                    return -1;
                }
            }

            return 0;
        }catch (Exception e){
            return -1;
        }
    }


    //Function ini berfungsi untuk melakukan pengeckan terhadap format perintah recommend
    static int checkRecommendFormat (String line){
        try{
            String[] movies = splitSpace(line);

            String movieID = (movies[0]);

            String n = movies[1];

            int totalMovie = Integer.parseInt(n);

            int id = Integer.parseInt(movieID);

            if (id < 0 || movieID.isBlank() || n.isBlank()){
                return -1;
            }

            return 0;

        }catch (Exception e){
            return -1;
        }
    }


    //Function ini berfungsi untuk melakukan pengecekan terhadap format perintah serial count
    static int checkSerialCountFormat (String line){
        try{
            String [] moviesID = Helper.splitSpace(line);

            String movieID1 = (moviesID[0]);

            String movieID2 = (moviesID[1]);

            int id1 = Integer.parseInt(movieID1);
            int id2 = Integer.parseInt(movieID2);

            if (id1 < 0 || id2 < 0 || movieID1.isBlank() || movieID2.isBlank()){
                return -1;
            }

            return 0;

        }catch (Exception e){
            return -1;
        }
    }

    //Function ini berfungsi untuk melakukan pengecekan terhadap format perintah sequel
    static int checkSequelFormat (String line){
        try{
            String [] moviesID = Helper.splitSpace(line);

            String movieID1 = (moviesID[0]);

            String movieID2 = (moviesID[1]);

            int id1 = Integer.parseInt(movieID1);
            int id2 = Integer.parseInt(movieID2);

            if (id1 < 0 || id2 < 0 || movieID1.isBlank() || movieID2.isBlank()){
                return -1;
            }

            return 0;

        }catch (Exception e){
            return -1;
        }
    }
}
