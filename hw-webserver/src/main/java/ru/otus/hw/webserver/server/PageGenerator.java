package ru.otus.hw.webserver.server;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class PageGenerator {
    public static final String STATUS_NOT_AUTHORIZED = "NOT AUTHORIZED";
    public static final String STATUS_AUTHORIZED = "AUTHORIZED";
    public static final String CONTENT_TYPE_FOR_RESPONSE = "text/html;charset=utf-8";

    public static final String PAGE_PARAMETER_USERS = "users";
    public static final String PAGE_PARAMETER_LOGIN = "login";
    public static final String PAGE_PARAMETER_AUTHORIZATION_STATUS = "authorization_status";
    public static final String PAGE_PARAMETER_SET_LOGIN = "set_login";
    public static final String PAGE_PARAMETER_SET_PASSWORD = "set_password";
    public static final String PAGE_PARAMETER_SET_USER_NAME = "set_user_name";
    public static final String PAGE_PARAMETER_SET_USER_AGE = "set_user_age";
    public static final String PAGE_PARAMETER_SET_USER_ADDRESS = "set_user_address";
    public static final String PAGE_PARAMETER_SET_USER_PHONE = "set_user_phone";

    private static final String HTML_DIR = "/web-page/";
    private static final String CONFIGURATION_DEFAULT_ENCODING = "UTF-8";

    private final Configuration configuration;

    public PageGenerator() {
        configuration = new Configuration(Configuration.VERSION_2_3_29);
        configuration.setClassForTemplateLoading(this.getClass(), HTML_DIR);
        configuration.setDefaultEncoding(CONFIGURATION_DEFAULT_ENCODING);
    }

    public String getPage(String filename, Map<String, Object> data) throws IOException {
        try (Writer stream = new StringWriter();) {
            Template template = configuration.getTemplate(filename);
            template.process(data, stream);
            return stream.toString();
        } catch (TemplateException e) {
            throw new IOException(e);
        }
    }

    public static Map<String, Object> setPageAuthorizationParameters(UserSession userSession) {
        Map<String, Object> pageVariables = new HashMap<>();

        if (userSession == null) {
            pageVariables.put(PAGE_PARAMETER_AUTHORIZATION_STATUS, STATUS_NOT_AUTHORIZED);
            pageVariables.put(PAGE_PARAMETER_LOGIN, "");
        } else {
            pageVariables.put(PAGE_PARAMETER_AUTHORIZATION_STATUS, STATUS_AUTHORIZED);
            pageVariables.put(PAGE_PARAMETER_LOGIN, userSession.getLogin());
        }

        return pageVariables;
    }
}
