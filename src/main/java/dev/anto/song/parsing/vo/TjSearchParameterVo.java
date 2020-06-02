package dev.anto.song.parsing.vo;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.domain.PageRequest;

import javax.validation.constraints.NotNull;

@Getter @ToString
public class TjSearchParameterVo {

	private String keyword;

	@NotNull
	private Integer typeNumber;

	private PageRequest pageRequest;

	public TjSearchParameterVo(String keyword, @NotNull Integer typeNumber, PageRequest pageRequest) {
		this.keyword = keyword;
		this.typeNumber = typeNumber;
		this.pageRequest = pageRequest;
	}

}
