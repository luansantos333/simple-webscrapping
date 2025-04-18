package com.edu.webscrapping.service;

import ch.qos.logback.core.testUtil.RandomUtil;
import com.edu.webscrapping.utils.RandomStringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
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
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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

        downloadPDFs(pdfURLSet, "/home/luangomes/IdeaProjects/webscrapping/src/ans.zip");

    }


    public void downloadPDFs(Set<String> pdfURLs, String zipFileName) throws IOException {

        try (FileOutputStream fos = new FileOutputStream(zipFileName); ZipOutputStream zos = new ZipOutputStream(fos)) {
            for (String pdf : pdfURLs) {

                String fileName = RandomStringUtils.generateRandomFilePrefix("ans", "pdf");
                try (InputStream input = new URL (pdf).openStream()){
                    zos.putNextEntry(new ZipEntry(fileName));
                    byte[] buffer = new byte[4096];
                    int len;
                    while ((len = input.read(buffer)) > 0) {

                        zos.write(buffer, 0, len);
                    }

                    zos.closeEntry();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

}




