package com.songspk.process;

import com.songspk.entity.Album;
import com.songspk.global.GenericDB;
import com.songspk.global.ShellExecutor;
import com.songspk.tables.Albums;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by hackme on 15/7/18.
 */
public class FetchAlbumLinks {

    public static String getNextUrl(String html){

        Document x = Jsoup.parse(html,"https://songspk.name");

        Elements elements = x.select(".pagination li");

        boolean success = false;
        Element elementFound = null;
        for(Element element: elements){
            if(success==false){
                if(element.hasClass("active")){
                    success = true;
                }
            }else {
                elementFound = element;
                break;
            }
        }

        try{
            return elementFound.select("a").first().absUrl("href");
        }catch (Exception e){

        }
        return null;
    }

    public static ArrayList<String> getData(String html){
        Document x = Jsoup.parse(html,"https://songspk.name");
        Elements elements = x.select("figure .image-hover");

        ArrayList<String> arrayList = new ArrayList<String>();
        for(Element e:elements){
            arrayList.add(e.absUrl("href"));
        }

        return arrayList;
    }

    public static String getHTML(String link){
        String data =null;
        try {
            data = ShellExecutor.execute("curl -s '" + link + "' -H 'Connection: keep-alive' -H 'Cache-Control: max-age=0' -H 'Upgrade-Insecure-Requests: 1' -H 'User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36' -H 'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8' -H 'Accept-Encoding: gzip, deflate' -H 'Accept-Language: en-GB,en-US;q=0.9,en;q=0.8' -H 'If-None-Match: \"1541025663+gzip\"' -H 'If-Modified-Since: Fri, 09 Aug 2013 23:54:35 GMT' --compressed");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static void main(String[] args) {

        String link = "https://songspk.name/browse/bollywood-music-albums";

        ArrayList<String> links = new ArrayList<String>();

        while (true) {
            String html = getHTML(link);

            if(html==null||html.length()==0){
                try {
                    Thread.currentThread().sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }

            ArrayList currentPageLinks = getData(html);
            links.addAll(currentPageLinks);

            link  = getNextUrl(html);

            System.out.println("Size : " + links.size() );
            System.out.println("Next Url : " + link );

            if(link==null)break;
        }

        for(String s: links){
            Album album = new Album(null,s,false,null,null,null,null);
            new GenericDB().addRow(Albums.ALBUMS,album);
        }
    }
}
