package observerpattern;


public interface Observer {
    void update();
    void notifySubject(Observer b);
}
