package umc.UMC_WORKBOOK.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import umc.UMC_WORKBOOK.domain.User;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcTemplateUserRepository implements UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateUserRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public User save(User user) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("Users");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("UserID", user.getId());
        parameters.put("Username", user.getName());
        parameters.put("Password", user.getPassword());
        parameters.put("BirthDate", user.getBirthDate());

        jdbcInsert.execute(new HashMap<>(parameters));

        return user;
    }

    @Override
    public boolean remove(String id) {
        int affected = jdbcTemplate.update("DELETE FROM Users WHERE UserID = ?", id);
        return affected > 0;
    }

    @Override
    public boolean update(String id, User updateUser) {
        int affected = jdbcTemplate.update("UPDATE Users SET Username = ?, Password = ? WHERE UserID = ?",
                updateUser.getName(), updateUser.getPassword(), id);
        return affected > 0;
    }

    @Override
    public Optional<User> findById(String id) {
        List<User> result = jdbcTemplate.query("SELECT * FROM Users WHERE UserID = ?", userRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM Users", userRowMapper());
    }


    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> {
            User user  = new User();
            user.setId(rs.getString("UserID"));
            user.setName(rs.getString("Username"));
            user.setPassword(rs.getString("Password"));
            user.setBirthDate(rs.getTimestamp("BirthDate"));
            return user;
        };
    }
}