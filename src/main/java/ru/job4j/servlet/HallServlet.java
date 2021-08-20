package ru.job4j.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import ru.job4j.model.Ticket;
import ru.job4j.store.SqlStore;

@WebServlet(
        value = "/hall.do",
        initParams = {
                @WebInitParam(name = "rows", value = "6"),
                @WebInitParam(name = "cells", value = "6")
        } )
public class HallServlet extends HttpServlet {
    private final static Gson GSON = new GsonBuilder().create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        int sessionId = Integer.parseInt(req.getParameter("sessionId"));
        int rows = Integer.parseInt(this.getInitParameter("rows"));
        int cells = Integer.parseInt(this.getInitParameter("cells"));
        List<Ticket> tickets = SqlStore.instOf().findTickets(sessionId);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("rows", rows);
        jsonObject.addProperty("cells", cells);
        String ticketsJson = GSON.toJson(tickets);
        jsonObject.addProperty("tickets", ticketsJson);
        try(PrintWriter out = resp.getWriter()) {
            out.write(jsonObject.toString());
        }
    }
}
