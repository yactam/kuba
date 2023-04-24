package observerpattern;
public interface SubjectObserver {
    void addObserver(Observer ob);
    void notifyObservers(Object obj);
}
