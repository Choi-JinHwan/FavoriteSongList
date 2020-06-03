$(document).ready(function () {
	$('button#button-search').click(function () {
		search();
	});

	$("#keyword").keydown(function (e) {
		if (e.keyCode == 13){
			search();
		}
	});
});

function search() {

	let searchType = $('.container #searchTypeSelect option:selected').val();
	let keyword = $('#keyword').val();
	$.ajax({
		url: `/rest/song/search/${searchType}?${searchType}=${keyword}`,
		success: function (data) {
			$('#searchResult > tbody').html('');
			console.log(data);
			if (!data.empty) {
				let content = data.content;

				content.forEach(song => {
					$('#searchResult > tbody').append(`<tr><td>${song.number}</td><td>${song.name}</td><td>${song.singer}</td><td>${song.lyricist}</td><td>${song.composer}</td></tr>`);
				});
			}
		}
	});

	return false;
}

