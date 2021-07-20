
const URL = 'http://localhost:8080/';
const SEARCH = 'search?query=';

$("#pco").on("click", function (event) {
    search($('#xip').val());
});

function search(query) {
    $.ajax({
        type: "POST",
        url: URL + SEARCH + query,
        data: {},
        success: function (result) {
            $("#atp").empty();
            $.each(result, function (index, element) {
                const html = '<li class="list-group-item"><a href='+element.link+'>'+element.title+'</a></li>';
                $("#atp").append(html);
            });
        }
    });
}