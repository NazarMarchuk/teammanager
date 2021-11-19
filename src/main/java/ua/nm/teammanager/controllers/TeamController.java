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
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    private TeamDao teamDao;

    @Autowired
    PlayerDao playerDao;

    @GetMapping()
    public String index(Model model){

        model.addAttribute("teams", teamDao.index());

        return "teams/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model1, Model model2){
        List<Player> teamplayers = new ArrayList<>();
        model1.addAttribute("team", teamDao.show(id));
        for(Player player : playerDao.index()){
            if (player.getTeam().equals(teamDao.show(id).getName())){
                teamplayers.add(player);
            }
        }
        model2.addAttribute("players", teamplayers);

        return "teams/show";
    }

    @GetMapping("/new")
    public String newPlayer(Model model){

        model.addAttribute("team", new Team());

        return "teams/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("team") @Valid Team team, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "teams/new";

        teamDao.create(team);
        return "redirect:/teams";

    }

    @GetMapping("/{id}/edit")
    public  String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("team", teamDao.show(id));

        return "teams/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("team")  Team team, @PathVariable("id") int id){
        teamDao.update(id, team);
        return "redirect:/teams";
    }

    @DeleteMapping("/{id}")
    public  String delete(@PathVariable("id") int id){
        teamDao.delete(id);
        return "redirect:/teams";
    }

}
