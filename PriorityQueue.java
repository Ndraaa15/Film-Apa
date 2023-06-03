package src.FilmApa;

import java.util.EmptyStackException;

/*
Class ini berfungsi sebagai struktur data priority queue nantinya class ini digunakan untuk menyimpan data yang membutuhkan struktur data ini
Pada class ini juga terdapat method method umum yang digunakan untuk ArrayList seperti enqueue, dequeue, dan clear.
Class ini juga mengimplementasikan generic type.
Priority queue ini digunakan untuk mengurutkan genre berdasarkan total movie yang dimiliki dan urutan abjad
*/

public class PriorityQueue <T>{
    //Deklarasi head
    NodeGenre head;
    //Deklarasi tail
    NodeGenre tail;
    //Deklarasi size untuk menyimpan total node
    int size = 0;

    //Function ini digunakan untuk melakukan pengecekan apakah priority queue kosong atau tidak
    public boolean isEmpty(){
        return size == 0;
    }

    //Function ini digunakan untuk mengambalikan total node yang ada di priority queue
    public int size (){
        return size;
    }

    //Function ini digunakan untuk menambah elemen kedalam priority queue dan melakukan perpandingan di totalMovie dan abjad
    public void enqueue(NodeGenre input) {
        NodeGenre curr = head;

        if (isEmpty()) {
            head = tail = input;
        } else {
            if (input.totalMovies > head.totalMovies || (input.totalMovies == head.totalMovies && input.genre.compareTo(head.genre) < 0)) {
                input.next = head;
                head = input;
            } else {
                while (curr.next != null) {
                    if (input.totalMovies > curr.next.totalMovies || (input.totalMovies == curr.next.totalMovies && input.genre.compareTo(curr.next.genre) < 0)) {
                        input.next = curr.next;
                        curr.next = input;
                        break;
                    }
                    curr = curr.next;
                }

                if (curr.next == null) {
                    tail.next = input;
                    tail = input;
                }
            }
        }
        size++;
    }

    //Function ini digunakan untuk mengambil elemen paling awal di priority queue
    public NodeGenre dequeue (){
        if (isEmpty()){
            throw new EmptyStackException();
        }
        NodeGenre temp = head;
        head = head.next;
        size--;
        return temp;
    }

    //Function ini digunakan untuk menghapus semua elemen priority queue dan membuat priority queue menjadi 0
    void clear(){
        head = tail = null;
        size = 0;
    }

}

class NodeGenre {
    //Deklarasi next
    NodeGenre next;
    //Deklarasi genre
    String genre;
    //Deklarasi totalMovies dari setiap genre
    int totalMovies;

    //Deklarasi constructor
    public NodeGenre(String genre, int totalMovies) {
        this.genre = genre;
        this.totalMovies = totalMovies;
    }
}
