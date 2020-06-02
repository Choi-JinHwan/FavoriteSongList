package dev.anto.song.parsing.vo;


import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class SongVo {

    private Long number;

    private String name;

    private String singer;

    private String lyricist;

    private String composer;


    public SongVo(Long number, String name, String singer, String lyricist, String composer) {
        this.number = number;
        this.name = name;
        this.singer = singer;
        this. lyricist = lyricist;
        this.composer = composer;
    }
}
