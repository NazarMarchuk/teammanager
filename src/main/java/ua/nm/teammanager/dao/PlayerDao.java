package ua.nm.teammanager.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import ua.nm.teammanager.models.Player;
import ua.nm.teammanager.models.Team;

import java.util.List;

@Controller
public class PlayerDao {
    
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public PlayerDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Player> index() {
        return jdbcTemplate.query("SELECT * FROM player", new BeanPropertyRowMapper<>(Player.class));
    }

    public Player show(int id) {
        return jdbcTemplate.query("SELECT * FROM player WHERE id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Player.class)).stream().findAny().orElse(null);
    }

    public void create(Player player) {
        jdbcTemplate.update("INSERT INTO Player(name, country, age, exp, price, team) VALUES(?, ?, ?, ?, ?, ?)", player.getName(), player.getCountry(),
                player.getAge(), player.getExp(), player.getPrice(), player.getTeam());
    }

    public void update(int id, Player updatedPlayer) {
        jdbcTemplate.update("UPDATE player SET name=?, country=?, age=?, exp=?, price=?, team=? WHERE id=?", updatedPlayer.getName(),
                updatedPlayer.getCountry(), updatedPlayer.getAge(), updatedPlayer.getExp(), updatedPlayer.getAge(), updatedPlayer.getTeam(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM player WHERE id=?", id);
    }




    //public List<Player> playIn(Player player){
    //    return jdbcTemplate.query("SELECT * FROM team WHERE team=?", new Object[]{player.getTeam()}, new BeanPropertyRowMapper<>(Player.class));
    //}



}
