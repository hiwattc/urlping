package com.staroot.urlping.util;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.staroot.urlping.controller.EmailController;
import com.staroot.urlping.domain.CertResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class EmailContentGenerator {
    private static final Logger logger = LoggerFactory.getLogger(EmailContentGenerator.class);

    public static String generateCertResponseListHtml(List<CertResponse> certResponses) throws IOException {
        Handlebars handlebars = new Handlebars();
        Template template = handlebars.compileInline(getCertResponseListHtmlTemplate());
        logger.debug("Email Template :: "+template.toString());

        logger.debug("certResponses.size() :: "+certResponses.size());
        return template.apply(certResponses);
    }

    private static String getCertResponseListHtmlTemplate() {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Cert Response List</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h1>Cert Response List</h1>\n" +
                "\n" +
                "<table>\n" +
                "    <thead>\n" +
                "    <tr>\n" +
                "        <th>URL</th>\n" +
                "        <th>System Manager</th>\n" +
                "        <th>Application Manager</th>\n" +
                "        <th>Expiration Date</th>\n" +
                "        <th>Log Date</th>\n" +
                "    </tr>\n" +
                "    </thead>\n" +
                "    <tbody>\n" +
                "    {{#each this}}\n" +
                "    <tr>\n" +
                "        <td>{{url}}</td>\n" +
                "        <td>{{sysMgr}}</td>\n" +
                "        <td>{{appMgr}}</td>\n" +
                "        <td>{{endDt}}</td>\n" +
                "        <td>{{logDt}}</td>\n" +
                "    </tr>\n" +
                "    {{/each}}\n" +
                "    </tbody>\n" +
                "</table>\n" +
                "</body>\n" +
                "</html>";
    }

}
