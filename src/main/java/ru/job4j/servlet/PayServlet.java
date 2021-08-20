package ru.job4j.servlet;

import ru.job4j.model.Account;
import ru.job4j.model.Ticket;
import ru.job4j.store.SqlStore;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(
        value = "/pay.do",
        initParams = {
                @WebInitParam(name = "price", value = "500")
        })
public class PayServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain; charset=utf-8");
        try(PrintWriter out = resp.getWriter()) {
            out.write(getInitParameter("price"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain; charset=utf-8");
        String tickets = req.getParameter("tickets");
        int sessionId = Integer.parseInt(req.getParameter("sessionId"));
        String[] seats = tickets.split(",");
        String phone = req.getParameter("phone");
        List<Ticket> ticketsList = new ArrayList<>(seats.length);
        try {
            Account account = SqlStore.instOf().findTheAccount(phone);
            for (String seat : seats) {
                ticketsList.add(new Ticket(0, sessionId, Character.getNumericValue(seat.charAt(0)),
                        Character.getNumericValue(seat.charAt(1)), account.getId()));
            }
            SqlStore.instOf().addTickets(ticketsList);
            try (PrintWriter out = resp.getWriter()) {
                out.write("Билеты куплены успешно.");
            }
        } catch (Exception ex) {
            try(PrintWriter out = resp.getWriter()) {
                out.write(ex.getMessage());
            }
        }
    }
}
