package net.afnf.blog.it;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import net.afnf.blog.SpringTestBase;
import net.afnf.blog.bean.AppConfig;
import net.afnf.blog.common.AfnfUtil;
import net.afnf.blog.common.Crypto;
import net.afnf.blog.mapper.EntryMapperCustomized;
import net.afnf.blog.service.TokenService;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SeleniumIT extends SpringTestBase {

    private static final long DEFAULT_WAIT = 500;
    private static final long IMPLICITLY_WAIT = 500;

    private WebDriver wd;
    private String baseurl;

    @Autowired
    private EntryMapperCustomized em;

    private static Map<String, String> webDriverMap = new HashMap<String, String>();
    static {
        webDriverMap.put("firefox", "org.openqa.selenium.firefox.FirefoxDriver");
        webDriverMap.put("phantomjs", "org.openqa.selenium.phantomjs.PhantomJSDriver");
        webDriverMap.put("chrome", "org.openqa.selenium.chrome.ChromeDriver");
        webDriverMap.put("ie", "org.openqa.selenium.ie.InternetExplorerDriver");
    }

    @Before
    public void setUp() throws Exception {
        AppConfig appConfig = AppConfig.getInstance();
        baseurl = appConfig.getSeleniumTargetUrl();
        wd = (WebDriver) Class.forName(webDriverMap.get(appConfig.getSeleniumWebdriver())).newInstance();
        wd.manage().window().setSize(new Dimension(1000, 700));
        wd.manage().timeouts().implicitlyWait(IMPLICITLY_WAIT, TimeUnit.MILLISECONDS);
    }

    @After
    public void tearDown() {
        wd.quit();
    }

    @Test
    public void test100_user() {
        wd.get(baseurl);
        assertEquals(0, find(".summary_entry_title").size());
        assertEquals(0, find(".sb_entry_title").size());
        assertEquals(0, find(".sb_tag").size());
        assertEquals(0, find(".sb_month").size());
    }

    @Test
    public void test101_admin() {
        wd.get(baseurl + "/_admin/entries/");
        assertThat(wd.getCurrentUrl(), startsWith(baseurl + "/_admin/pub/login"));

        wd.findElement(By.name("username")).click();
        wd.findElement(By.name("username")).clear();
        wd.findElement(By.name("username")).sendKeys("admin");
        wd.findElement(By.name("password")).click();
        wd.findElement(By.name("password")).clear();
        wd.findElement(By.name("password")).sendKeys("pass");
        postAndCloseModal();
        assertThat(wd.getCurrentUrl(), startsWith(baseurl + "/_admin/pub/login"));

        wd.findElement(By.name("j_username")).click();
        wd.findElement(By.name("j_username")).clear();
        wd.findElement(By.name("j_username")).sendKeys("admin");
        wd.findElement(By.name("j_password")).click();
        wd.findElement(By.name("j_password")).clear();
        wd.findElement(By.name("j_password")).sendKeys("test");
        postAndWait();
        assertThat(wd.getCurrentUrl(), startsWith(baseurl + "/_admin/pub/login"));
        assertThat(wd.getCurrentUrl(), containsString("faliled"));

        wd.findElement(By.name("j_username")).click();
        wd.findElement(By.name("j_username")).clear();
        wd.findElement(By.name("j_username")).sendKeys("admin");
        wd.findElement(By.name("j_password")).click();
        wd.findElement(By.name("j_password")).clear();
        wd.findElement(By.name("j_password")).sendKeys("pass");
        postAndWait();
        assertEquals(baseurl + "/_admin/entries/", wd.getCurrentUrl());

        assertEquals(0, find(".summary_entry_title").size());

        wd.findElement(By.xpath("//div[@class='btn-group']//button[.='new']")).click();
        assertEquals("-1", find("#id").get(0).getAttribute("value"));

        wd.findElement(By.id("title")).click();
        wd.findElement(By.id("title")).clear();
        wd.findElement(By.id("title")).sendKeys("blog1");
        wd.findElement(By.id("tags")).click();
        wd.findElement(By.id("tags")).clear();
        wd.findElement(By.id("tags")).sendKeys("tag1,tag2,tag3");
        wd.findElement(By.id("content")).click();
        wd.findElement(By.id("content")).clear();
        wd.findElement(By.id("content"))
                .sendKeys(
                        "#てすと123\nabc\n\n#code\n````\nif(1==1){\n    alert(1)\n}\n````\n\n#point\n1. aaega\n1. 433\n1. 4343\n 1. 224\n 1. あああ");
        postAndWait();
        assertThat(find(".ajaxform .ajaxret").get(0).getText(), is(not("")));
        assertEquals("1", find("#id").get(0).getAttribute("value"));
        assertEquals(1, em.countWithCond(null).intValue());

        postAndWait();
        assertEquals("1", find("#id").get(0).getAttribute("value"));
        assertEquals(1, em.countWithCond(null).intValue());

        wd.findElement(By.xpath("//div[@class='btn-group']//button[.='entries']")).click();
        assertEquals(1, find(".summary_entry_title").size());
        wd.findElement(By.linkText("blog1")).click();
        wd.findElement(By.id("title")).click();
        wd.findElement(By.id("title")).clear();
        wd.findElement(By.id("title")).sendKeys("blog1234");
        wd.findElement(By.id("content")).click();
        wd.findElement(By.id("content")).clear();
        wd.findElement(By.id("content"))
                .sendKeys(
                        "#てすと123\nabc\n\n#code\n````\nif(1==1){\n    alert(1)\n}\n````\n\n#point\n1. aaega\n1. 433\n1. 4343\n 1. 224\n 1. あああ\n 1. bbb");
        postAndWait();
        assertThat(find(".ajaxform .ajaxret").get(0).getText(), is(not("")));

        wd.findElement(By.xpath("//div[@class='btn-group']//button[.='entries']")).click();
        assertEquals(1, find(".summary_entry_title").size());
        wd.findElement(By.linkText("blog1234")).click();
    }

    @Test
    public void test102_user() {

        wd.get(baseurl);
        assertEquals(1, find(".summary_entry_title").size());
        assertEquals(1, find(".sb_entry_title").size());
        assertEquals(3, find(".sb_tag").size());
        assertEquals(1, find(".sb_month").size());

        wd.findElement(By.linkText("blog1234")).click();
        wd.findElement(By.cssSelector(".comments_container")).click();
        assertEquals(0, find(".comments_container .comment").size());

        wd.findElement(By.id("name")).click();
        wd.findElement(By.id("name")).clear();
        wd.findElement(By.id("name")).sendKeys("test2");
        wd.findElement(By.id("content")).click();
        wd.findElement(By.id("content")).clear();
        wd.findElement(By.id("content")).sendKeys("aaaaaaa\"aaaaaa");
        postAndCloseModal();

        wd.findElement(By.cssSelector(".comments_container")).click();
        assertEquals(1, find(".comments_container .comment").size());
        assertEquals(0, find(".comments_container .comment_content").size());

        find(".sb_recents .sb_more a").get(0).click();
        wd.findElement(By.cssSelector(".headerdiv > h1 > a")).click();

        find(".sb_entry_title a").get(0).click();
        find(".sb_tag a").get(1).click();
        find(".sb_month a").get(0).click();
        find(".sb_recents .sb_more a").get(0).click();
        wd.findElement(By.cssSelector(".headerdiv > h1 > a")).click();
    }

    @Test
    public void test103_admin() {
        wd.get(baseurl + "/_admin/entries/");
        assertThat(wd.getCurrentUrl(), startsWith(baseurl + "/_admin/pub/login"));

        wd.findElement(By.name("j_username")).click();
        wd.findElement(By.name("j_username")).clear();
        wd.findElement(By.name("j_username")).sendKeys("admin");
        wd.findElement(By.name("j_password")).click();
        wd.findElement(By.name("j_password")).clear();
        wd.findElement(By.name("j_password")).sendKeys("pass");
        postAndWait();
        assertEquals(baseurl + "/_admin/entries/", wd.getCurrentUrl());

        assertEquals(1, find(".summary_entry_title").size());

        wd.findElement(By.xpath("//div[@class='btn-group']//button[.='new']")).click();
        wd.findElement(By.id("title")).click();
        wd.findElement(By.id("title")).clear();
        wd.findElement(By.id("title")).sendKeys("ブログ12345");
        wd.findElement(By.id("tags")).click();
        wd.findElement(By.id("tags")).clear();
        wd.findElement(By.id("tags")).sendKeys("tag1, tag3, タグa");
        wd.findElement(By.id("content")).click();
        wd.findElement(By.id("content")).clear();
        wd.findElement(By.id("content")).sendKeys("#テスト\naaa|bbb|ccc\n---|---|---\n1|2|3\nああああ|いいいいい|うううううう\n#|*|abbb");
        wd.findElement(By.id("r0")).click(); // draft
        postAndWait();
        assertThat(find(".ajaxform .ajaxret").get(0).getText(), is(not("")));
        assertEquals("2", find("#id").get(0).getAttribute("value"));
        assertEquals(2, em.countWithCond(null).intValue());

        wd.findElement(By.xpath("//div[@class='btn-group']//button[.='entries']")).click();
        assertEquals(2, find(".summary_entry_title").size());

        wd.findElement(By.xpath("//div[@class='btn-group']//button[.='cache']")).click();
    }

    @Test
    public void test104_user() {

        wd.get(baseurl);

        // draftなので増えない
        assertEquals(1, find(".summary_entry_title").size());
        assertEquals(1, find(".sb_entry_title").size());
        assertEquals(3, find(".sb_tag").size());
        assertEquals(1, find(".sb_month").size());

        wd.findElement(By.linkText("blog1234")).click();
        wd.findElement(By.id("name")).click();
        wd.findElement(By.id("name")).clear();
        wd.findElement(By.id("name")).sendKeys("test2\nbbbbb<script>location.href='/a';</script>");
        wd.findElement(By.id("content")).click();
        wd.findElement(By.id("content")).clear();
        wd.findElement(By.id("content")).sendKeys(
                "aaaaaaaabbbbbbbbbbbbb\n ああああああ<script>location.href='/b';</script>ああああaaa\"aa\n<b>aaa</b>");
        postAndCloseModal();

        wd.findElement(By.cssSelector(".comments_container")).click();
        assertEquals(2, find(".comments_container .comment").size());
        assertEquals(0, find(".comments_container .comment_content").size());

        find(".sb_entry_title a").get(0).click();
        find(".sb_tag a").get(2).click();
        find(".sb_month a").get(0).click();
        find(".sb_recents .sb_more a").get(0).click();
        wd.findElement(By.cssSelector(".headerdiv > h1 > a")).click();

        wd.findElement(By.linkText("blog1234")).click();
        wd.findElement(By.id("name")).click();
        wd.findElement(By.id("name")).clear();
        wd.findElement(By.id("name")).sendKeys("test3");
        wd.findElement(By.id("content")).click();
        wd.findElement(By.id("content")).clear();
        wd.findElement(By.id("content")).sendKeys("aaaaaaabbbbbb\n あああ\n<b>aaa</b> vv");
        postAndCloseModal();

        wd.findElement(By.cssSelector(".comments_container")).click();
        assertEquals(3, find(".comments_container .comment").size());
        assertEquals(0, find(".comments_container .comment_content").size());

        String redirected = baseurl + "/";
        {
            wd.get(baseurl + "/t/aaaaaaaaaaaaaa");
            assertEquals(redirected, wd.getCurrentUrl());

            wd.get(baseurl + "/t/bbbbbbbb?page=11");
            assertEquals(redirected, wd.getCurrentUrl());

            wd.get(baseurl + "/m/aaaaa");
            assertEquals(redirected, wd.getCurrentUrl());

            wd.get(baseurl + "/999999999");
            assertEquals(redirected, wd.getCurrentUrl());

            wd.get(baseurl + "/2");
            assertEquals(redirected, wd.getCurrentUrl());

            wd.get(baseurl + "/1");
            assertEquals(baseurl + "/1", wd.getCurrentUrl());
        }
    }

    @Test
    public void test105_admin() {
        wd.get(baseurl + "/_admin/entries/");
        wd.findElement(By.name("j_username")).click();
        wd.findElement(By.name("j_username")).clear();
        wd.findElement(By.name("j_username")).sendKeys("admin");
        wd.findElement(By.name("j_password")).click();
        wd.findElement(By.name("j_password")).clear();
        wd.findElement(By.name("j_password")).sendKeys("pass");
        postAndWait();

        assertEquals(2, find(".summary_entry_title").size());

        wd.findElement(By.linkText("ブログ12345")).click();
        wd.findElement(By.id("content")).click();
        wd.findElement(By.id("content")).clear();
        wd.findElement(By.id("content")).sendKeys("#テスト\naaa|bbb|ccc\n---|---|---\n1|2|3\nああああ|いいいいい|うううううう\n#|*|abbbccc");
        wd.findElement(By.id("r1")).click(); // normal
        wd.findElement(By.cssSelector("span")).click();
        postAndWait();
        assertThat(find(".ajaxform .ajaxret").get(0).getText(), is(not("")));

        wd.findElement(By.xpath("//div[@class='btn-group']//button[.='entries']")).click();
        assertEquals(2, find(".summary_entry_title").size());

        wd.findElement(By.xpath("//div[@class='btn-group']//button[.='comments']")).click();
        assertEquals(3, find(".comments_container .comment_content").size());
        assertEquals(3, find(".comments_container .waiting_comment").size());
        assertEquals(0, find(".comments_container .normal_comment").size());
        assertEquals(0, find(".comments_container .deleted_comment").size());
        wd.findElement(By.id("c2_r1")).click();
        AfnfUtil.sleep(DEFAULT_WAIT);
        assertThat(find("#c2 .ajaxret").get(0).getText(), is(not("")));

        wd.findElement(By.xpath("//div[@class='btn-group']//button[.='comments']")).click();
        assertEquals(3, find(".comments_container .comment_content").size());
        assertEquals(2, find(".comments_container .waiting_comment").size());
        assertEquals(1, find(".comments_container .normal_comment").size());
        assertEquals(0, find(".comments_container .deleted_comment").size());
        wd.findElement(By.id("c3_r2")).click();
        AfnfUtil.sleep(DEFAULT_WAIT);
        assertThat(find("#c3 .ajaxret").get(0).getText(), is(not("")));

        wd.findElement(By.xpath("//div[@class='btn-group']//button[.='comments']")).click();
        assertEquals(3, find(".comments_container .comment_content").size());
        assertEquals(1, find(".comments_container .waiting_comment").size());
        assertEquals(1, find(".comments_container .normal_comment").size());
        assertEquals(1, find(".comments_container .deleted_comment").size());
    }

    @Test
    public void test106_user() {

        wd.get(baseurl);
        assertEquals(2, find(".summary_entry_title").size());
        assertEquals(2, find(".sb_entry_title").size());
        assertEquals(4, find(".sb_tag").size());
        assertEquals(1, find(".sb_month").size());

        wd.findElement(By.cssSelector(".summary_entry_title")).click();
        wd.findElement(By.linkText("blog1234")).click();

        assertEquals(2, find(".comments_container .comment").size());
        assertEquals(1, find(".comments_container .comment_content").size());

        wd.findElement(By.id("name")).click();
        wd.findElement(By.id("name")).clear();
        wd.findElement(By.id("name")).sendKeys("aaaa");
        wd.findElement(By.id("content")).click();
        wd.findElement(By.id("content")).clear();
        wd.findElement(By.id("content")).sendKeys("bbbbbbbb");
        postAndCloseModal();

        assertEquals(3, find(".comments_container .comment").size());
        assertEquals(1, find(".comments_container .comment_content").size());

        wd.findElement(By.linkText("ブログ12345")).click();

        assertEquals(0, find(".comments_container .comment").size());
        assertEquals(0, find(".comments_container .comment_content").size());

        wd.findElement(By.id("name")).click();
        wd.findElement(By.id("name")).clear();
        wd.findElement(By.id("name")).sendKeys("ああああああああああああ");
        wd.findElement(By.id("content")).click();
        wd.findElement(By.id("content")).clear();
        wd.findElement(By.id("content")).sendKeys(
                "aaaaaaaabbbbbbbbbbbbb\n ああああああああああaaaaa\n<b>aaa</b>jfdytr\n\n\n\ni7tfjyg\n\n\nfj75rfjytft");
        postAndCloseModal();

        assertEquals(1, find(".comments_container .comment").size());
        assertEquals(0, find(".comments_container .comment_content").size());

        wd.findElement(By.linkText("タグa")).click();
        List<WebElement> matches = find(".sb_matched");
        assertEquals(1, matches.size());
        assertEquals("タグa (1)", matches.get(0).getText());

        wd.findElement(By.linkText("tag1")).click();
        matches = find(".sb_matched");
        assertEquals(1, matches.size());
        assertEquals("tag1 (2)", matches.get(0).getText());
    }

    @Test
    public void test107_admin() {
        wd.get(baseurl + "/_admin/entries/");
        wd.findElement(By.name("j_username")).click();
        wd.findElement(By.name("j_username")).clear();
        wd.findElement(By.name("j_username")).sendKeys("admin");
        wd.findElement(By.name("j_password")).click();
        wd.findElement(By.name("j_password")).clear();
        wd.findElement(By.name("j_password")).sendKeys("pass");
        postAndWait();

        wd.findElement(By.xpath("//div[@class='btn-group']//button[.='new']")).click();
        wd.findElement(By.id("title")).click();
        wd.findElement(By.id("content")).click();
        wd.findElement(By.id("content")).clear();
        wd.findElement(By.id("content"))
                .sendKeys(
                        "#Markdownのテスト1234\n てすとてすとてすとて **すとてすとてす** とてすとてすとてすと。\nてすと```てすとてすとてすとてすと```てすとてすとてす _とてすとてすとてす_ とてすとてすと。\naaaalongaaaalogaaaalongaaaalongaaaalongaaaalongaaaalongaaaalongaaaalongaaaalongaaaalongaaaalongaaaalongaaaalongaaaalongaaaalongaaaalongaaaalongaaaalongaaaalongaaaalongaaaalongaaaalongaaaalongaa\n<br/><br/>aa3a<span style=\"color:red\" class=\"md_element\">e3a4</span>3aa\n\n#テーブル1224\n1|2 |3\n---|---|---\na|b|c\nxxxxxxx|yyyyyyyy|zzzzzz*long* **longlong**\n\n\n# 複数行の整形済テキスト\n````\nclass HelloWorld {\n  public static void main() {\n    int i = 1;\n    System.out.println(\"Hello, World! : \" + i);\n  }\n}\n````\n\n## PHPでhello world\n````\n<?php\n  echo \"Hello, World!\\n\";\n?>\n````\n\n\n## 順序\n1. a1\n1. 順序付きリストのアイテム\n 1. 子供1\n 1. 子供1\n1. 順序付きリストの別のアイテム\n  \n\n### リスト\n* aa\n * [抗酸化物質](//ja.wikipedia.org/wiki/%E6%8A%97%E9%85%B8%E5%8C%96%E7%89%A9%E8%B3%AA)\n * cc\n* e\n\n\n#### リンク\n* [ローカルホスト2](http://localhost)\n* [ローカルホスト1](http://localhost)\n* ![alt](//upload.wikimedia.org/wikipedia/commons/a/ad/Wikipedia-logo-v2-ja.png)");
        wd.findElement(By.id("title")).click();
        wd.findElement(By.id("title")).clear();
        wd.findElement(By.id("title")).sendKeys("Markdownのテスト1234");
        wd.findElement(By.id("tags")).click();
        wd.findElement(By.id("tags")).sendKeys("\\9");
        wd.findElement(By.id("tags")).click();
        wd.findElement(By.id("tags")).clear();
        wd.findElement(By.id("tags")).sendKeys("tag1, tag3, タグa, タグb");

        wd.findElement(By.cssSelector("span.md_element")).click();

        postAndWait();
        assertThat(find(".ajaxform .ajaxret").get(0).getText(), is(not("")));

        wd.findElement(By.xpath("//div[@class='btn-group']//button[.='cache']")).click();
        wd.findElement(By.name("update")).click();
        AfnfUtil.sleep(DEFAULT_WAIT);
    }

    @Test
    public void test108_user() {

        wd.get(baseurl);
        assertEquals(3, find(".summary_entry_title").size());
        assertEquals(3, find(".sb_entry_title").size());
        assertEquals(5, find(".sb_tag").size());
        assertEquals(1, find(".sb_month").size());

        List<WebElement> tags = find(".sb_tag");
        assertEquals("tag1 (3)", tags.get(0).getText());
        assertEquals("tag3 (3)", tags.get(1).getText());
        assertEquals("タグa (2)", tags.get(2).getText());
        assertEquals(5, tags.size());

        List<WebElement> cmcount = find(".cmcount");
        assertThat(cmcount.get(0).getText(), endsWith("(1)"));
        assertThat(cmcount.get(1).getText(), endsWith("(3)"));
        assertEquals(2, cmcount.size());

        wd.findElement(By.cssSelector("h3.summary_entry_title")).click();
        wd.findElement(By.linkText("Markdownのテスト1234")).click();
        wd.findElement(By.cssSelector("span.md_element")).click();
        wd.findElement(By.id("name")).click();
        wd.findElement(By.id("name")).clear();
        wd.findElement(By.id("name")).sendKeys("aaa");
        wd.findElement(By.id("content")).click();
        wd.findElement(By.id("content")).clear();
        wd.findElement(By.id("content")).sendKeys("bbbbb");
        postAndCloseModal();

        assertEquals(1, find(".comments_container .comment").size());
        assertEquals(0, find(".comments_container .comment_content").size());

        wd.findElement(By.id("content")).click();
        wd.findElement(By.id("name")).click();
        wd.findElement(By.id("name")).clear();
        wd.findElement(By.id("name")).sendKeys("vvvvv");
        wd.findElement(By.id("content")).click();
        wd.findElement(By.id("content")).clear();
        wd.findElement(By.id("content")).sendKeys("cccccccc");
        postAndCloseModal();

        assertEquals(2, find(".comments_container .comment").size());
        assertEquals(0, find(".comments_container .comment_content").size());

        wd.findElement(By.id("name")).click();
        wd.findElement(By.id("name")).clear();
        wd.findElement(By.id("name")).sendKeys("dddddd");
        wd.findElement(By.id("content")).click();
        wd.findElement(By.id("content")).clear();
        wd.findElement(By.id("content")).sendKeys("vvvvvvvvvvvvvvvvvvv");
        postAndCloseModal();

        assertEquals(3, find(".comments_container .comment").size());
        assertEquals(0, find(".comments_container .comment_content").size());

        wd.findElement(By.cssSelector(".headerdiv > h1 > a")).click();
        cmcount = find(".cmcount");
        assertThat(cmcount.get(0).getText(), endsWith("(3)"));
        assertThat(cmcount.get(1).getText(), endsWith("(1)"));
        assertThat(cmcount.get(2).getText(), endsWith("(3)"));
        assertEquals(3, cmcount.size());
    }

    @Test
    public void test201_admin() {

        // 10000件投入
        executeSql("classpath:sql/db-testdata-copy10000.sql");

        wd.get(baseurl + "/_admin/entries/");
        wd.findElement(By.name("j_username")).click();
        wd.findElement(By.name("j_username")).clear();
        wd.findElement(By.name("j_username")).sendKeys("admin");
        wd.findElement(By.name("j_password")).click();
        wd.findElement(By.name("j_password")).clear();
        wd.findElement(By.name("j_password")).sendKeys("pass");
        postAndWait();

        assertEquals(30, find(".summary_entry_title").size());

        wd.findElement(By.xpath("//div[@class='btn-group']//button[.='cache']")).click();

        assertEquals("3", wd.findElement(By.className("totalNormalCount")).getText());
        assertEquals("5", wd.findElement(By.className("tagCount")).getText());
        assertEquals("1", wd.findElement(By.className("monthCount")).getText());

        wd.findElement(By.name("update")).click();
        AfnfUtil.sleep(DEFAULT_WAIT);

        assertEquals("10004", wd.findElement(By.className("totalNormalCount")).getText());
        assertEquals("18", wd.findElement(By.className("tagCount")).getText());
        assertEquals("34", wd.findElement(By.className("monthCount")).getText());
    }

    @Test
    public void test202_user() {

        wd.get(baseurl);
        wd.findElement(By.cssSelector("div.col-md-9.maindiv")).click();
        wd.findElement(By.linkText("3")).click();
        wd.findElement(By.linkText("8")).click();
        wd.findElement(By.linkText("14")).click();
        wd.findElement(By.linkText("20")).click();
        wd.findElement(By.linkText("26")).click();
        wd.findElement(By.linkText(">")).click();
        wd.findElement(By.cssSelector("div.summary_entries_container")).click();
        wd.findElement(By.linkText(">")).click();
        wd.findElement(By.cssSelector("body")).click();
        wd.findElement(By.linkText(">")).click();
        wd.findElement(By.linkText("35")).click();
        wd.findElement(By.linkText("Sep 2012")).click();
        wd.findElement(By.cssSelector("div.summary_entries_container")).click();
        wd.findElement(By.linkText("title9430")).click();
        wd.findElement(By.id("name")).click();
        wd.findElement(By.id("name")).clear();
        wd.findElement(By.id("name")).sendKeys("fwe");
        wd.findElement(By.id("content")).click();
        wd.findElement(By.id("content")).clear();
        wd.findElement(By.id("content")).sendKeys("feafefea");
        postAndCloseModal();

        find(".sb_tag a").get(2).click();
        wd.findElement(By.linkText("12")).click();
        wd.findElement(By.linkText(">")).click();
        wd.findElement(By.linkText("7")).click();
        wd.findElement(By.linkText("11")).click();
        wd.findElement(By.linkText("<")).click();
        wd.findElement(By.linkText("<")).click();
        wd.findElement(By.linkText("<")).click();
        wd.findElement(By.linkText("<")).click();
        wd.findElement(By.linkText("14")).click();
        wd.findElement(By.linkText("20")).click();
        wd.findElement(By.linkText("26")).click();
        wd.findElement(By.linkText("32")).click();
        wd.findElement(By.cssSelector("body")).click();
        wd.findElement(By.linkText("38")).click();
        wd.findElement(By.cssSelector("body")).click();
        wd.findElement(By.linkText("44")).click();
        find(".sb_tag a").get(3).click();
        wd.findElement(By.linkText("14")).click();
        find(".sb_recents .sb_more a").get(0).click();
        wd.findElement(By.linkText("title9997")).click();

        find(".sb_tags .sb_more a").get(0).click();
        wd.findElement(By.linkText("tag4")).click();
        find(".sb_tags .sb_more a").get(0).click();
        List<WebElement> matches = find(".sb_matched");
        assertEquals(1, matches.size());
        assertThat(matches.get(0).getText(), startsWith("tag4 ("));

        wd.findElement(By.linkText("タグb")).click();
        matches = find(".sb_matched");
        assertEquals(1, matches.size());
        assertThat(matches.get(0).getText(), startsWith("タグb ("));

        wd.findElement(By.linkText("tag1")).click();
        wd.findElement(By.linkText("ALL")).click();
        wd.findElement(By.linkText("12")).click();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        String yyyyMM = sdf.format(new Date());
        wd.get(baseurl + "/m/" + yyyyMM);

        List<WebElement> cmcount = find(".cmcount");
        assertThat(cmcount.get(0).getText(), endsWith("(3)"));
        assertThat(cmcount.get(1).getText(), endsWith("(1)"));
        assertThat(cmcount.get(2).getText(), endsWith("(670)"));
        assertEquals(3, cmcount.size());
    }

    @Test
    public void test203_admin() {
        wd.get(baseurl + "/_admin/entries/");
        wd.findElement(By.name("j_username")).click();
        wd.findElement(By.name("j_username")).clear();
        wd.findElement(By.name("j_username")).sendKeys("admin");
        wd.findElement(By.name("j_password")).click();
        wd.findElement(By.name("j_password")).clear();
        wd.findElement(By.name("j_password")).sendKeys("pass");
        postAndWait();

        wd.findElement(By.linkText("5")).click();
        wd.findElement(By.linkText("10")).click();
        wd.findElement(By.linkText("15")).click();
        wd.findElement(By.linkText(">")).click();
        wd.findElement(By.linkText(">")).click();
        wd.findElement(By.linkText("20")).click();
        wd.findElement(By.linkText(">")).click();
        wd.findElement(By.linkText(">")).click();
        wd.findElement(By.linkText(">")).click();
        wd.findElement(By.linkText(">")).click();
        wd.findElement(By.linkText(">")).click();
        wd.findElement(By.linkText(">")).click();
        wd.findElement(By.linkText("29")).click();

        wd.findElement(By.linkText("title9132")).click();
        wd.findElement(By.xpath("//form[@id='entry']//label[.='delete']")).click();
        wd.findElement(By.id("r2")).click();
        postAndWait();
        assertThat(find(".ajaxform .ajaxret").get(0).getText(), is(not("")));

        wd.findElement(By.xpath("//div[@class='btn-group']//button[.='comments']")).click();
        wd.findElement(By.linkText("6")).click();
        wd.findElement(By.linkText(">")).click();
        wd.findElement(By.linkText(">")).click();
        wd.findElement(By.linkText("11")).click();
        wd.findElement(By.linkText("16")).click();
        wd.findElement(By.linkText("21")).click();
        wd.findElement(By.linkText("26")).click();
        wd.findElement(By.linkText("<")).click();
        wd.findElement(By.linkText("<")).click();
        wd.findElement(By.linkText("<")).click();
        wd.findElement(By.linkText("<")).click();
        wd.findElement(By.linkText("<")).click();
        wd.findElement(By.linkText("<")).click();
        wd.findElement(By.linkText("<")).click();
        wd.findElement(By.linkText("<")).click();
        wd.findElement(By.linkText("17")).click();

        wd.findElement(By.id("c678_r2")).click();
        wd.findElement(By.id("c677_r1")).click();
        wd.findElement(By.id("c676_r1")).click();
        wd.findElement(By.id("c676_r2")).click();
        AfnfUtil.sleep(DEFAULT_WAIT);

        wd.findElement(By.linkText("18")).click();
        wd.findElement(By.id("c662_r1")).click();
        wd.findElement(By.id("c661_r1")).click();
        wd.findElement(By.id("c657_r2")).click();
        wd.findElement(By.id("c655_r2")).click();
        wd.findElement(By.id("c654_r1")).click();
        wd.findElement(By.id("c653_r1")).click();
        wd.findElement(By.id("c651_r2")).click();
        AfnfUtil.sleep(DEFAULT_WAIT);

        wd.findElement(By.linkText("22")).click();
        wd.findElement(By.linkText("25")).click();
        wd.findElement(By.linkText("28")).click();
        wd.findElement(By.linkText("32")).click();
        wd.findElement(By.linkText("35")).click();
        wd.findElement(By.linkText("38")).click();
        wd.findElement(By.linkText("39")).click();
        wd.findElement(By.linkText("44")).click();
        wd.findElement(By.linkText("47")).click();
        wd.findElement(By.linkText("50")).click();
        wd.findElement(By.linkText("51")).click();
        wd.findElement(By.id("c6_r1")).click();
        wd.findElement(By.linkText(" 2 ")).click();
        assertThat(wd.findElement(By.className("cmcount")).getText(), endsWith("(1)"));
    }

    @Test
    public void test204_user() throws ParseException {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMM");
        String yyyyMM = sdf1.format(new Date());
        wd.get(baseurl + "/m/" + yyyyMM);

        List<WebElement> cmcount = find(".cmcount");
        assertThat(cmcount.get(0).getText(), endsWith("(3)"));
        assertThat(cmcount.get(1).getText(), endsWith("(1)"));
        assertThat(cmcount.get(2).getText(), endsWith("(668)"));
        assertEquals(3, cmcount.size());

        assertEquals(4, find(".summary_entry_title").size());
        assertEquals(8, find(".sb_entry_title").size());
        assertEquals(18, find(".sb_tag").size());
        assertEquals(34, find(".sb_month").size());

        List<WebElement> matches;

        SimpleDateFormat sdf2 = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH);
        String mmmyyyy = sdf2.format(new Date());
        wd.findElement(By.linkText(mmmyyyy)).click();
        find(".sb_archive .sb_more a").get(0).click();
        matches = find(".sb_matched");
        assertEquals(1, matches.size());
        assertThat(matches.get(0).getText(), startsWith(mmmyyyy + " ("));

        mmmyyyy = sdf2.format(sdf1.parse("201101"));
        wd.findElement(By.linkText(mmmyyyy)).click();
        find(".sb_archive .sb_more a").get(0).click();
        matches = find(".sb_matched");
        assertEquals(1, matches.size());
        assertThat(matches.get(0).getText(), startsWith(mmmyyyy + " ("));

        mmmyyyy = sdf2.format(sdf1.parse("201102"));
        wd.findElement(By.linkText(mmmyyyy)).click();
        find(".sb_archive .sb_more a").get(0).click();
        matches = find(".sb_matched");
        assertEquals(1, matches.size());
        assertThat(matches.get(0).getText(), startsWith(mmmyyyy + " ("));

        mmmyyyy = sdf2.format(sdf1.parse("201103"));
        wd.findElement(By.linkText(mmmyyyy)).click();
        find(".sb_archive .sb_more a").get(0).click();

        mmmyyyy = sdf2.format(sdf1.parse("201104"));
        wd.findElement(By.linkText(mmmyyyy)).click();
        find(".sb_archive .sb_more a").get(0).click();

        List<WebElement> months = find(".sb_month");
        assertEquals(34, months.size());
        assertEquals(sdf2.format(new Date()) + " (4)", months.get(0).getText());
        assertEquals(sdf2.format(sdf1.parse("201210")) + " (271)", months.get(1).getText());
        assertEquals(sdf2.format(sdf1.parse("201209")) + " (300)", months.get(2).getText());
        assertEquals(sdf2.format(sdf1.parse("201208")) + " (309)", months.get(3).getText());

        wd.findElement(By.linkText("tag1")).click();
        wd.findElement(By.linkText("tag0")).click();
        find(".sb_tags .sb_more a").get(0).click();
        wd.findElement(By.linkText("tag8")).click();
        find(".sb_tags .sb_more a").get(0).click();
        wd.findElement(By.linkText("tag9")).click();

        find(".sb_tags .sb_more a").get(0).click();
        List<WebElement> tags = find(".sb_tag");
        assertEquals(18, tags.size());
        assertEquals("tag1 (10003)", tags.get(0).getText());
        assertEquals("tag3 (10003)", tags.get(1).getText());
        assertEquals("タグa (10002)", tags.get(2).getText());
        assertEquals("tag9 (625)", tags.get(16).getText());
        assertEquals("tag12 (624)", tags.get(17).getText());
    }

    @Test
    public void test205_user() {

        wd.get(baseurl + "/?page=323");
        wd.findElement(By.linkText("327")).click();
        wd.findElement(By.linkText("334")).click();
        wd.findElement(By.linkText("<")).click();
        wd.findElement(By.linkText(">")).click();
        wd.findElement(By.linkText("title1")).click();

        wd.findElement(By.id("name")).click();
        wd.findElement(By.id("name")).clear();
        wd.findElement(By.id("name")).sendKeys("test");
        wd.findElement(By.id("content")).click();
        wd.findElement(By.id("content")).clear();
        wd.findElement(By.id("content")).sendKeys("test");
        postAndCloseModal();

        wd.navigate().back();
        wd.findElement(By.linkText("333")).click();
        wd.findElement(By.linkText(">")).click();

        List<WebElement> cmcount = find(".cmcount");
        assertThat(cmcount.get(0).getText(), endsWith("(1)"));
        assertThat(cmcount.get(1).getText(), endsWith("(3)"));
        assertThat(cmcount.get(2).getText(), endsWith("(1)"));
        assertThat(cmcount.get(3).getText(), endsWith("(668)"));
        assertEquals(4, cmcount.size());
    }

    @Test
    public void test206_user() {
        wd.get(baseurl);

        long future = System.currentTimeMillis() + TokenService.VALID_MS * 2;
        long past = System.currentTimeMillis() - TokenService.VALID_MS - 1;

        String[] tokens = {
                null,
                "aaaaaa",
                "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb",
                "cwdkJZ3ioCMDTmyJ9PIsGnXzrLF5HlfhbMqipf3cIUE",
                Crypto.encrypt("" + future),
                Crypto.encrypt("" + past),
                Crypto.encrypt("aaaaaaaa"),
                null };
        int[] counts = { 1, 1, 1, 1, 1, 1, 1, 2 };

        JavascriptExecutor je = (JavascriptExecutor) wd;

        for (int i = 0; i < tokens.length; i++) {
            wd.findElement(By.linkText("Markdownのテスト1234789")).click();
            if (tokens[i] != null) {
                je.executeScript("afnfblog.convToken = function(){return '" + tokens[i] + "';}");
            }
            else {
                je.executeScript("afnfblog.convToken = undefined;");
            }
            wd.findElement(By.id("name")).click();
            wd.findElement(By.id("name")).clear();
            wd.findElement(By.id("name")).sendKeys("&tes\n\n\n\'t\\3<b>abbb</b>\"");
            wd.findElement(By.id("content")).click();
            wd.findElement(By.id("content")).clear();
            wd.findElement(By.id("content")).sendKeys("aaaaaaaabbbbbbbbbbaaaaaaaa\"a&aa");
            postAndCloseModal();
            assertEquals("loop" + i, counts[i], find(".comments_container .comment").size());
            assertEquals("loop" + i, 0, find(".comments_container .comment_content").size());
        }
    }

    @Test
    public void test207_user() {
        wd.get(baseurl + "/m/201003");

        wd.findElement(By.cssSelector(".previous a")).click();
        assertThat(wd.getCurrentUrl(), endsWith("201002"));

        wd.findElement(By.cssSelector(".next a")).click();
        assertThat(wd.getCurrentUrl(), endsWith("201003"));

        wd.findElement(By.cssSelector(".previous a")).click();
        assertThat(wd.getCurrentUrl(), endsWith("201002"));

        wd.findElement(By.cssSelector(".next a")).click();
        assertThat(wd.getCurrentUrl(), endsWith("201003"));

        wd.findElement(By.cssSelector(".next a")).click();
        assertThat(wd.getCurrentUrl(), endsWith("201004"));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        String yyyyMM = sdf.format(new Date());
        wd.findElement(By.linkText("Sep 2012")).click();

        wd.findElement(By.cssSelector(".next a")).click();
        assertThat(wd.getCurrentUrl(), endsWith("201210"));

        wd.findElement(By.cssSelector(".next a")).click();
        assertThat(wd.getCurrentUrl(), endsWith(yyyyMM));

        wd.findElement(By.cssSelector(".previous a")).click();
        assertThat(wd.getCurrentUrl(), endsWith("201210"));

        wd.findElement(By.cssSelector(".next a")).click();
        assertThat(wd.getCurrentUrl(), endsWith(yyyyMM));

        wd.findElement(By.cssSelector(".previous a")).click();
        assertThat(wd.getCurrentUrl(), endsWith("201210"));

        wd.findElement(By.cssSelector(".previous a")).click();
        assertThat(wd.getCurrentUrl(), endsWith("201209"));
    }

    protected List<WebElement> find(String cssPath) {
        return wd.findElements(By.cssSelector(cssPath));
    }

    protected void postAndWait() {
        wd.findElement(By.name("post")).click();
        AfnfUtil.sleep(DEFAULT_WAIT);
    }

    protected void postAndCloseModal() {
        wd.findElement(By.name("post")).click();
        AfnfUtil.sleep(DEFAULT_WAIT);
        wd.findElement(By.id("close_modal")).click();
    }

    protected void takeScreenShot() {
        File tmpFile = ((TakesScreenshot) wd).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(tmpFile, new File("logs/" + System.currentTimeMillis() + ".img"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
