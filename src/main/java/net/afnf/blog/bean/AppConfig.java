package net.afnf.blog.bean;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${app.type}")
    private String type = null;

    @Value("${app.title}")
    private String title;

    @Value("${app.adminhost}")
    private String adminHost;

    @Value("${app.salt}")
    private String salt;

    @Value("${app.cipherSeed}")
    private String cipherSeed;

    @Value("${app.buildDate}")
    private String buildDate;

    @Value("${assets.baseurl}")
    private String assetsBaseurl;

    @Value("${assets.shell}")
    private String assetsShell = null;

    private String assetsSrcDir = "../../assets";

    @Value("${selenium.targetUrl}")
    private String seleniumTargetUrl = null;

    @Value("${selenium.webdriver}")
    private String seleniumWebdriver = null;

    private static String webappDir;

    private static AppConfig instance = null;

    public AppConfig() {
        instance = this;
    }

    public static AppConfig getInstance() {
        return instance;
    }

    public boolean isDevelopment() {
        return StringUtils.indexOf(getTitle(), "dev-") == 0;
    }

    public boolean isDemoSite() {
        return StringUtils.equalsIgnoreCase(getType(), "demo");
    }

    public boolean isTestSite() {
        return StringUtils.equalsIgnoreCase(getType(), "test");
    }

    public boolean isProductionAndNormalSite() {
        return StringUtils.equals(getType(), "production-normal");
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAdminHost() {
        return adminHost;
    }

    public void setAdminHost(String adminHost) {
        this.adminHost = adminHost;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getCipherSeed() {
        return cipherSeed;
    }

    public void setCipherSeed(String cipherSeed) {
        this.cipherSeed = cipherSeed;
    }

    public String getBuildDate() {
        return buildDate;
    }

    public void setBuildDate(String buildDate) {
        this.buildDate = buildDate;
    }

    public String getAssetsBaseurl() {
        return assetsBaseurl;
    }

    public void setAssetsBaseurl(String assetsBaseurl) {
        this.assetsBaseurl = assetsBaseurl;
    }

    public String getAssetsShell() {
        return assetsShell;
    }

    public void setAssetsShell(String assetsShell) {
        this.assetsShell = assetsShell;
    }

    public String getAssetsSrcDir() {
        return assetsSrcDir;
    }

    public void setAssetsSrcDir(String assetsSrcDir) {
        this.assetsSrcDir = assetsSrcDir;
    }

    public String getSeleniumTargetUrl() {
        return seleniumTargetUrl;
    }

    public void setSeleniumTargetUrl(String seleniumTargetUrl) {
        this.seleniumTargetUrl = seleniumTargetUrl;
    }

    public String getSeleniumWebdriver() {
        return seleniumWebdriver;
    }

    public void setSeleniumWebdriver(String seleniumWebdriver) {
        this.seleniumWebdriver = seleniumWebdriver;
    }

    public static String getWebappDir() {
        return webappDir;
    }

    public static void setWebappDir(String webappDir) {
        AppConfig.webappDir = webappDir;
    }
}
