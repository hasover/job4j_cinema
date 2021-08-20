package ru.job4j.store;

import ru.job4j.model.Account;
import ru.job4j.model.Ticket;

import java.util.List;

public interface Store {
    Account findTheAccount(String phone);
    List<Ticket> findTickets(int sessionId);
    boolean addTickets(List<Ticket> ticket);
}
