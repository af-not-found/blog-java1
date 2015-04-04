package net.afnf.blog;

import java.sql.Connection;

import javax.sql.DataSource;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

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
        Resource resource = new ClassPathResource(path, getClass());
        ResourceDatabasePopulator rdp = new ResourceDatabasePopulator();
        rdp.addScript(resource);
        rdp.setSqlScriptEncoding("UTF-8");
        rdp.setIgnoreFailedDrops(true);
        rdp.setContinueOnError(false);

        JdbcTemplate template = new JdbcTemplate(dataSource);
        Connection conn = DataSourceUtils.getConnection(template.getDataSource());
        rdp.populate(conn);
    }
}
