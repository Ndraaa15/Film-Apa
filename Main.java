package src.FilmApa;

/*
 *225150200111006_Gede Indra Adi Brata_1
 *225150200111007_Achmad Fauzi Aranda_2
 *225150207111015_Gratia Yudika Morado Silalahi_3
 */


import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //Arraylist ini berfungsi untuk menyimpan logs dari input yang telah dimasukkan sebelumnya
        ArrayList <String> logs = new ArrayList<>();

        //Objek filmApaApps merupakan object yang berfungsi untuk mengatur segala macam proses yang ada nantinya
        FilmApaApps filmApaApps = new FilmApaApps();

        //Objek Scanner yang bernama sc berfungsi untuk menerima input yang nantinya akan di olah.
        Scanner sc = new Scanner(System.in);

        //Perulangan while berfungsi untuk melakukan perulangan terhadap input yang ada
        while (sc.hasNextLine()){
            //line merupakan raw input yang terima
            //Contoh :
            //INSERT 001|CODER|9.0|ADVENTURE,THRILLER
            String line = sc.nextLine();

            //Kemudian melakukan pengecekan line tersebut apakah EOF atau space kosong
            //Apabila benar maka akan memanggil method printResult (menampilkan semua output log)
            //Setelah itu perulangan akan berhenti karena ditambahkan break
            if(line.isEmpty()){
                Helper.printResult(logs);
                return;
            }

            //Melakukan pemisahan di line menjadi command dan elemen movie;
            String [] lines  = Helper.splitLine(line);

            //Terdapat pengkondisian yang berfungsi untuk melakukan pengecakan command jenis apa yang terdapat pada input
            //Contoh :
            //INSERT, RGSERIAL, NUMSERIAL, SERIAL, DLL

            //INSERT = Menambahkan movie ke dalam objek filmApaApps
            if (lines[0].equals("INSERT")){
                //Melakukan pengecekan apakah lines[1] kosong atau tidak apabila kosong maka akan menambahkan log INVALID FORMAT
                //Karena seharusnya menambahkan 2 movie ID yang ingin dihubungkan
                if (lines.length == 1){
                    logs.add("INVALID FORMAT");
                    continue;
                }

                //Melakukan pemisahan terdalam elemen movie menjadi bagian per bagian dalam bentuk array.
                String [] detailMovie = Helper.splitMovieElement(lines[1]);

                //Menyimpan ID dari movie
                String movieID = detailMovie[0];

                //Menyimpan Title dari movie
                String movieTitle = detailMovie[1];

                //Melakukan pemisahan genre film menjadi bagian per bagian dalam bentuk array.
                String [] movieGenre = Helper.splitGenre(detailMovie[3]);

                //Menyimpan rating dari movie
                double rating = Double.parseDouble(detailMovie[2]);

                //Melakukan pengecekan terhadap format input dari command INSERT
                //Apabila ada yang salah maka akan mengembalikan nilai -1
                int check = Helper.checkInsertFormat(lines[1]);

                //Melakukan proses memasukkan movie ke dalam objek filmApaApps
                //Dan method insert movie mengembalikan nilai berupa string apabila -1 berarti ada yang salah dengan elemen movienya
                String log = filmApaApps.insertMovie(movieID, movieTitle, movieGenre, rating);

                if (log.equals("-1") || check == -1){
                    //Apabila nilai yang dihasilkan log -1 maka akan menambahkan INVALID FORMAT ke dalam logs
                    logs.add("INVALID FORMAT");
                }else {
                    //Apabila nilai yang dihasilkan selain -1 maka akan menambahkan log yang dihasilkan dari method insertMovie
                    logs.add(log);
                }


            //SEQUEL = Menghubungkan 1 movie ke movie lain
            } else if (lines[0].equals("SEQUEL")) {
                //Melakukan pengecekan apakah lines[1] kosong atau tidak apabila kosong maka akan menambahkan log INVALID FORMAT
                //Karena seharusnya menambahkan 2 movie ID yang ingin dihubungkan
                if (lines.length == 1){
                    logs.add("INVALID FORMAT");
                    continue;
                }

                //Melakukan pengecekan apakah lines[1] sesuai format yang diharapkan atau tidak, apabila tidak akan mengembalikan -1
                int check = Helper.checkSequelFormat(lines[1]);

                //Melakukan pengecekan apakah nilai check yang dihasilkan -1 atau tidak. Apabila iya maka akan menambahkan log
                // ("INVALID FORMAT") dan continue (melanjutkan ke input selanjutnya)
                if (check == -1){
                    logs.add("INVALID FORMAT");
                    continue;
                }

                //Melakukan pemisahan spasi terhadap elemen dari lines [0]
                String [] moviesID = lines[1].split(" ");

                //Deklarasi untuk menyimpan 2 movie ID yang berbeda.
                String movieID1 = (moviesID[0]);
                String movieID2 = (moviesID[1]);

                //Menerima log yang dihasilkan setelah memanggil function insertSequelMovie
                 String log = filmApaApps.insertSequelMovie(movieID1, movieID2);

                //Memasukkan log yang telah di hasilkan sebelumnya
                logs.add(log);

            //MOSTGENRE = Menampilkan genre berdasarkan total movienya yang terbanyak dan urutan abjad
            } else if (lines[0].equals("MOSTGENRE")) {
                //Penambahan try catch untuk melakukan handle error apabila terjadi kesalahan saat parsing
                try {
                    //Melakukan pengecekan apakah lines[1] kosong atau tidak apabila kosong maka akan menambahkan log INVALID FORMAT
                    if (lines.length == 1){
                        logs.add("INVALID FORMAT");
                        continue;
                    }

                    //Deklarasi totalGenre untuk menentukan jumlah genre yang akan ditampilkan
                    int totalGenre = Integer.parseInt(lines[1]);

                    //Melakukan pengecekan dengan operator ternary apakah input yang diberikan lebih kecil dari 0 atau tidak
                    //Apabila iya maka akan dibulatkan menjadi 1
                    totalGenre = totalGenre <= 0 ? 1 : totalGenre;

                    //Deklarasi arraylist untuk menyimpan log yang dihasilkan oleh function mostGenre
                    ArrayList <String> log = filmApaApps.mostGenre(totalGenre);

                    //Menambahkan log yang dihasilkan ke dalam arraylist logs yang sudah dideklarasikan sebelumnya
                    for (int i = 0; i < log.size(); i++) {
                        logs.add(log.get(i));
                    }

                //Catch berfungsi untuk menangkap error yang terjadi pada try dan menambahkan INVALID FORMAT pada logs
                }catch (Exception e){
                    logs.add("INVALID FORMAT");
                }

            //SERIAL_COUNT = Menampilkan urutan movie yang memiliki sequel
            } else if (lines[0].equals("SERIAL_COUNT")){
                //Melakukan pengecekan apakah pada perintah hanya terdapat 1 argumen atau tidak
                //Apabila terdapat 1 argumen maka akan menambahkan "INVALID FORMAT" ke dalam logs
                if (lines[1].length() == 1){
                    logs.add("INVALID FORMAT");
                    continue;
                }

                //Melakukan pengecekan terdapat format serial count argument
                int check = Helper.checkSerialCountFormat(lines[1]);

                //Apabila hasil check yang dihasilkan adalah -1 maka akan menambahkan "INVALID FORMAT" ke logs
                if (check == -1){
                    logs.add("INVALID FORMAT");
                    continue;
                }

                //Melakukan pemisahan elemen antara movie ID
                String [] moviesID = Helper.splitSpace(lines[1]);

                //Deklarasi movieID1 sebagai start
                String movieID1 = (moviesID[0]);

                //Deklarasi movieID2 sebagai end
                String movieID2 = (moviesID[1]);

                //Menerima hasil nilai dari function serialCount di dalam variabel log
                String log = filmApaApps.serialCount(movieID1, movieID2);

                //Melakukan pengecekan apakah nilai yang dihasilkan log apakah "-2" atau tidak
                //Apabila iya maka akan menambahkan "INVALID FORMAT" pada logs
                //Apabila tidak maka akan menambahkan log yang dihasilkan oleh serialCount ke dalam logs
                if (log.equals("-2")){
                    logs.add("INVALID FORMAT");
                }else {
                    logs.add(log);
                }

                //INVALID FORMAT = IF FILM DOESNT EXIST
                //-1 = IF NOT HAVE PATH

            //NUMSERIAL = Menampilkan jumlah movie yang memiliki sequel
            } else if (lines[0].equals("NUMSERIAL")) {
                //Melakukan pengecekan apakah lines[1] kosong atau tidak apabila todal kosong maka akan menambahkan
                //INVALID FORMAT pada logs.

                //Karena seharusnya pada NUMSERIAL tidak terdapat argumen lain
                if (lines.length == 1){
                    //Deklarasi log untuk menerima nilai yang dihasilkan setelah memanggil function numSerial
                    int log = filmApaApps.numSerial();
                    //Menambahkan log yang dihasilkan pada logs.
                    logs.add(String.valueOf(log));
                }else {
                    logs.add("INVALID FORMAT");
                }

            //RGSERIAL = Menampilkan rating serta genre dari movie yang berserial
            } else if (lines[0].equals("RGSERIAL")) {
                //Melakukan pengecekan apakah lines[1] kosong atau tidak, apabila tidak kosong maka akan menambahkan log
                //(INVALID FORMAT) pada logs.

                //Karena seharusnya pada RGSERIAL tidak terdapat argumen lain
                if (lines.length == 1){
                    //Deklarasi arraylist untuk menerima nilai yang dihasilkan dari function rgSerial
                    ArrayList <String> log = filmApaApps.rgSerial();

                    //Melakukan pengecekan size dari log yang dihasilkan dari function rgSerial
                    //Apabila sizenya 0 maka akan menambahkan "0" pada logs.
                    if (log.size() == 0){
                        logs.add("0");
                    }else {
                        //Menambahkan log yang dihasilkan ke dalam logs
                        for (int i = 0; i < log.size(); i++) {
                            logs.add(log.get(i));
                        }
                    }
                }else {
                    //Apabila pada RGSERIAL terdapat argumen lain maka akan dimasukkan "INVALID FORMAT" ke dalam logs
                    logs.add("INVALID FORMAT");
                }


            //RECOMMEND = Menampilkan rekomendasi film berdasarkan genre dan ratingnya
            } else if (lines[0].equals("RECOMMEND")) {
                //Penambahan try catch untuk melakukan handle error apabila terjadi kesalahan saat parsing
                try{
                    //Melakukan pengecekan apakah pada perintah hanya terdapat 1 argumen atau tidak
                    //Apabila terdapat 1 argumen maka akan menambahkan "INVALID FORMAT" ke dalam logs
                    if (lines[1].length() == 1) {
                        logs.add("INVALID FORMAT");
                        continue;
                    }

                    //Melakukan pengecekan terdapat format serial count argument
                    int check = Helper.checkRecommendFormat(lines[1]);

                    //Apabila hasil check yang dihasilkan adalah -1 maka akan menambahkan "INVALID FORMAT" ke logs
                    if (check == -1) {
                        logs.add("INVALID FORMAT");
                        continue;
                    }

                    //Melakukan pemisahan elemen dari argumen recommend
                    String[] movies = Helper.splitSpace(lines[1]);

                    //Deklarasi movieID untuk menyimpan movieID dari argumen
                    String movieID = (movies[0]);

                    //Deklarasi totalMovie untuk membatasi movie yang akan ditampilkan
                    int totalMovie = Integer.parseInt(movies[1]);

                    //Melakukan pengecekan dengan operator ternary apakah input yang diberikan lebih kecil dari 0 atau tidak
                    //Apabila iya maka akan dibulatkan menjadi 3
                    totalMovie = totalMovie <= 0 ? 3 : totalMovie;

                    //Deklarasi arraylist log untuk menyimpan nilai hasil dari function recommend
                    ArrayList<String> log = filmApaApps.recommend(movieID, totalMovie);

                    //Melakukan pengecekan apakah size dari log apakah 0 atau tidak
                    //Apabila iya menambahkan '0' ke dalam logs apabila tidak menambahkan log yang dihasilkan
                    //dari function recommend dan dimasukkan ke logs
                    if (log.size() == 0){
                        logs.add("0");
                    }else {
                        for (int i = 0; i < log.size(); i++) {
                            logs.add(log.get(i));
                        }
                    }
                //Catch berfungsi untuk menangkap error yang terjadi pada try dan menambahkan INVALID FORMAT pada logs
                }catch (Exception e){
                    logs.add("INVALID FORMAT");
                }
            //Apabila ada kesalahan dari command akan menambahkan "INVALID FORMAT" ke dalam logs
            }else {
                logs.add("INVALID FORMAT");
            }
        }
    }
}
