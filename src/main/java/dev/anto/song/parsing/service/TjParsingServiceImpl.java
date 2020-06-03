package dev.anto.song.parsing.service;


import dev.anto.song.parsing.vo.SongVo;
import dev.anto.song.parsing.vo.TjSearchParameterVo;
import dev.anto.song.parsing.vo.TjSearchType;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class TjParsingServiceImpl implements TjSongParsingService {

    private final Converter<TjSearchParameterVo, Map<String, String>> searchParameterConverter;

    private static final String TJ_SONG_SEARCH_URL = "https://www.tjmedia.co.kr/tjsong/song_search_list.asp";

    public TjParsingServiceImpl(Converter<TjSearchParameterVo, Map<String, String>> searchParameterConverter) {
        this.searchParameterConverter = searchParameterConverter;
    }

    @Override
    public Page<SongVo> search(TjSearchType searchType, String keyword, Pageable pageable) {
        log.info("[{}]: {}", searchType.name(), keyword);
        switch (searchType) {
            case NUMBER:
            case LYRICIST:
            case COMPOSER:
            case SINGER:
                return searchSong(getParameterVo(keyword, searchType, pageable));
            default:
            case TITLE:
            case ALL:
                return searchSong(getParameterVo(keyword, TjSearchType.TITLE, pageable));
        }
    }

    private TjSearchParameterVo getParameterVo(String keyword, TjSearchType type, Pageable pageable){
        return new TjSearchParameterVo(keyword,
                type.getNumber(),
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize())
        );
    }

    private Page<SongVo> searchSong(TjSearchParameterVo parameterVo) {

        Map<String, String> parameters = searchParameterConverter.convert(parameterVo);

        Document document = null;
        try {
            document = Jsoup.connect(TJ_SONG_SEARCH_URL)
                    .data(parameters)
                    .get();
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"can't parsing");
        }
        if(document == null)
            return null;
        log.info("location: " + document.location());
        List<SongVo> songList = new ArrayList<>();
        for (Element element : document.select(".board_type1")) {
            for (Element tr : element.select("tr")) {
                Elements tds = tr.select("td");
                if (tds.size() > 0 && tds.size() % 6 == 0) {
                    Long number = Long.parseLong(tds.get(0).text());
                    String title = tds.get(1).text();
                    String singer = tds.get(2).text();
                    String lyricist = tds.get(3).text();
                    String composer = tds.get(4).text();
                    SongVo song = new SongVo(number, title, singer, lyricist, composer);
                    songList.add(song);

                    log.info(song.toString());
                }
            }
        }
        if(songList.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find "+parameterVo.getTypeNumber()+": ["+parameterVo.getKeyword()+"]");

        return new PageImpl<>(songList);
    }

}
