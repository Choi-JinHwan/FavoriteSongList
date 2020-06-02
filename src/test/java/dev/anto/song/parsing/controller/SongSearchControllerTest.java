package dev.anto.song.parsing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SongSearchControllerTest {


	@Autowired
	WebApplicationContext ctx;

	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@BeforeEach
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
				.addFilters(new CharacterEncodingFilter("UTF-8", true))
				.alwaysDo(print())
				.build();
	}

	@ParameterizedTest(name = "노래 검색 - 제목[{0}]")
	@ValueSource(strings = {"사랑을 했다", "가라사대"})
	public void title(String title) throws Exception {
		MvcResult mvcResult = mockMvc.perform(
				get("/rest/song/search/title")
						.param("title", title)
						.contentType(MediaType.APPLICATION_JSON)
		)
				.andExpect(status().isOk())
				.andExpect(jsonPath("empty").value("false"))
				.andReturn();
	}

	@ParameterizedTest(name = "노래 검색 - 가수[{0}]")
	@ValueSource(strings = {"태연", "BEWHY"})
	public void singer(String singer) throws Exception {
		MvcResult mvcResult = mockMvc.perform(
				get("/rest/song/search/singer")
						.param("singer", singer)
						.contentType(MediaType.APPLICATION_JSON)
		)
				.andExpect(status().isOk())
				.andExpect(jsonPath("empty").value("false"))
				.andReturn();
	}

	@ParameterizedTest(name = "노래 검색 - 작곡가[{0}]")
	@ValueSource(strings = {"박진영", "이적"})
	void composer(String composer) throws Exception {
		MvcResult mvcResult = mockMvc.perform(
				get("/rest/song/search/composer")
						.param("composer", composer)
						.contentType(MediaType.APPLICATION_JSON)
		)
				.andExpect(status().isOk())
				.andExpect(jsonPath("empty").value("false"))
				.andReturn();
	}

	@ParameterizedTest(name = "노래 검색 - 작사가[{0}]")
	@ValueSource(strings = {"이적", "아이유"})
	public void lyricist(String lyricist) throws Exception {
		MvcResult mvcResult = mockMvc.perform(
				get("/rest/song/search/lyricist")
						.param("lyricist", lyricist)
						.contentType(MediaType.APPLICATION_JSON)
		)
				.andExpect(status().isOk())
				.andExpect(jsonPath("empty").value("false"))
				.andReturn();
	}

	@ParameterizedTest(name = "노래 검색 - 번호[{0}]")
	@ValueSource(longs = {62743L,24526L} )
	public void number(long number) throws Exception {
		MvcResult mvcResult = mockMvc.perform(
				get("/rest/song/search/number")
						.param("number", String.valueOf(number))
						.contentType(MediaType.APPLICATION_JSON)
		)
				.andExpect(status().isOk())
				.andExpect(jsonPath("empty").value("false"))
				.andReturn();
	}


}