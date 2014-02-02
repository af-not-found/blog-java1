package net.afnf.blog;

import javax.sql.DataSource;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.jdbc.JdbcTestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {
        "file:src/main/webapp/WEB-INF/applicationContext.xml",
        "file:src/main/webapp/WEB-INF/applicationContext-db.xml",
        "file:src/main/webapp/WEB-INF/applicationContext-security.xml",
        "classpath:/applicationContext-testdb.xml" })
public abstract class SpringTestBase {

    @Autowired
    private ApplicationContext ctx;

    @Autowired
    protected DataSource dataSource;

    protected void executeSql(String path) {
        EncodedResource resource = new EncodedResource(ctx.getResource(path), "UTF-8");
        JdbcTemplate template = new JdbcTemplate(dataSource);
        JdbcTestUtils.executeSqlScript(template, resource, true);
    }
}
