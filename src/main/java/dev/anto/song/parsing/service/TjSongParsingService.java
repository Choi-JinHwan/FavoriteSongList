package dev.anto.song.parsing.service;


import dev.anto.song.parsing.vo.SongVo;
import dev.anto.song.parsing.vo.TjSearchType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TjSongParsingService {

	Page<SongVo> search(TjSearchType searchType, String keyword, Pageable pageable);

}
