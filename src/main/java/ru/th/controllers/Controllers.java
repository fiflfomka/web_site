package ru.th.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.th.DAO.*;
import ru.th.models.*;
import org.springframework.stereotype.Controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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






    // content maker part

    @GetMapping(value = {"contentmaker/", "/contentmaker/choosehall"})
    public String choosehall(Model model) {
        HallsDAO dao = new HallsDAO();
        model.addAttribute("AllHalls", dao.getAll());
        TheaterDAO tdao = new TheaterDAO();
        model.addAttribute("DAO", tdao);
        return "contentmaker/choosehall";
    }

    @GetMapping(value = {"/contentmaker/addperformance"})
    public String addplay(@RequestParam("Hall") Integer hall_id, Model model) {
        PlayDAO dao = new PlayDAO();
        model.addAttribute("AllPlay", dao.getAll());
        model.addAttribute("Hall", hall_id);
        return "contentmaker/addperformance";
    }

    @PostMapping(value = {"/contentmaker/success"})
    public String addplay_handler(
            @RequestParam("Start") String start,
            @RequestParam("End") String end,
            @RequestParam("Hall") Integer hall_id,
            @RequestParam("Play") Integer play_id,
            Model model) {
        Timestamp start_time = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date parsedDate = dateFormat.parse(start);
            start_time = new Timestamp(parsedDate.getTime());
        } catch(Exception e) {
            model.addAttribute("OK", false);
            return "contentmaker/success";
        }

        Timestamp end_time = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date parsedDate = dateFormat.parse(end);
            end_time = new Timestamp(parsedDate.getTime());
        } catch(Exception e) {
            model.addAttribute("OK", false);
            return "contentmaker/success";
        }

        Performance p = new Performance();
        p.setStart_time(start_time);
        p.setEnd_time(end_time);
        p.setPlay_id(play_id);
        p.setHall_id(hall_id);
        // потом переделать
        List<Integer> a = new ArrayList<>();
        a.add(100);
        a.add(200);
        a.add(300);
        a.add(400);
        a.add(500);
        a.add(600);
        a.add(700);
        a.add(800);
        p.setPlaces_price_array(a);

        PerformanceDAO dao = new PerformanceDAO();
        dao.save(p);

        model.addAttribute("OK", true);
        return "contentmaker/success";
    }






    // expert part

    @GetMapping(value = {"expert/", "/expert/choseaction"})
    public String expert_choseaction(Model model) {
        return "expert/choseaction";
    }

    @GetMapping(value = {"/expert/actors"})
    public String expert_actors(Model model) {
        return "expert/actors";
    }

    @GetMapping(value = {"/expert/addpiece_regisseur"})
    public String addpiece_regisseur(Model model) {
        ManDAO dao = new ManDAO();
        model.addAttribute("AllMan", dao.getAll());
        return "expert/addpiece_regisseur";
    }

    @GetMapping(value = {"/expert/addpiece"})
    public String addpiece(
            @RequestParam("Regisseur") String id,
            Model model) {
        model.addAttribute("Regisseur", id);
        return "expert/addpiece";
    }

    @PostMapping(value = {"/expert/success"})
    public String addpiece_handler(
            @RequestParam("Name") String name,
            @RequestParam("Annotation") String annotation,
            @RequestParam("Regisseur") String regisseur,
            @RequestParam("Year") String year,
            @RequestParam("group") EnumPlayGenre genre,
            Model model) {

        if (name.isEmpty()) {
            model.addAttribute("Success", false);
            return "/expert/success";
        }

        Integer reg;
        if (Objects.equals(regisseur, "null")) {
            reg = null;
        } else {
            try {
                reg = Integer.parseInt(regisseur);
            }
            catch (NumberFormatException e) {
                model.addAttribute("Success", false);
                model.addAttribute("Regisseur", regisseur);
                return "/expert/success";
            }
        }

        Integer ye;
        if (year.isEmpty()) {
            ye = null;
        } else {
            try {
                ye = Integer.parseInt(year);
            }
            catch (NumberFormatException e) {
                model.addAttribute("Success", false);
                model.addAttribute("Regisseur", regisseur);
                return "/expert/success";
            }
        }

        String ann = annotation;
        if (ann.isEmpty()) {
            ann = null;
        }

        Integer piece_id =  null;
        try {
            Play p = new Play();
            p.setAnnotation(ann);
            p.setName(name);
            p.setGenre(genre);
            p.setRegisseur(reg);
            p.setRelease_year(ye);
            PlayDAO dao = new PlayDAO();
            dao.saveOrUpdate(p);
            piece_id = p.getPlay_id();
        }
        catch (Exception e) {
            model.addAttribute("Success", false);
            model.addAttribute("Regisseur", regisseur);
            return "/expert/success";
        }
        model.addAttribute("Success", true);
        model.addAttribute("PieceId", piece_id);
        ManDAO dao = new ManDAO();
        model.addAttribute("AllMan", dao.getAll());
        return "/expert/success";
    }

    @PostMapping(value = {"/expert/success2"})
    public String add_actor_for_play_handler (
            @RequestParam("answerList") Integer[] actorList,
            @RequestParam("PieceId") Integer piece_id,
            Model model) {
        List<Actor> lst = new ArrayList<>();
        for (Integer i : actorList) {
            Actor a = new Actor();
            a.setPlay_id(piece_id);
            a.setMan_id(i);
            lst.add(a);
        }
        ActorDAO dao = new ActorDAO();
        dao.save(lst);
        return "/expert/success2";
    }
}
