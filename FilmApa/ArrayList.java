package src.FilmApa;

import java.util.Arrays;
import java.util.Objects;

/*
Class ini berfungsi sebagai struktur data ArrayList nantinya class ini digunakan untuk menyimpan data yang membutuhkan struktur data ini
Pada class ini juga terdapat method method umum yang digunakan untuk ArrayList seperti add, set, remove, dan lain-lain.
Class ini juga mengimplementasikan generic type.
*/

public class ArrayList<E> {
    //Deklarasi dari default capacity untuk array list;
    private static final int DEFAULT_CAPACITY = 10;
    //Deklarasi untuk array of element pada array list (bergantung pada type datanya nanti)
    private E [] elements;
    //Deklarasi untuk size dari array list
    private int size = 0;

    //Contructor arraylist tanpa parameter
    @SuppressWarnings("unchecked")
    public ArrayList() {
        this.elements = (E[]) new Object[DEFAULT_CAPACITY];
    }

    //Constructor arraylist dengan parameter untuk kapasitasnya
    @SuppressWarnings("unchecked")
    public ArrayList(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity cannot be negative");
        }
        elements = (E[])new Object[capacity];
    }

    //Funtion untuk mengembalikan jumlah elemen dari arraylist
    public int size() {
        return size;
    }

    //Function untuk mengembalikan nilai apakah arraylist kosong atau tidak
    public boolean isEmpty() {
        return size == 0;
    }

    //Function untuk melakukan pengecekan apakah terdapat suatu element di arraylist ini
    public boolean contains(E o) {
        return indexOf(o) >= 0;
    }

    //Function untuk mengembalikan nilai index dari suatu element di arraylist
    public int indexOf(E o) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(o, elements[i])) {
                return i;
            }
        }
        return -1;
    }

    //Function untuk mendapatkan nilai dari suatu index di arraylist
    public E get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return(E) elements[index];
    }


    //Function yang digunakan untuk menambahkan elemen ke dalam index tertentu.
    public E set(int index, E element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of range: " + index);
        }
        E oldValue = (E) elements[index];
        elements[index] = element;
        return oldValue;
    }


    //Function yang berfungsi untuk menambahkan element ke dalam arraylist
    public boolean add(E element) {
        ensureCapacity(size + 1);
        elements[size++] = element;
        return true;
    }

    //Function ini befungsi untuk menambahkan element ke dalam arraylist dengan menggunakan index yang spesific
    public void add(int index, E element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of range: " + index);
        }
        ensureCapacity(size + 1);
        System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = element;
        size++;
    }

    //Function yang digunakan untuk menghapus elemen dari arraylist berdasarkan indexnya
    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of range: " + index);
        }
        E oldValue = (E) elements[index];
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(elements, index + 1, elements, index, numMoved);
        }
        elements[--size] = null;
        return oldValue;
    }

    //Function yang digunakan untuk menghapus elemen dari arraylist berdasarkan elemennya
    public boolean remove(E o) {
        int index = indexOf(o);
        if (index >= 0) {
            remove(index);
            return true;
        }
        return false;
    }

    //Berfungsi untuk menambahkan kapasistas array of elemen sebesar 2 kali lipat.
    private void ensureCapacity(int minCapacity) {
        if (minCapacity > elements.length) {
            int newCapacity = elements.length * 2;
            if (newCapacity < minCapacity) {
                newCapacity = minCapacity;
            }
            elements = Arrays.copyOf(elements, newCapacity);
        }
    }
}

