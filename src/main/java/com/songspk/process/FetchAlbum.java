package com.songspk.process;

import com.songspk.entity.Album;
import com.songspk.entity.Song;
import com.songspk.global.GenericDB;
import com.songspk.global.TaskManager;
import com.songspk.tables.Albums;
import com.songspk.tables.Songs;
import org.jooq.Condition;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by hackme on 22/7/18.
 */
public class FetchAlbum implements Runnable{
    private String link;
    private Long id;

    public FetchAlbum(String link, Long id){
        this.link = link;
        this.id = id;
    }

    private ArrayList<Song> getSongs(String html){

        ArrayList<Song> songs = new ArrayList<Song>();

        Document dx = Jsoup.parse(html,"https://songspk.name");
        Elements elements = dx.select(".page-tracklist-body li");

        for(Element songElement : elements){

            try{
                String songUrl = songElement.select(".col-play a").first().attr("data-url");
                String songName = songElement.select(".col-text h3").first().text();
                Elements artistLinks = songElement.select(".artist a");
                ArrayList<String> arrayList = new ArrayList<String>();
                String singers = "";

                int i=0;
                for(Element x : artistLinks){
                    if(i==artistLinks.size()-1){
                        singers = singers + (x.text()) ;
                    }else {
                        singers = singers + (x.text()) + ",";
                    }
                }

                Song song = new Song(songName,songUrl,singers,null,null,id);
                songs.add(song);
            }catch (Exception e){

            }
        }

        return songs;
    }

    private Album process(String html){

        Document document = Jsoup.parse(html,"https://songspk.name/");

        Elements elements = document.select(".list-group.page-meta-body li");

        String arr []= {"Album","Released","Cast","Music Director","Lyricist"};

        HashMap<String,String> map = new HashMap<String, String>();

        for(Element li : elements){
            try{
                String row = (li.text());
                row= row.trim();

                for(String x : arr){
                    if(row.startsWith(x)){
                        String key = x;
                        String value = row.substring(x.length()).trim();
                        map.put(key,value);
                        break;
                    }
                }

            }catch (Exception e){

            }
        }

        return new Album(map.get("Album"),link,true,map.get("Released"),map.get("Cast"),map.get("Music Director"),null);
    }

    public void run() {
            String html = FetchAlbumLinks.getHTML(link);
            Album album =process(html);

            ArrayList<Song> songs = getSongs(html);

            for(Song song: songs){
                try{
                    new GenericDB().addRow(Songs.SONGS,song);
                }catch (Exception re){
                    re.printStackTrace();
                }
            }

            Condition condition = Albums.ALBUMS.LINK.eq(album.link);
            new GenericDB<String>().updateColumn(Albums.ALBUMS.MOVIE,album.movie,Albums.ALBUMS,condition);
            new GenericDB<String>().updateColumn(Albums.ALBUMS.RELEASED,album.released,Albums.ALBUMS,condition);
            new GenericDB<String>().updateColumn(Albums.ALBUMS.CAST,album.cast,Albums.ALBUMS,condition);
            new GenericDB<String>().updateColumn(Albums.ALBUMS.DIRECTOR,album.director,Albums.ALBUMS,condition);
            new GenericDB<Boolean>().updateColumn(Albums.ALBUMS.IS_FETCHED,true,Albums.ALBUMS,condition);

            System.out.println(album.movie +":"+songs.size());
    }

    public static void main(String[] args) {


        TaskManager taskManager = new TaskManager(200);

        List<Album> albums = (List<Album>) GenericDB.getRows(Albums.ALBUMS,Album.class,Albums.ALBUMS.IS_FETCHED.isNull().or(Albums.ALBUMS.IS_FETCHED.eq(false)),null);

        for(Album a : albums){
            FetchAlbum fetchAlbum = new FetchAlbum(a.link,a.id);
            taskManager.waitTillQueueIsFreeAndAddTask(fetchAlbum);
        }
    }
}