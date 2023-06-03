package src.FilmApa;

/*
Class ini berfungsi sebagai struktur data queue nantinya class ini digunakan untuk menyimpan data yang membutuhkan struktur data ini
Pada class ini juga terdapat method method umum yang digunakan untuk ArrayList seperti enqueue, dequeue, peek.
Class ini juga mengimplementasikan generic type.
Data stuktur queue ini biasanya digunakan untuk Breadht First Search
*/

class Queue <T>{
    //Deklarasi node head
    Node head;
    //Deklaraasi node tail
    Node tail;
    //Deklarasi size untuk menyimpan total elemen dari queue
    int size = 0;

    //Function yang berfungsi untuk melakukan pengecekan apakah queue kosong atau tidak
    public boolean isEmpty () {
        return size == 0;
    }

    //Function yang berfungsi untuk menambahkan data ke dalam queue
    public void enqueue (int data){
        Node input = new Node(data);
        if (isEmpty()){
            head = tail = input;
        }else {
            tail.next = input;
            tail = input;
        }
        size++;
    }

    //Function ini berfungsi untuk melakukan pengambilan elemen dari queue
    public int dequeue (){
        Node curr = head;
        if (isEmpty()){
            return -1;
        }else {
            if (head.next == null){
                head = null;
            }else {
                head = head.next;
            }
        }
        size--;
        return curr.data;
    }

    //Function ini berfungsi untuk melihat elemen paling awal dari queue
    public int peek (){
        if (head == null){
            return -1;
        }
        return head.data;
    }
}

//Class node ini berfungsi untuk menyimpan data dalam bentuk integer dan pointer next
class Node {
    //Deklarasi data
    int data;
    //Deklarasi pointer next (mengarah ke node selanjutnya)
    Node next;
    //Deklarasi constructor
    public Node(int data) {
        this.data = data;
    }
}

