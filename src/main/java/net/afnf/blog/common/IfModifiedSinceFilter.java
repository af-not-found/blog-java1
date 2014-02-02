package net.afnf.blog.common;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.afnf.blog.bean.AppConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IfModifiedSinceFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(IfModifiedSinceFilter.class);

    private static long lastModified = 0;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {

        if (request instanceof HttpServletRequest) {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;

            String method = req.getMethod();
            if (method != null && (method.equals("GET") || method.equals("HEAD"))) {

                // staticと_adminは対象外
                String servletPath = req.getServletPath();
                if (servletPath != null
                        && (servletPath.indexOf("/static/") == 0 || servletPath.indexOf("/_admin/") == 0) == false) {

                    // Developmentなら無効化
                    if (AppConfig.getInstance().isDevelopment() == false) {

                        // lastModifiedと一致すれば304を返す
                        long since = req.getDateHeader("If-Modified-Since");
                        //logger.info(" since=" + since + ", lastmod=" + getLastModified());
                        if (since == getLastModified()) {
                            res.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                            return;
                        }
                        // 更新されている場合はLast-Modifiedを設定する
                        else {
                            res.setDateHeader("Last-Modified", getLastModified());
                        }
                    }
                }
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

    public static void updateLastModified() {
        // HTTP Header Dateはミリ秒を扱えないのでここで取り除く
        long now = System.currentTimeMillis();
        now = now - now % 1000;
        lastModified = now;
    }

    public static long getLastModified() {
        return lastModified;
    }
}
