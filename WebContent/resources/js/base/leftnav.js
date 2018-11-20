//tabs config

function gotoRight(url) {
    $.ajax({
        url: url,
        type: "post",
        dataType: "text",
        success: function (response) {
            $("#right-content").html(response).find(".search-btn").trigger("click");
        },
        error: function () {
        }
    });
}