package dev.anto.song.parsing.controller;


import dev.anto.song.parsing.service.TjParsingServiceImpl;
import dev.anto.song.parsing.vo.SongVo;
import dev.anto.song.parsing.vo.TjSearchType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest/song/search")
public class SongSearchController {

	private final TjParsingServiceImpl service;

	public SongSearchController(TjParsingServiceImpl service) {
		this.service = service;
	}

	@GetMapping("/title")
	public ResponseEntity<?> title(String title, Pageable pageable) {
		Page<SongVo> songList = service.search(TjSearchType.TITLE, title, pageable);
		return ResponseEntity.ok(songList);
	}

	@GetMapping("/singer")
	public ResponseEntity<?> singer(String singer, Pageable pageable) {
		Page<SongVo> songList = service.search(TjSearchType.SINGER, singer, pageable);
		return ResponseEntity.ok(songList);
	}

	@GetMapping("/composer")
	public ResponseEntity<Page<?>> composer(String composer, Pageable pageable) {
		Page<SongVo> songList = service.search(TjSearchType.COMPOSER, composer, pageable);
		return ResponseEntity.ok(songList);
	}

	@GetMapping("/lyricist")
	public ResponseEntity<Page<SongVo>> lyricist(String lyricist, Pageable pageable) {
		Page<SongVo> songList = service.search(TjSearchType.LYRICIST, lyricist, pageable);
		return ResponseEntity.ok(songList);
	}

	@GetMapping("/number")
	public ResponseEntity<Page<SongVo>> singer(Long number, Pageable pageable) {
		Page<SongVo> songList = service.search(TjSearchType.NUMBER, String.valueOf(number), pageable);
		return ResponseEntity.ok(songList);
	}
}
