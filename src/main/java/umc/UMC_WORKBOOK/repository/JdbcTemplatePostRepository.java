package umc.UMC_WORKBOOK.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import umc.UMC_WORKBOOK.domain.Post;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcTemplatePostRepository implements PostRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplatePostRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Post save(Post post) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("Posts")
                .usingGeneratedKeyColumns("PostID");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("UserID", post.getUserId());
        parameters.put("Title", post.getTitle());
        parameters.put("Content", post.getContent());
        parameters.put("PostDate", post.getPostDate());

        jdbcInsert.execute(new HashMap<>(parameters));

        return post;
    }

    @Override
    public boolean remove(int postID) {
        return jdbcTemplate.update("DELETE FROM Posts WHERE PostID = ?",postID) > 0;
    }


    @Override
    public boolean update(Post updatedPost) {
        // 아래 명령어를 참고해 작성하세요
        return jdbcTemplate.update("UPDATE Posts SET Title = ?, Content = ? WHERE PostID = ?", updatedPost.getTitle(), updatedPost.getContent(), updatedPost.getId()) > 0;
    }

    @Override
    public Optional<Post> findByTitle(String title) {
        String sql = "SELECT * FROM Posts WHERE Title = ?";
        List<Post> dataList = jdbcTemplate.query(sql, postRowMapper(), title);
        return dataList.stream().findAny();
    }

    @Override
    public Optional<Post> findById(int id) {
        String sql = "SELECT * FROM Posts WHERE PostID = ?";
        List<Post> dataList = jdbcTemplate.query(sql, postRowMapper(), id);
        return dataList.stream().findAny();
    }

    @Override
    public List<Post> findAll() {
        // 아래 명령어를 참고해 작성하세요
        return jdbcTemplate.query("SELECT * FROM Posts", postRowMapper());
    }

    private RowMapper<Post> postRowMapper() {
        return (rs, rowNum) -> {
            Post post = new Post();
            post.setId(rs.getInt("PostID"));
            post.setUserId(rs.getString("UserID"));
            post.setTitle(rs.getString("Title"));
            post.setContent(rs.getString("Content"));
            post.setPostDate(rs.getDate("PostDate"));
            return post;
        };
    }
}
