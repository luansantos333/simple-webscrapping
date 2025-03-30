package com.edu.webscrapping.service;

import ch.qos.logback.core.testUtil.RandomUtil;
import com.edu.webscrapping.utils.RandomStringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class WebScrapper implements CommandLineRunner {


    @Override
    public void run(String... args) throws Exception {

        Document document = Jsoup.connect("https://www.gov.br/ans/pt-br/acesso-a-informacao/participacao-da-sociedade/atualizacao-do-rol-de-procedimentos").get();
        Elements elements = document.select("a[href$=.pdf]");

        Set<String> pdfURLSet = new HashSet<>();

        for (Element link : elements) {

            String pdfUrl = link.attr("abs:href").trim();


            if (pdfUrl.contains("?")) {
                pdfUrl = pdfUrl.split("\\?")[0];
                System.out.println("Contain ?");
            }
            if (pdfUrl.contains("#")) {
                pdfUrl = pdfUrl.split("#")[0];
                System.out.println("Contain #");
            }


            if (pdfUrl.contains("atualizacao-do-rol-de-procedimentos")) {
                pdfURLSet.add(pdfUrl);
            }

        }

        downloadPDFs(pdfURLSet);

    }


    public void downloadPDFs(Set<String> pdfURLs) throws IOException {

        InputStream in = null;


        for (String pdf : pdfURLs) {
            try {
                in = new URL(pdf).openStream();
                Files.copy(in, Paths.get(RandomStringUtils.generateRandomFilePrefix("ans", "pdf")), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                in.close();
            }
        }
    }


}




