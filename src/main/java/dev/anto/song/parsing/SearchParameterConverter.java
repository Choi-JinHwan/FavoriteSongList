package dev.anto.song.parsing;

import dev.anto.song.parsing.vo.TjSearchParameterVo;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SearchParameterConverter implements Converter<TjSearchParameterVo, Map<String, String>> {
	@Override
	public Map<String, String> convert(TjSearchParameterVo tjSearchParameterVo) {
		int pageType = tjSearchParameterVo.getTypeNumber();
		PageRequest pageRequest = tjSearchParameterVo.getPageRequest();

		Integer pageSize = pageRequest.getPageSize();
		int pageNumber = pageRequest.getPageNumber();

		Map<String, String> parameters = new HashMap<>();
		parameters.put("strCond", "0");
		parameters.put("strType", "1");
		//검색 페이징 사이즈
		parameters.put("strSize01", String.valueOf(pageSize));
		if(pageNumber == 0 ){
			//검색 페이지 숫자
			parameters.put("intPage", "1");
		} else {
			parameters.put("intPage", String.valueOf(pageNumber));
		}
		//검색 종류
		parameters.put("strType", String.valueOf(pageType));
		//검색어
		parameters.put("strText", tjSearchParameterVo.getKeyword());

		return parameters;
	}
}
