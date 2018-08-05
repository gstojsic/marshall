package com.skunkworks.rmi.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.junit.Test;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * stole on 11.04.17.
 */
public class FreemarkerTest {

    @Test
    public void testTemplate() throws Exception {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_26);
        cfg.setClassForTemplateLoading(getClass(), "/freemarker");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);

        Map<String, Object> root = new HashMap<>();

        root.put("user", "Big Joe");
        Product latest = new Product();
        latest.setUrl("products/greenmouse.html");
        latest.setName("green mouse");
        root.put("latestProduct", latest);

        Template temp = cfg.getTemplate("rmi/rpcSerializer.ftl");

        Writer out = new OutputStreamWriter(System.out);
        temp.process(root, out);
    }

    public static class Product {
        private String url;
        private String name;

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
