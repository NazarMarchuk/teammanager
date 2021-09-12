package ua.nm.teammanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.nm.teammanager.dao.PlayerDao;
import ua.nm.teammanager.dao.TeamDao;
import ua.nm.teammanager.models.Player;
import ua.nm.teammanager.models.Team;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/players")
public class PlayerController {

    @Autowired
    private PlayerDao playerDao;

    @Autowired
    private TeamDao teamDao;

    @GetMapping()
    public String index(Model model){

        model.addAttribute("players", playerDao.index());

        return "players/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model1, Model model2){
        model1.addAttribute("player", playerDao.show(id));
        Team playerTeam = null;
        for(Team team : teamDao.index()){
            if (team.getName().equals(playerDao.show(id).getTeam())){
                playerTeam = team;
            }
        }
        model2.addAttribute("team", playerTeam);
        return "players/show";
    }

    @GetMapping("/new")
    public String newPlayer(Model model){

        model.addAttribute("player", new Player());

        return "players/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("player") @Valid Player player, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "players/new";
        playerDao.create(player);
        return "redirect:/players";

    }

    @GetMapping("/{id}/edit")
    public  String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("player", playerDao.show(id));

        return "players/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("player") @Valid Player player, BindingResult bindingResult, @PathVariable("id") int id){
        if(bindingResult.hasErrors())
            return "players/edit";
        playerDao.update(id, player);
        return "redirect:/players";
    }

    @DeleteMapping("/{id}")
    public  String delete(@PathVariable("id") int id){
        playerDao.delete(id);
        return "redirect:/players";
    }
}
