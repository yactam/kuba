package observerpattern;


public interface Observer {
    void update(Object obj);
    void notifySubject(Observer b);
}
