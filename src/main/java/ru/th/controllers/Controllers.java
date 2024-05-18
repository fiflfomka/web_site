package ru.th.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.th.DAO.*;
import ru.th.models.*;
import org.springframework.stereotype.Controller;

@Controller
public class Controllers {
    @GetMapping(value = {"/", "/index"})
    public String index(Model model) {
        PerformanceDAO dao = new PerformanceDAO();
        model.addAttribute("allPerformances", dao.getAll());
        return "index";
    }

    @GetMapping(value = {"/performance"})
    public String performance(
            @RequestParam("PerformanceID") int id,
            Model model) {
        PerformanceDAO dao = new PerformanceDAO();
        model.addAttribute("thisPerformance", dao.findByID(id));
        FreeSeatsDAO fdao = new FreeSeatsDAO();
        model.addAttribute("allPlaces", fdao.findByPerformance(id));
        return "performance";
    }

    @GetMapping(value = {"/play"})
    public String play(
            @RequestParam("PlayID") Integer id,
            Model model) {
        PlayDAO dao = new PlayDAO();
        model.addAttribute("thisPlay", dao.findById(id));
        ManDAO mdao = new ManDAO();
        model.addAttribute("Regesseur", mdao.findById(id));
        PerformanceDAO pdao = new PerformanceDAO();
        model.addAttribute("allPerf", pdao.getByPlayId(id));
        return "play";
    }

    @GetMapping(value = {"/theater"})
    public String theater(
            @RequestParam("TheaterID") Integer id,
            Model model) {
        TheaterDAO dao = new TheaterDAO();
        model.addAttribute("thisTheater", dao.findById(id));
        PerformanceDAO pdao = new PerformanceDAO();
        model.addAttribute("allPerf", pdao.getByTheaterId(id));
        return "theater";
    }

    @GetMapping(value = {"/find_string"})
    public String find_string(
            @RequestParam("SubString") String s,
            Model model) {
        PerformanceDAO dao = new PerformanceDAO();
        model.addAttribute("allPerformances", dao.getByText(s));
        return "index";
    }

    @GetMapping(value = {"/payment"})
    public String find_string(
            @RequestParam("PerformanceID") Integer pid,
            @RequestParam("SeatID") Integer sid,
            Model model) {
        FreeSeatsDAO dao = new FreeSeatsDAO();
        FreeSeatsPK fpk = new FreeSeatsPK(pid,sid);
        model.addAttribute("thisPlace", dao.findById( fpk ));
        return "payment";
    }

    @GetMapping(value = {"/pay"})
    public String find_string(
            @RequestParam("PerformanceID") Integer pid,
            @RequestParam("SeatID") Integer sid,
            @RequestParam("Name") String name,
            @RequestParam("CardNumber") String card_number,
            Model model) {
        FreeSeatsDAO dao = new FreeSeatsDAO();
        FreeSeatsPK fpk = new FreeSeatsPK(pid,sid);
        model.addAttribute("PerformanceID", pid);
        model.addAttribute("Success", dao.deleteById(fpk));
        return "success";
    }
}
