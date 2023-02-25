package observerpattern;
public interface Observable<T>{
    void addObserver(Observer<T> observer);
    void notifyObservers();
}
