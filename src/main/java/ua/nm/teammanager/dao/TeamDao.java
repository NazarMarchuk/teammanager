package ua.nm.teammanager.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import ua.nm.teammanager.models.Team;

import java.util.List;

@Controller
public class TeamDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public TeamDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Team> index() {
        return jdbcTemplate.query("SELECT * FROM team", new BeanPropertyRowMapper<>(Team.class));
    }

    public Team show(int id) {
        return jdbcTemplate.query("SELECT * FROM team WHERE id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Team.class)).stream().findAny().orElse(null);
    }

    public void update(int id, Team updatedTeam) {
        jdbcTemplate.update("UPDATE team SET name=?, country=?, balance=?, commission=? WHERE id=?", updatedTeam.getName(),
                updatedTeam.getCountry(), updatedTeam.getBalance(), updatedTeam.getCommission(), id);
    }

    public void create(Team team) {
        jdbcTemplate.update("INSERT INTO team(name, country, balance, commission) VALUES(?, ?, ?, ?)", team.getName(),
                team.getCountry(), team.getBalance(), team.getCommission());
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM team WHERE id=?", id);
    }




}
